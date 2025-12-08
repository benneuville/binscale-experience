package fr.unice.scale.latencyaware.controller.entity.meta_data;

import fr.unice.scale.latencyaware.controller.entity.Partition;

public class PartitionMetaData {
    private long lag;
    private double arrivalRate;
    private double processingTime;
    private double processingCount;
    private double latency;
    private Partition partition;
    private final double REBALANCING_TIME;

    public PartitionMetaData(Partition partition, double rebalancingTime) {
        this.partition = partition;
        this.lag = 0;
        this.arrivalRate = 0.0;
        this.REBALANCING_TIME = rebalancingTime;
    }

    public double getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(double processingTime) {
        this.processingTime = processingTime;
    }

    public double getProcessingCount() {
        return processingCount;
    }

    public void setProcessingCount(double processingCount) {
        this.processingCount = processingCount;
    }

    public double getProcessingCapacity() {
        if (processingCount == 0) {
            return 0.0;
        }
        return processingTime / processingCount;
    }

    public double getLagRebalancing() {
        return getProcessingCapacity() * REBALANCING_TIME;
    }

    public double getLatency() {
        return latency;
    }

    public void setLatency(double latency) {
        this.latency = latency;
    }

    public Partition getPartition() {
        return partition;
    }

    public long getLag() {
        return lag;
    }

    public void setLag(long lag) {
        this.lag = lag;
    }

    public double getArrivalRate() {
        return arrivalRate;
    }

    public void setArrivalRate(double arrivalRate) {
        this.arrivalRate = arrivalRate;
    }

    @Override
    public int hashCode() {
        int result = 0;
        long temp;
        result = 31 * result + (int) (lag ^ (lag >>> 32));
        temp = Double.doubleToLongBits(arrivalRate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "PartitionMetaData{" +
                "partition=" + partition.getId() +
                ", lag=" + lag +
                ", arrivalRate=" + arrivalRate +
                ", latency=" + latency +
                ", processingTime=" + processingTime +
                ", processingCount=" + processingCount +
                '}';
    }
}
