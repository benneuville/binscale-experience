package fr.unice.scale.latencyaware.controller.entity.metric;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DoubleMetric extends Metric {

    public DoubleMetric() {
        super();
    }

    public DoubleMetric(@JsonProperty("timestamp") Long timestamp, @JsonProperty("metric") String metric) {
        super(timestamp, metric);
    }

    public Double getValue() {
        return Double.parseDouble(getMetric());
    }
}
