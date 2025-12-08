package fr.unice.scale.latencyaware.common.utils.prometheus.metrics;

public class SumMetricBuilder extends ByBuilder {
    private final static String SUM_METRIC_QUERY_PATTERN = "sum(#{metric})";

    private final String METRIC = "metric";

    SumMetricBuilder() {
        super();
    }

    public static SumMetricBuilder builder() {
        return new SumMetricBuilder();
    }

    public SumMetricBuilder metric(String metric) {
        this.params.put(METRIC, metric);
        return this;
    }

    public SumMetricBuilder metric(MetricBuilder metricBuilder) {
        this.params.put(METRIC, metricBuilder);
        return this;
    }

    @Override
    public String getFormulaPattern() {
        return SUM_METRIC_QUERY_PATTERN;
    }
}
