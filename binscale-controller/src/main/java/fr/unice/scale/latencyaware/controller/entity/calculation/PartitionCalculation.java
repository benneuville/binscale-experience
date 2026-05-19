package fr.unice.scale.latencyaware.controller.entity.calculation;

import fr.unice.scale.latencyaware.controller.entity.Partition;
import fr.unice.scale.latencyaware.controller.entity.meta_data.PartitionMetaData;

public class PartitionCalculation implements Comparable<PartitionCalculation> {
    private final Partition partition;

    private final double lag;
    private final double arrivalRate;
    private final double lagRebalancing;
    private double maxLagCapacity; // upscale
    private double minLagCapacity; // downscale
    private double maxArrivalRate; // upscale
    private double minArrivalRate; // downscale

    // For non-root
    public PartitionCalculation(PartitionMetaData metaData, double arrivalRate) {
        this.partition = metaData.getPartition();
        this.lag = metaData.getLag();
        this.arrivalRate = arrivalRate;
        this.lagRebalancing = metaData.getLagRebalancing();
    }

    public double getMaxArrivalRate() {
        return maxArrivalRate;
    }

    public void setMaxArrivalRate(double maxArrivalRate) {
        this.maxArrivalRate = maxArrivalRate;
    }

    public double getMinArrivalRate() {
        return minArrivalRate;
    }

    public void setMinArrivalRate(double minArrivalRate) {
        this.minArrivalRate = minArrivalRate;
    }

    public double getLagRebalancing() {
        return lagRebalancing;
    }

    public Partition getPartition() {
        return partition;
    }

    public double getIndexedLagCapacityUpScale() {
        return Math.min(lag + lagRebalancing, maxLagCapacity);
    }

    public double getIndexedLagCapacityDownScale() {
        return Math.min(lag + lagRebalancing, minLagCapacity);
    }

    public double getIndexedArrivalRateUpScale() {
        return Math.min(arrivalRate, maxArrivalRate);
    }

    public double getIndexedArrivalRateDownScale() {
        return Math.min(arrivalRate, minArrivalRate);
    }

    public double getLag() {
        return lag;
    }

    public double getArrivalRate() {
        return arrivalRate;
    }

    public double getMaxLagCapacity() {
        return maxLagCapacity;
    }

    public void setMaxLagCapacity(double maxLagCapacity) {
        this.maxLagCapacity = maxLagCapacity;
    }

    public double getMinLagCapacity() {
        return minLagCapacity;
    }

    public void setMinLagCapacity(double minLagCapacity) {
        this.minLagCapacity = minLagCapacity;
    }

    @Override
    public int compareTo(PartitionCalculation o) {
        return Double.compare(this.getArrivalRate(), o.getArrivalRate());
    }

    @Override
    public String toString() {
        return partition.getId() + " : arrivalRate = " + getArrivalRate() + ", lag = " + getLag();
    }
}
