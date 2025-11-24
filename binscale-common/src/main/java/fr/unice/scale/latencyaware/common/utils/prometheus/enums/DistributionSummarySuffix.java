package fr.unice.scale.latencyaware.common.utils.prometheus.enums;

public enum DistributionSummarySuffix {
    COUNT("_count"),
    SUM("_sum"),
    MAX("_max");

    private final String suffix;

    DistributionSummarySuffix(String value) {
        this.suffix = value;
    }

    public String suffix() {
        return suffix;
    }
}