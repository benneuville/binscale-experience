package fr.unice.scale.latencyaware.controller.graph;

import fr.unice.scale.latencyaware.controller.entity.ConsumerGroup;
import fr.unice.scale.latencyaware.controller.entity.distribution.GraphDistributionConfig;
import fr.unice.scale.latencyaware.controller.entity.distribution.NodeDistribution;
import fr.unice.scale.latencyaware.controller.entity.graph.Graph;

public class GraphBuilderImpl implements GraphBuilder {
    @Override
    public Graph<ConsumerGroup> buildGraph(GraphDistributionConfig config) {
        Graph<ConsumerGroup> graph = new Graph<>();
        for (NodeDistribution node : config.getNodes()) {
            graph.addVertex(node.getName(),
                    new ConsumerGroup(
                            node.getInputTopic(),
                            node.getMaxConsumptionRate(),
                            node.getWsla(),
                            node.getName(),
                            node.getGroupId(),
                            node.getPartitionNumber()
                    ));
        }
        config.getEdges().forEach((e) -> graph.addEdge(e.getFrom(), e.getTo(), e.getWeight()));

        return graph;
    }
}
