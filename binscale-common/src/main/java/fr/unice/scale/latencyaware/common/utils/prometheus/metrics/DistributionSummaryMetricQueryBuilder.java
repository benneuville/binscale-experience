package fr.unice.scale.latencyaware.common.utils.prometheus.metrics;

import fr.unice.scale.latencyaware.common.utils.prometheus.PrometheusBuilder;
import fr.unice.scale.latencyaware.common.utils.prometheus.enums.DistributionSummarySuffix;

public class DistributionSummaryMetricQueryBuilder extends PrometheusBuilder {

    private final static String DISTRIBUTION_SUMMARY_METRIC_QUERY_PATTERN = "#{metric}#{suffix}";
    private final String METRIC = "metric";
    private final String SUFFIX = "suffix";

    public DistributionSummaryMetricQueryBuilder(DistributionSummarySuffix suffix) {
        super();
        this.params.put(SUFFIX, suffix.suffix());
    }

    public DistributionSummaryMetricQueryBuilder(QuantileSuffix suffix) {
        super();
        this.params.put(SUFFIX, suffix);
    }

    public DistributionSummaryMetricQueryBuilder() {
        super();
        this.params.put(SUFFIX, "");
    }

    public static DistributionSummaryMetricQueryBuilder builder() {
        return new DistributionSummaryMetricQueryBuilder();
    }

    public DistributionSummaryMetricQueryBuilder metric(MetricBuilder metricBuilder) {
        this.params.put(METRIC, metricBuilder);
        return this;
    }

    public DistributionSummaryMetricQueryBuilder suffix(DistributionSummarySuffix suffix) {
        this.params.put(SUFFIX, suffix.suffix());
        return this;
    }

    public DistributionSummaryMetricQueryBuilder suffix(QuantileSuffix suffix) {
        this.params.put(SUFFIX, suffix);
        return this;
    }

    @Override
    public String getPattern() {
        return DISTRIBUTION_SUMMARY_METRIC_QUERY_PATTERN;
    }

    public static class QuantileSuffix extends PrometheusBuilder {
        private final static String QUANTILE_SUFFIX_PATTERN = "{quantile=\"#{quantile}\"}";

        private final String QUANTILE = "quantile";

        public QuantileSuffix(double quantile) {
            super();
            this.params.put(QUANTILE, quantile);
        }

        public QuantileSuffix quantile(double quantile) {
            this.params.put(QUANTILE, quantile);
            return this;
        }

        @Override
        public String getPattern() {
            return QUANTILE_SUFFIX_PATTERN;
        }
    }
}
