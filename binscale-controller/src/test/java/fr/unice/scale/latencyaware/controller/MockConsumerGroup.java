package fr.unice.scale.latencyaware.controller;

import fr.unice.scale.latencyaware.controller.entity.Consumer;
import fr.unice.scale.latencyaware.controller.entity.ConsumerGroup;
import fr.unice.scale.latencyaware.controller.entity.Partition;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MockConsumerGroup extends ConsumerGroup {
    private List<Consumer> assignment;
    private List<Partition> partitions;

    public MockConsumerGroup(String topic, int nbPartitions, int nbConsumers) {
        super(topic, nbConsumers, 100, 0.5, "fakeConsumer" + topic, "fakeGroup" + topic, nbPartitions, null);

        this.partitions = IntStream.range(0, nbPartitions)
                .mapToObj(Partition::new)
                .collect(Collectors.toList());

        this.assignment = new ArrayList<>();
        for (int i = 0; i < nbConsumers; i++) {
            assignment.add(new Consumer(String.valueOf(i)));
        }

        // Toutes les partitions assignées au consumer C0
        if (!assignment.isEmpty()) {
            partitions.forEach(assignment.get(0)::assignPartition);
        }
    }

    @Override
    public List<Partition> getTopicPartitions() {
        return partitions;
    }

    @Override
    public List<Consumer> getAssignment() {
        return assignment;
    }

    @Override
    public void setAssignment(List<Consumer> assignment) {
        this.assignment = assignment;
    }

    @Override
    public double getFup() {
        return .7;
    }

    @Override
    public double getFdown() {
        return .3;
    }
}
