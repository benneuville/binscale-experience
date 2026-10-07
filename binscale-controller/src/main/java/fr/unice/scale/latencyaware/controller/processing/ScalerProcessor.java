package fr.unice.scale.latencyaware.controller.processing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import fr.unice.scale.latencyaware.controller.bin_pack.BinPack;
import fr.unice.scale.latencyaware.controller.entity.ConsumerGroup;
import fr.unice.scale.latencyaware.controller.entity.Partition;
import fr.unice.scale.latencyaware.controller.entity.calculation.PartitionCalculation;
import fr.unice.scale.latencyaware.controller.entity.decision.ScaleDecision;
import fr.unice.scale.latencyaware.controller.entity.graph.BranchingFactor;
import fr.unice.scale.latencyaware.controller.entity.graph.Graph;
import fr.unice.scale.latencyaware.controller.entity.graph.Vertex;
import fr.unice.scale.latencyaware.controller.entity.meta_data.CGMetaData;
import fr.unice.scale.latencyaware.controller.entity.meta_data.PartitionMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ScalerProcessor {
    protected final Logger logger = LoggerFactory.getLogger(ClassicScalerProcessor.class);
    private ObjectWriter objectWriter = new ObjectMapper().writer();

    public Map<Partition, PartitionCalculation> computeConsumer(CGMetaData cgdata) {
        double maxLagCapacity = cgdata.getDynamicMaxLagCapacity();
        double maxArrivalRate = cgdata.getDynamicMaxAverageArrivalRate();

        double minLagCapacity = cgdata.getDynamicMinLagCapacity();
        double minArrivalRate = cgdata.getDynamicMinAverageArrivalRate();

        Map<Partition, PartitionCalculation> parts = cgdata.getPartitionsMetaData().values().stream().map(
                pmd -> new PartitionCalculation(
                        pmd, getArrivalRateForPartitionCalculation(cgdata, pmd)
                )
        ).collect(Collectors.toMap(
                PartitionCalculation::getPartition,
                pc -> pc
        ));

        parts.forEach(
                (p, pc) -> {
                    pc.setMaxArrivalRate(maxArrivalRate);
                    pc.setMaxLagCapacity(maxLagCapacity);
                    pc.setMinArrivalRate(minArrivalRate);
                    pc.setMinLagCapacity(minLagCapacity);
                }
        );
        return parts;
    }


    public Map<ConsumerGroup, ScaleDecision> process(Graph<ConsumerGroup> graph, Map<ConsumerGroup, CGMetaData> cgdatas) {
        if (graph == null || cgdatas == null) {
            return new HashMap<>();
        }
        if (cgdatas.isEmpty()) {
            return new HashMap<>();
        }
        propagateArrivalRate(graph, cgdatas);

        Map<ConsumerGroup, ScaleDecision> decisions = new HashMap<>();

        for (Map.Entry<ConsumerGroup, CGMetaData> entry : cgdatas.entrySet()) {
            ScaleDecision scaleDecision = BinPack.scaleDecisionEventConsumerWithLag(entry.getKey(), entry.getValue(), computeConsumer(entry.getValue()));
            recomputeAfterScaleDecision(graph, cgdatas, entry.getValue(), scaleDecision);
            decisions.put(entry.getKey(), scaleDecision);
        }
        try {
            logger.info("Pulled data from Prometheus : {}", objectWriter.writeValueAsString(cgdatas.values()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return decisions;
    }

    public void propagateArrivalRate(Graph<ConsumerGroup> graph, Map<ConsumerGroup, CGMetaData> cgdatas) {
        List<Vertex<ConsumerGroup>> roots = graph.roots();
        if (roots.isEmpty()) {
            roots.add(graph.topologicalSort().get(0));
        }
        for (CGMetaData data : cgdatas.values()) {
            data.resetParentalArrivalRate();
        }
        applyPropagationArrivalRate(roots, graph, cgdatas);
    }

    private void applyPropagationArrivalRate(List<Vertex<ConsumerGroup>> roots, Graph<ConsumerGroup> graph, Map<ConsumerGroup, CGMetaData> cgdatas) {
        List<Vertex<ConsumerGroup>> toVisit = new LinkedList<>(graph.topologicalSort());

        for (Vertex<ConsumerGroup> root : roots) {
            toVisit.remove(root);
            CGMetaData currentData = cgdatas.get(root.getGroup());
            double totalAR = getRootArrivalRate(currentData);
//            double laggingRate = currentData.getLag();
            for (BranchingFactor<ConsumerGroup> child : graph.getChildBranchingFactors(root)) {

                CGMetaData childData = cgdatas.get(child.getVertex().getGroup());
                childData.setParentArrivalRate(root.getGroup(), totalAR * child.getFactor());
//                childData.setPropagatedLag(root.getGroup(), currentData.getLag() * child.getFactor());
            }
        }

        while (!toVisit.isEmpty()) {
            Vertex<ConsumerGroup> current = toVisit.remove(0);
            computeParentArrivalRate(current, graph, cgdatas);
        }
    }

    private void computeParentArrivalRate(Vertex<ConsumerGroup> vertex,
                                          Graph<ConsumerGroup> graph,
                                          Map<ConsumerGroup, CGMetaData> cgdatas) {
        CGMetaData currentData = cgdatas.get(vertex.getGroup());
        double totalAR = getPropagatedParentalArrivalRate(currentData);
//        double laggingRate = currentData.getLag();

        for (BranchingFactor<ConsumerGroup> child : graph.getChildBranchingFactors(vertex)) {
            CGMetaData childData = cgdatas.get(child.getVertex().getGroup());
            childData.setParentArrivalRate(vertex.getGroup(), totalAR * child.getFactor());
//            childData.setPropagatedLag(vertex.getGroup(), currentData.getLag() * child.getFactor());
        }
    }

    protected abstract double getArrivalRateForPartitionCalculation(CGMetaData data, PartitionMetaData partitionMetaData);

    protected abstract double getRootArrivalRate(CGMetaData data);

    protected abstract double getPropagatedParentalArrivalRate(CGMetaData data);

    /**
     * This method is used to recompute any datas after scale decision in the downstream nodes.
     */
    protected void recomputeAfterScaleDecision(Graph<ConsumerGroup> graph, Map<ConsumerGroup, CGMetaData> cgdatas, CGMetaData targetMetaDataNode, ScaleDecision targetDecision) {
        // By default, do nothing. Override this method if you need to recompute any data after scale decision.
    }
}
