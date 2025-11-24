package fr.unice.scale.latencyaware.controller.entity;
import java.util.ArrayList;
import java.util.List;

public class Consumer {
    private final Double lagCapacity;
    private final Double arrivalCapacity;
    private final String id;
    private List<Partition> assignedPartitions = new ArrayList<>();

    public Consumer(String id, Double lagCapacity,
                    double arrivalCapacity) {
        this.lagCapacity = lagCapacity;
        this.arrivalCapacity = arrivalCapacity;
        this.id = id;
    }

    public void assignPartition(Partition partition) {
        assignedPartitions.add(partition);
    }


    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "\nConsumer{" + "id=" + id +
                ",  lagCapacity= " + lagCapacity +
                ", arrivalCapacity= " + String.format("%.2f", arrivalCapacity) +
                ", assignedPartitions= \n" + assignedPartitions +
                "}";
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = lagCapacity != null ? lagCapacity.hashCode() : 0;
        result = 31 * result + (assignedPartitions != null ? assignedPartitions.hashCode() : 0);
        temp = Double.doubleToLongBits(arrivalCapacity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }


    public List<Partition> getAssignedPartitions() {
        return assignedPartitions;
    }

}
