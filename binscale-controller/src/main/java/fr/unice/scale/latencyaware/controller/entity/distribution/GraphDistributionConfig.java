package fr.unice.scale.latencyaware.controller.entity.distribution;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class GraphDistributionConfig {
    private List<EdgeDistribution> topics;

    private List<NodeDistribution> nodes;

    public GraphDistributionConfig() {
        this.topics = new ArrayList<>();
    }

    @JsonCreator
    public GraphDistributionConfig(@JsonProperty("topics") List<EdgeDistribution> topics,
                                   @JsonProperty("nodes") List<NodeDistribution> nodes) {
        this.topics = topics;
        this.nodes = nodes;
    }

    public List<EdgeDistribution> getEdges() {
        return topics;
    }


    public void setEdges(List<EdgeDistribution> topics) {
        this.topics = topics;
    }

    public List<NodeDistribution> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodeDistribution> nodes) {
        this.nodes = nodes;
    }
}
