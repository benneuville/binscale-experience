package fr.unice.scale.latencyaware.common.utils.prometheus.metrics;

import fr.unice.scale.latencyaware.common.utils.prometheus.PrometheusBuilder;

public class AverageMetricBuilder extends PrometheusBuilder {

    private final static String AVERAGE_METRIC_QUERY_PATTERN = "avg(#{metric})";

    private final String METRIC = "metric";

    public AverageMetricBuilder() {
        super();
    }

    public static AverageMetricBuilder builder() {
        return new AverageMetricBuilder();
    }

    public AverageMetricBuilder metric(String metric) {
        this.params.put(METRIC, metric);
        return this;
    }

    public AverageMetricBuilder metric(MetricBuilder metricBuilder) {
        this.params.put(METRIC, metricBuilder);
        return this;
    }

    public AverageMetricBuilder metric(RateMetricQueryBuilder rateMetricBuilder) {
        this.params.put(METRIC, rateMetricBuilder);
        return this;
    }

    @Override
    public String getPattern() {
        return AVERAGE_METRIC_QUERY_PATTERN;
    }
}
