package fr.unice.scale.latencyaware.controller.processing;

import fr.unice.scale.latencyaware.controller.bin_pack.BinPack;
import fr.unice.scale.latencyaware.controller.entity.ConsumerGroup;
import fr.unice.scale.latencyaware.controller.entity.decision.ScaleDecision;
import fr.unice.scale.latencyaware.controller.entity.graph.BranchingFactor;
import fr.unice.scale.latencyaware.controller.entity.graph.Graph;
import fr.unice.scale.latencyaware.controller.entity.graph.Vertex;
import fr.unice.scale.latencyaware.controller.entity.meta_data.CGMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This scaler processor implements the binpack classic scaling strategy.
 * <br>
 * Include here the logic to propagate arrival rates through the graph for t+1.
 * <br><br>
 * <i>This implementation is based on M. Ezzeddine work </i>
 */
public class ClassicScalerProcessor implements ScalerProcessor {

    private final Logger logger = LoggerFactory.getLogger(ClassicScalerProcessor.class);

    @Override
    public Map<ConsumerGroup, ScaleDecision> process(Graph<ConsumerGroup> graph, Map<ConsumerGroup, CGMetaData> cgdatas) {
        logger.info("Starting ClassicScalerProcessor processing...");
        if (graph == null || cgdatas == null) {
            logger.warn("Graph or CGMetaData map is null. Returning empty decisions.");
            return new HashMap<>();
        }
        if (cgdatas.isEmpty()) {
            logger.warn("ConsumerGroupMetaData map is empty. No decisions taken.");
            return new HashMap<>();
        }
        propagateArrivalRate(graph, cgdatas);

        Map<ConsumerGroup, ScaleDecision> decisions = new HashMap<>();

        for (Map.Entry<ConsumerGroup, CGMetaData> entry : cgdatas.entrySet()) {
            decisions.put(entry.getKey(), BinPack.scaleDecisionEventConsumerWithLag(entry.getKey(), entry.getValue()));
        }
        logger.info("ClassicScalerProcessor processing completed.");
        return decisions;
    }

    public void propagateArrivalRate(Graph<ConsumerGroup> graph, Map<ConsumerGroup, CGMetaData> cgdatas) {
        logger.info("Propagating arrival rates through the graph...");
        List<Vertex<ConsumerGroup>> roots = graph.roots();
        if (roots.isEmpty()) {
            roots.add(graph.topologicalSort().get(0));
        }
        for (CGMetaData data : cgdatas.values()) {
            data.resetArrivalRate();
        }
        applyPropagationArrivalRate(roots, graph, cgdatas);

    }

    protected void applyPropagationArrivalRate(List<Vertex<ConsumerGroup>> roots, Graph<ConsumerGroup> graph, Map<ConsumerGroup, CGMetaData> cgdatas) {
        logger.info("Applying arrival rate propagation...");
        //trusted topological sorted
        List<Vertex<ConsumerGroup>> toVisit = new LinkedList<>(graph.topologicalSort());

        for (Vertex<ConsumerGroup> root : roots) {
            toVisit.remove(root);
            CGMetaData currentData = cgdatas.get(root.getGroup());
            double totalAR = currentData.getTotalArrivalRate();
            // To facilitate calculations for root, totalArrivalRate = parentArrivalRate
            currentData.setParentArrivalRate(totalAR);
            double avgLT = currentData.getAvgEventProcessingRate();
            for (BranchingFactor<ConsumerGroup> child : graph.getChildBranchingFactors(root)) {
                CGMetaData childData = cgdatas.get(child.getVertex().getGroup());
                childData.addParentArrivalRate((totalAR + avgLT) * child.getFactor()); // (ArrivalRate(parent) + Lag(parent)) * BF(parent->child)
            }
        }

        while (!toVisit.isEmpty()) {
            Vertex<ConsumerGroup> current = toVisit.remove(0);
            computeParentArrivalRate(current, graph, cgdatas);
        }
    }

    protected void computeParentArrivalRate(Vertex<ConsumerGroup> vertex,
                                            Graph<ConsumerGroup> graph,
                                            Map<ConsumerGroup, CGMetaData> cgdatas) {
        CGMetaData currentData = cgdatas.get(vertex.getGroup());
        double totalAR = currentData.getParentArrivalRate();
        double avgLT = currentData.getAvgEventProcessingRate();

        for (BranchingFactor<ConsumerGroup> child : graph.getChildBranchingFactors(vertex)) {
            CGMetaData childData = cgdatas.get(child.getVertex().getGroup());
            childData.addParentArrivalRate((totalAR + avgLT) * child.getFactor()); // (ParentArrivalRate(parent) + Lag(parent)) * BF(parent->child)
        }
    }
}
