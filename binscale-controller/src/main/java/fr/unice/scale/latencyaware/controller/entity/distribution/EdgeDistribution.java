package fr.unice.scale.latencyaware.controller.entity.distribution;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EdgeDistribution {
    private String from;
    private String to;
    private Double weight;

    @JsonCreator
    public EdgeDistribution(
            @JsonProperty("from") String from,
            @JsonProperty("to") String to,
            @JsonProperty("weight") Double weight
    ) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public Double getWeight() {
        return weight;
    }
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "ProducerTopicDistribution{" +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", weights=" + weight +
                '}';
    }
}
