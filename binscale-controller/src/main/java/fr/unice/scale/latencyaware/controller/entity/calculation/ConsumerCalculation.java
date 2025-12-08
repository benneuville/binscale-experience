package fr.unice.scale.latencyaware.controller.entity.calculation;

import fr.unice.scale.latencyaware.controller.entity.Partition;

import java.util.ArrayList;
import java.util.List;

public class ConsumerCalculation implements Comparable<ConsumerCalculation> {
    private final String id;
    private Double remainingProcessingCapacity;
    private List<Partition> assignedPartitions = new ArrayList<>();
    private Double remainingLagCapacity;

    public ConsumerCalculation(String id, Double maxLagCapacity,
                               double maxProcessingCapacity) {
        this.id = id;
        this.remainingLagCapacity = maxLagCapacity;
        this.remainingProcessingCapacity = maxProcessingCapacity;
    }

    public void assignPartition(Partition partition, double lagCapacity, double processingRate) {
        assignedPartitions.add(partition);
        remainingLagCapacity -= lagCapacity;
        remainingProcessingCapacity -= processingRate;
    }


    public String getId() {
        return id;
    }

    public Double getRemainingLagCapacity() {
        return remainingLagCapacity;
    }

    public double getRemainingProcessingCapacity() {
        return remainingProcessingCapacity;
    }

    @Override
    public String toString() {
        return "\nConsumer{" + "id=" + id +
                ", remainingArrivalCapacity= " + String.format("%.2f", remainingProcessingCapacity) +
                ", remainingLagCapacity= " + remainingLagCapacity +
                ", assignedPartitions= \n" + assignedPartitions +
                "}";
    }

    @Override
    public int hashCode() {
        int result = 0;
        long temp;
        temp = Double.doubleToLongBits(remainingProcessingCapacity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (assignedPartitions != null ? assignedPartitions.hashCode() : 0);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (remainingLagCapacity != null ? remainingLagCapacity.hashCode() : 0);
        return result;
    }


    public List<Partition> getAssignedPartitions() {
        return assignedPartitions;
    }

    @Override
    public int compareTo(ConsumerCalculation o) {
        return Double.compare(this.remainingProcessingCapacity, o.getRemainingLagCapacity());
    }
}
