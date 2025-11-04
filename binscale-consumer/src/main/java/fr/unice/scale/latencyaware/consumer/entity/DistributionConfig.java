package fr.unice.scale.latencyaware.consumer.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class DistributionConfig {
    private List<ProducerTopicDistribution> output;

    public DistributionConfig() {
        this.output = new ArrayList<>();
    }

    @JsonCreator
    public DistributionConfig(@JsonProperty("output") List<ProducerTopicDistribution> output) {
        this.output = output;
    }

    public List<ProducerTopicDistribution> getOutput() {
        return output;
    }

    public void setOutput(List<ProducerTopicDistribution> output) {
        this.output = output;
    }
}
