package fr.unice.scale.latencyaware.controller.entity.metric;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ArrivalRatePerProviderMetric extends DoubleMetric {
    private String providerGroupId;

    public ArrivalRatePerProviderMetric(@JsonProperty("groupId") String groupId, @JsonProperty("metric") String metric, @JsonProperty("timestamp") long timestamp) {
        super(timestamp, metric);
        this.providerGroupId = groupId;
    }

    public ArrivalRatePerProviderMetric() {
    }

    public String getProviderGroupId() {
        return providerGroupId;
    }
}
