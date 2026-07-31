package fr.unice.scale.latencyaware.common.utils.prometheus.metrics;

import fr.unice.scale.latencyaware.common.utils.prometheus.PrometheusBuilder;

public class CounterMetricQueryBuilder extends PrometheusBuilder {
    private final static String COUNTER_METRIC_QUERY_PATTERN = "#{metric}_total";
    private final String METRIC = "metric";

    public static CounterMetricQueryBuilder builder() {
        return new CounterMetricQueryBuilder();
    }

    public CounterMetricQueryBuilder metric(String name) {
        this.params.put(METRIC, name);
        return this;
    }

    @Override
    public String getPattern() {
        return COUNTER_METRIC_QUERY_PATTERN;
    }
}
