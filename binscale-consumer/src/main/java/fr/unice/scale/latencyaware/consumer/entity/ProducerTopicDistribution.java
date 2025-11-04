package fr.unice.scale.latencyaware.consumer.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProducerTopicDistribution {
    private String name;
    private float ratio;

    public ProducerTopicDistribution() {
    }

    @JsonCreator
    public ProducerTopicDistribution(
            @JsonProperty("name") String name,
            @JsonProperty("ratio") float ratio
    ) {
        this.name = name;
        this.ratio = ratio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    @Override
    public String toString() {
        return "ProducerTopicDistribution{" +
                "name='" + name + '\'' +
                ", ratio=" + ratio +
                '}';
    }
}
