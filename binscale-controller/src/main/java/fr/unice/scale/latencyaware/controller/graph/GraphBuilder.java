package fr.unice.scale.latencyaware.controller.graph;

import fr.unice.scale.latencyaware.controller.entity.ConsumerGroup;
import fr.unice.scale.latencyaware.controller.entity.distribution.GraphDistributionConfig;
import fr.unice.scale.latencyaware.controller.entity.graph.Graph;

public interface GraphBuilder {
    public Graph<ConsumerGroup> buildGraph(GraphDistributionConfig config);
}
