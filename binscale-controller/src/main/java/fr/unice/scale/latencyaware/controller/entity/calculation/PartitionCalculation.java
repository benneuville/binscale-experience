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
    private double maxProcessingCapacity; // upscale
    private double minProcessingCapacity; // downscale
    private double maxArrivalRate; // upscale
    private double minArrivalRate; // downscale

    public PartitionCalculation(PartitionMetaData metaData) {
        this.partition = metaData.getPartition();
        this.lag = metaData.getLag();
        this.arrivalRate = metaData.getArrivalRate();
        this.lagRebalancing = metaData.getLagRebalancing();
    }

    public PartitionCalculation(Partition partition, PartitionMetaData metaData) {
        this.partition = partition;
        this.lag = metaData.getLag();
        this.arrivalRate = metaData.getArrivalRate();
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

    public double getMaxProcessingCapacity() {
        return maxProcessingCapacity;
    }

    public void setMaxProcessingCapacity(double maxProcessingCapacity) {
        this.maxProcessingCapacity = maxProcessingCapacity;
    }

    public double getMinProcessingCapacity() {
        return minProcessingCapacity;
    }

    public void setMinProcessingCapacity(double minProcessingCapacity) {
        this.minProcessingCapacity = minProcessingCapacity;
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
}
