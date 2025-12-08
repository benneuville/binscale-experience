package fr.unice.scale.latencyaware.common.utils.prometheus.metrics;

import fr.unice.scale.latencyaware.common.utils.prometheus.PrometheusBuilder;

public class RateMetricQueryBuilder extends ByBuilder {
    private final static String RATE_METRIC_QUERY_PATTERN = "rate(#{metric})";

    private final String METRIC = "metric";

    public RateMetricQueryBuilder() {
        super();
    }

    @Override
    public String getFormulaPattern() {
        return RATE_METRIC_QUERY_PATTERN;
    }

    public static RateMetricQueryBuilder builder() {
        return new RateMetricQueryBuilder();
    }

    public RateMetricQueryBuilder metric(String metric) {
        this.params.put(METRIC, metric);
        return this;
    }

    public RateMetricQueryBuilder metric(PrometheusBuilder metricBuilder) {
        this.params.put(METRIC, metricBuilder);
        return this;
    }
}
