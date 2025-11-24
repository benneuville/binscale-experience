package fr.unice.scale.latencyaware.controller.entity.calculation;

import fr.unice.scale.latencyaware.controller.entity.Partition;
import fr.unice.scale.latencyaware.controller.entity.meta_data.PartitionMetaData;

public class PartitionCalculation implements Comparable<PartitionCalculation> {
    private final Partition partition;

    private final double lag;
    private final double arrivalRate;
    private double maxLagCapacity; // upscale
    private double minLagCapacity; // downscale
    private double maxConsumptionRate; // upscale
    private double minConsumptionRate; // downscale

    public PartitionCalculation(Partition partition, double lag, double arrivalRate) {
        this.partition = partition;
        this.lag = lag;
        this.arrivalRate = arrivalRate;
    }

    public PartitionCalculation(PartitionMetaData metaData) {
        this.partition = metaData.getPartition();
        this.lag = metaData.getLag();
        this.arrivalRate = metaData.getArrivalRate();
    }

    public PartitionCalculation(Partition partition, PartitionMetaData metaData) {
        this.partition = partition;
        this.lag = metaData.getLag();
        this.arrivalRate = metaData.getArrivalRate();
    }

    public PartitionCalculation(Partition partition, PartitionMetaData metaData,
                                double minLagCapacity, double maxLagCapacity,
                                double minConsumptionRate, double maxConsumptionRate) {
        this.partition = partition;
        this.lag = metaData.getLag();
        this.arrivalRate = metaData.getArrivalRate();
        this.minLagCapacity = minLagCapacity;
        this.maxLagCapacity = maxLagCapacity;
        this.minConsumptionRate = minConsumptionRate;
        this.maxConsumptionRate = maxConsumptionRate;
    }

    public Partition getPartition() {
        return partition;
    }

    public double getIndexedLagCapacityUpScale() {
        return Math.max(lag, minLagCapacity);
    }

    public double getIndexedLagCapacityDownScale() {
        return Math.max(lag, maxLagCapacity);
    }

    public double getIndexedConsumptionRateUpScale() {
        return Math.max(arrivalRate, minConsumptionRate);
    }

    public double getIndexedConsumptionRateDownScale() {
        return Math.max(arrivalRate, maxConsumptionRate);
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

    public double getMaxConsumptionRate() {
        return maxConsumptionRate;
    }

    public void setMaxConsumptionRate(double maxConsumptionRate) {
        this.maxConsumptionRate = maxConsumptionRate;
    }

    public double getMinConsumptionRate() {
        return minConsumptionRate;
    }

    public void setMinConsumptionRate(double minConsumptionRate) {
        this.minConsumptionRate = minConsumptionRate;
    }

    @Override
    public int compareTo(PartitionCalculation o) {
        return Double.compare(this.getArrivalRate(), o.getArrivalRate());
    }
}
