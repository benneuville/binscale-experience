package fr.unice.scale.latencyaware.controller.entity.metric;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ArrivalRatePerProviderMetric extends DoubleMetric {
    private String providerGroupId;

    public ArrivalRatePerProviderMetric(@JsonProperty("provider_group_id") String providerGroupId, @JsonProperty("metric") String metric, @JsonProperty("timestamp") long timestamp) {
        super(timestamp, metric);
        this.providerGroupId = providerGroupId;
    }

    public ArrivalRatePerProviderMetric() {
    }

    public String getProviderGroupId() {
        return providerGroupId;
    }
}
