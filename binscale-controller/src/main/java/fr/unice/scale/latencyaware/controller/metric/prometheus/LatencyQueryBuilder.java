package fr.unice.scale.latencyaware.controller.metric.prometheus;

import fr.unice.scale.latencyaware.common.utils.prometheus.PrometheusBuilder;
import fr.unice.scale.latencyaware.common.utils.prometheus.SimpleQueryBuilder;

public class LatencyQueryBuilder extends SimpleQueryBuilder {
    protected static final String URI_LATENCY_PATTERN = "1000/(#{numerator}/#{denominator})";

    private final String NUMERATOR = "numerator";
    private final String DENOMINATOR = "denominator";

    public LatencyQueryBuilder() {
        super();
    }

    public static LatencyQueryBuilder builder() {
        return new LatencyQueryBuilder();
    }

    public LatencyQueryBuilder numerator(String numerator) {
        this.params.put(NUMERATOR, numerator);
        return this;
    }

    public LatencyQueryBuilder numerator(PrometheusBuilder numeratorBuilder) {
        this.params.put(NUMERATOR, numeratorBuilder);
        return this;
    }

    public LatencyQueryBuilder denominator(String denominator) {
        this.params.put(DENOMINATOR, denominator);
        return this;
    }

    public LatencyQueryBuilder denominator(PrometheusBuilder denominatorBuilder) {
        this.params.put(DENOMINATOR, denominatorBuilder);
        return this;
    }

    @Override
    public String getPattern() {
        return URI_LATENCY_PATTERN;
    }
}
