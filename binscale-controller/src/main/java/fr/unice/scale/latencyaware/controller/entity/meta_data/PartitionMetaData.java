package fr.unice.scale.latencyaware.controller.entity.meta_data;

import fr.unice.scale.latencyaware.controller.entity.Partition;

import java.util.HashMap;
import java.util.Map;

import static fr.unice.scale.latencyaware.common.constant.CommonVariables.EXTERNAL_GROUP_NAME;

public class PartitionMetaData {
    private final double REBALANCING_TIME;
    private long lag;
    private Map<String, Double> arrivalRate;
    private double processingTime;
    private double processingCount;
    private double latency;
    private Partition partition;

    public PartitionMetaData(Partition partition, double rebalancingTime) {
        this.partition = partition;
        this.lag = 0;
        this.arrivalRate = new HashMap<>();
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

    public double getProcessingRate() {
        if (processingCount == 0) {
            return 0.0;
        }
        return processingTime / processingCount;
    }

    public double getLagRebalancing() {
        return getProcessingRate() * REBALANCING_TIME;
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

    public Map<String, Double> getArrivalRate() {
        return arrivalRate;
    }

    public void setArrivalRate(Map<String, Double> arrivalRate) {
        this.arrivalRate = arrivalRate;
    }

    public void putArrivalRate(String providerId, Double value) {
        arrivalRate.put(providerId, value);
    }

    public double getAvgArrivalRate() {
        if (arrivalRate.isEmpty()) {
            return 0.0;
        }
        return arrivalRate.values().stream().reduce(0.0, Double::sum) / arrivalRate.size();
    }

    public Double getTotalExternalArrivalRate() {
        return arrivalRate.getOrDefault(EXTERNAL_GROUP_NAME, 0.0);
    }

    public Double getTotalInternalArrivalRate() {
        return arrivalRate.values().stream().reduce(0.0, Double::sum) - getTotalExternalArrivalRate();
    }

    public Double getTotalInputArrivalRate() {
        return arrivalRate.values().stream().reduce(0.0, Double::sum);
    }

    @Override
    public int hashCode() {
        int result = 0;
        long temp;
        result = 31 * result + (int) (lag ^ (lag >>> 32));
        // TODO
//        temp = Double.doubleToLongBits(arrivalRate);
//        result = 31 * result + (int) (temp ^ (temp >>> 32));
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
