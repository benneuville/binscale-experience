package fr.unice.scale.latencyaware.controller.processing;

import fr.unice.scale.latencyaware.controller.constant.Action;
import fr.unice.scale.latencyaware.controller.entity.ConsumerGroup;
import fr.unice.scale.latencyaware.controller.entity.decision.ScaleDecision;
import fr.unice.scale.latencyaware.controller.entity.graph.BranchingFactor;
import fr.unice.scale.latencyaware.controller.entity.graph.Graph;
import fr.unice.scale.latencyaware.controller.entity.graph.Vertex;
import fr.unice.scale.latencyaware.controller.entity.meta_data.CGMetaData;
import fr.unice.scale.latencyaware.controller.entity.meta_data.PartitionMetaData;

import java.util.Map;

public class UpscalePropagateLagInSARClassicScalerProcessor extends SeparateArrivalRateClassicScalerProcessor {

    /**
     * This method is used to compute and repropagate the lag of the target consumer group to each of its children and applying this with the Branching Factor
     *
     * @param graph
     * @param cgdatas
     * @param sourceMetaDataNode
     * @param targetDecision
     */
    @Override
    protected void recomputeAfterScaleDecision(Graph<ConsumerGroup> graph, Map<ConsumerGroup, CGMetaData> cgdatas, CGMetaData sourceMetaDataNode, ScaleDecision targetDecision) {
        if (targetDecision.getAction().equals(Action.UP)) {
            recursivePropagateLag(graph, cgdatas, sourceMetaDataNode, sourceMetaDataNode, sourceMetaDataNode.getLag());
        }
    }

    private void propagateLag(Graph<ConsumerGroup> graph, Map<ConsumerGroup, CGMetaData> cgdatas, CGMetaData sourceMetaDataNode, CGMetaData currentMetaDataNode, double lag) {
        recursivePropagateLag(graph, cgdatas, sourceMetaDataNode, currentMetaDataNode, lag);
    }

    private void recursivePropagateLag(Graph<ConsumerGroup> graph, Map<ConsumerGroup, CGMetaData> cgdatas, CGMetaData sourceMetaDataNode, CGMetaData currentMetaDataNode, double lag) {
        for (Vertex<ConsumerGroup> child : graph.getChildVertices(currentMetaDataNode.getConsumerGroup().getConsumerName())) {
            CGMetaData childData = cgdatas.get(child.getGroup());
            BranchingFactor<ConsumerGroup> bf = graph.getBranchingFactor(
                            graph.getVertex(currentMetaDataNode.getConsumerGroup().getGroupName()), child)
                    .orElse(new BranchingFactor<>(null, 0.));
            logger.info("Propagating lag from {} to {} with branching factor {}", currentMetaDataNode.getConsumerGroup().getConsumerName(), child.getGroup().getConsumerName(), bf.getFactor());
            double propagatedLag = lag * bf.getFactor();

            childData.setPropagatedLag(sourceMetaDataNode.getConsumerGroup(), propagatedLag);
            propagateLag(graph, cgdatas, sourceMetaDataNode, childData, propagatedLag);
        }
    }

    @Override
    protected double getArrivalRateForPartitionCalculation(CGMetaData data, PartitionMetaData partitionMetaData) {
        return data.getAvgParentArrivalRate() + partitionMetaData.getTotalExternalArrivalRate() + data.getAvgMaxPropagatedLag();
    }
}
