package fr.unice.scale.latencyaware.controller.entity;
import java.util.ArrayList;
import java.util.List;

public class Consumer {
    private final String id;
    private List<Partition> assignedPartitions = new ArrayList<>();

    public Consumer(String id) {
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
                ", assignedPartitions= \n" + assignedPartitions +
                "}";
    }


    public List<Partition> getAssignedPartitions() {
        return assignedPartitions;
    }

}
