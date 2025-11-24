package fr.unice.scale.latencyaware.controller.entity.meta_data;

import fr.unice.scale.latencyaware.controller.entity.ConsumerGroup;
import fr.unice.scale.latencyaware.controller.entity.Partition;

import java.util.HashMap;
import java.util.Map;

public class CGMetaData {
    private ConsumerGroup consumerGroup;

    private Map<Partition, PartitionMetaData> partitionsMetaData = new HashMap<>();

    private long processingRate = 0;

    private double parentArrivalRate = 0;

    public CGMetaData(ConsumerGroup consumerGroup) {
        this.consumerGroup = consumerGroup;
        for (Partition p : consumerGroup.getTopicPartitions()) {
            partitionsMetaData.put(p, new PartitionMetaData(p));
        }
    }

    public long getProcessingRate() {
        return processingRate;
    }

    public void setProcessingRate(long processingRate) {
        this.processingRate = processingRate;
    }

    public double getParentArrivalRate() {
        return parentArrivalRate;
    }

    public void setParentArrivalRate(double parentArrivalRate) {
        this.parentArrivalRate = parentArrivalRate;
    }

    public void addParentArrivalRate(double arrivalRate) {
        this.parentArrivalRate += arrivalRate;
    }

    public ConsumerGroup getConsumerGroup() {
        return consumerGroup;
    }

    public void setConsumerGroup(ConsumerGroup consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public Map<Partition, PartitionMetaData> getPartitionsMetaData() {
        return partitionsMetaData;
    }

    public void setPartitionsMetaData(Map<Partition, PartitionMetaData> partitionsMetaData) {
        this.partitionsMetaData = partitionsMetaData;
    }

    public PartitionMetaData getPartitionMetaData(Partition partition) {
        return partitionsMetaData.get(partition);
    }

    public PartitionMetaData getPartitionMetaData(int partitionId) {
        for (Partition p : partitionsMetaData.keySet()) {
            if (p.getId() == partitionId) {
                return partitionsMetaData.get(p);
            }
        }
        return null;
    }

    public void setPartitionMetaData(Partition partition, PartitionMetaData metaData) {
        this.partitionsMetaData.put(partition, metaData);
    }

    public double getTotalArrivalRate() {
        return this.partitionsMetaData.values().stream().map(PartitionMetaData::getArrivalRate).reduce(0.0, Double::sum);
    }

    public long getAvgLatency() {
        return this.partitionsMetaData.values().stream().map(PartitionMetaData::getLag).reduce(0L, Long::sum) / this.partitionsMetaData.size();
    }

    public void resetArrivalRate() {
        this.parentArrivalRate = 0;
    }

    public double getMaxLagCapacity() {
        return this.getAvgLatency() * this.getConsumerGroup().getWsla() * this.getConsumerGroup().getFup();
    }

    public double getMaxAverageConsumptionRate() {
        return this.getAvgLatency() * this.getConsumerGroup().getFup();
    }

    public double getMinLagCapacity() {
        return this.getAvgLatency() * this.getConsumerGroup().getWsla() * this.getConsumerGroup().getFdown();
    }

    public double getMinAverageConsumptionRate() {
        return this.getAvgLatency() * this.getConsumerGroup().getFdown();
    }

    @Override
    public String toString() {
        return "CGMetaData{" +
                "consumerGroup=" + consumerGroup +
                ", partitionsMetaData=" + partitionsMetaData +
                ", processingRate=" + processingRate +
                ", parentArrivalRate=" + parentArrivalRate +
                '}';
    }
}
