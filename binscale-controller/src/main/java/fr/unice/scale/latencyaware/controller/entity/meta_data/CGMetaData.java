package fr.unice.scale.latencyaware.controller.entity.meta_data;

import fr.unice.scale.latencyaware.controller.entity.Consumer;
import fr.unice.scale.latencyaware.controller.entity.ConsumerGroup;
import fr.unice.scale.latencyaware.controller.entity.Partition;

import java.util.HashMap;
import java.util.Map;

public class CGMetaData {
    private ConsumerGroup consumerGroup;

    private Map<Partition, PartitionMetaData> partitionsMetaData = new HashMap<>();
    private Map<Consumer, ConsumerMetaData> consumersMetaData = new HashMap<>();
    private double parentArrivalRate = 0.0;

    public CGMetaData(ConsumerGroup consumerGroup, double rebalancingTime) {
        this.consumerGroup = consumerGroup;
        for (Partition p : consumerGroup.getTopicPartitions()) {
            partitionsMetaData.put(p, new PartitionMetaData(p, rebalancingTime));
        }

        for (Consumer c : consumerGroup.getAssignment()) {
            consumersMetaData.put(c, new ConsumerMetaData(c, 0)); //TODO give the dynamic processing capacity
        }
    }

    public double getLag() { // lag requested by partition is the rate during DI
        return partitionsMetaData.values().stream().map(PartitionMetaData::getLag).reduce(0L, Long::sum);
    }

    public double getAvgEventProcessingRate() {
        double totalProcessingRate = 0.0;

        for (Consumer c : consumerGroup.getAssignment()) {
            double consumerProcessingCount = 0.0;
            double consumerProcessingSum = 0.0;
            for (Partition p : c.getAssignedPartitions()) {
                PartitionMetaData pMetaData = partitionsMetaData.get(p);
                consumerProcessingSum += pMetaData.getProcessingTime();
                consumerProcessingCount += pMetaData.getProcessingCount();
            }
            if (consumerProcessingCount > 0) {
                double consumerAvgProcessingRate = consumerProcessingSum / consumerProcessingCount;
                totalProcessingRate += consumerAvgProcessingRate;
            }
        }
        return totalProcessingRate / consumerGroup.getAssignment().size();
    }

    public double getAvgTotalInputArrivalRate() {
        if (partitionsMetaData.values().isEmpty()) {
            return 0.0;
        }
        return getTotalInputArrivalRate() / partitionsMetaData.size();
    }

    public double getAvgTotalExternalArrivalRate() {
        if (partitionsMetaData.values().isEmpty()) {
            return 0.0;
        }
        return getTotalExternalArrivalRate() / partitionsMetaData.size();
    }

    public double getAvgLag() {
        if (partitionsMetaData.values().isEmpty()) {
            return 0.0;
        }
        return getLag() / partitionsMetaData.size();
    }

    public Map<Consumer, ConsumerMetaData> getConsumersMetaData() {
        return consumersMetaData;
    }

    public void setConsumersMetaData(Map<Consumer, ConsumerMetaData> consumersMetaData) {
        this.consumersMetaData = consumersMetaData;
    }

    public ConsumerMetaData getConsumerMetaData(Consumer consumer) {
        return consumersMetaData.get(consumer);
    }

    public double getParentArrivalRate() {
        return parentArrivalRate;
    }

    public void setParentArrivalRate(double parentArrivalRate) {
        this.parentArrivalRate = parentArrivalRate;
    }

    public double getAvgParentArrivalRate() {
        if (partitionsMetaData.values().isEmpty()) {
            return 0.0;
        }
        return parentArrivalRate / partitionsMetaData.size();
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

    public Double getTotalInputArrivalRate() {
        return this.partitionsMetaData.values().stream().map(PartitionMetaData::getTotalInputArrivalRate).reduce(0.0, Double::sum);
    }

    public Double getTotalExternalArrivalRate() {
        return this.partitionsMetaData.values().stream().map(PartitionMetaData::getTotalExternalArrivalRate).reduce(0.0, Double::sum);
    }

    public void resetParentalArrivalRate() {
        this.parentArrivalRate = 0.0;
    }

    public double getMaxLagCapacity() {
        return this.consumerGroup.getMaxDefinedProcessingRate() * this.getConsumerGroup().getWsla() * this.getConsumerGroup().getFup();
    }

    public double getMaxAverageArrivalRate() {
        return this.consumerGroup.getMaxDefinedProcessingRate() * this.getConsumerGroup().getFup();
    }

    public double getMinLagCapacity() {
        return this.consumerGroup.getMaxDefinedProcessingRate() * this.getConsumerGroup().getWsla() * this.getConsumerGroup().getFdown();
    }

    public double getMinAverageArrivalRate() {
        return this.consumerGroup.getMaxDefinedProcessingRate() * this.getConsumerGroup().getFdown();
    }

    @Override
    public String toString() {
        return "ConsumerGroupMetaData{" +
                "consumerGroup=" + consumerGroup.getGroupName() +
                ", partitionsMetaData=" + partitionsMetaData +
                ", consumersMetaData=" + consumersMetaData +
                ", parentArrivalRate=" + parentArrivalRate +
                '}';
    }
}
