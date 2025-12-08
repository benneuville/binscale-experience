package fr.unice.scale.latencyaware.consumer.entity.assignment;

import org.apache.kafka.common.TopicPartition;

import java.util.List;
import java.util.Optional;

public final class MemberData {
    public final List<TopicPartition> partitions;
    public final double maxConsumptionRate;
    public final Optional<Integer> generation;

    public MemberData(List<TopicPartition> partitions,
                      double maxConsumptionRate,
                      Optional<Integer> generation) {
        this.partitions = partitions;
        this.maxConsumptionRate = maxConsumptionRate;
        this.generation = generation;
    }
}