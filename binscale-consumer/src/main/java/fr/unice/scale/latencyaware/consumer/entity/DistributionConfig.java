package fr.unice.scale.latencyaware.consumer.entity;

import java.util.ArrayList;
import java.util.List;

public class DistributionConfig {
    private List<ProducerTopicDistribution> outputTopics;

    public DistributionConfig() {
        this.outputTopics = new ArrayList<>();
    }

    public DistributionConfig(List<ProducerTopicDistribution> outputTopics) {
        this.outputTopics = outputTopics;
    }

    public List<ProducerTopicDistribution> getOutputTopics() {
        return outputTopics;
    }

    public void setOutputTopics(List<ProducerTopicDistribution> outputTopics) {
        this.outputTopics = outputTopics;
    }
}
