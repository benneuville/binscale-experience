package fr.unice.scale.latencyaware.controller.entity.distribution;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NodeDistribution {
    private String name;
    private double wsla;
    private double maxConsumptionRate;
    private String inputTopic;
    private int partitionNumber;
    private String groupId;

    public NodeDistribution(@JsonProperty("name") String name,
                            @JsonProperty("wsla") double wsla,
                            @JsonProperty("maxConsumptionRate") double maxConsumptionRate,
                            @JsonProperty("inputTopic") String inputTopic,
                            @JsonProperty("partitionNumber") int partitionNumber,
                            @JsonProperty("groupId") String groupId) {
        this.name = name;
        //given in milliseconds, converted in seconds
        this.wsla = wsla / 1000.0;
        this.maxConsumptionRate = maxConsumptionRate;
        this.inputTopic = inputTopic;
        this.partitionNumber = partitionNumber;
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWsla() {
        return wsla;
    }

    public void setWsla(double wsla) {
        this.wsla = wsla;
    }

    public double getMaxConsumptionRate() {
        return maxConsumptionRate;
    }

    public void setMaxConsumptionRate(double maxConsumptionRate) {
        this.maxConsumptionRate = maxConsumptionRate;
    }

    public String getInputTopic() {
        return inputTopic;
    }

    public void setInputTopic(String inputTopic) {
        this.inputTopic = inputTopic;
    }

    public int getPartitionNumber() {
        return partitionNumber;
    }

    public void setPartitionNumber(int partitionNumber) {
        this.partitionNumber = partitionNumber;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
