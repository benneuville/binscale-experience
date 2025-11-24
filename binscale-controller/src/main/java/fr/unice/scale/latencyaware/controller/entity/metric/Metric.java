package fr.unice.scale.latencyaware.controller.entity.metric;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.function.Function;

public abstract class Metric {
    private Long timestamp;
    private String metric;

    public Metric(@JsonProperty("timestamp") Long timestamp, @JsonProperty("metric") String metric) {
        this.timestamp = timestamp;
        this.metric = metric;
    }

    public Metric() {
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public <T> T getMetricAs(Function<String, T> parser) {
        return parser.apply(metric);
    }

    @Override
    public String toString() {
        return "Metric{" +
                "timestamp=" + timestamp +
                ", metric='" + metric + '\'' +
                '}';
    }
}
