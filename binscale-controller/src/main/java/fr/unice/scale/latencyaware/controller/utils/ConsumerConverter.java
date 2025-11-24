package fr.unice.scale.latencyaware.controller.utils;

import fr.unice.scale.latencyaware.controller.entity.Consumer;
import fr.unice.scale.latencyaware.controller.entity.Partition;
import fr.unice.scale.latencyaware.controller.entity.calculation.ConsumerCalculation;
import fr.unice.scale.latencyaware.controller.entity.calculation.PartitionCalculation;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConsumerConverter {
    public static List<ConsumerCalculation> convert(List<Consumer> consumers) {
        return consumers.stream().map(c -> new ConsumerCalculation(c.getId(), .0, .0)).collect(Collectors.toList());
    }

    public static List<ConsumerCalculation> convert(List<Consumer> consumers, Map<Partition, PartitionCalculation> parts, double maxLag, double maxCapacity) {
        return consumers.stream().map( c -> {
                    ConsumerCalculation cc = new ConsumerCalculation(c.getId(), maxCapacity, maxLag);
                    for(Partition p : c.getAssignedPartitions()) {
                        cc.assignPartition(p, parts.get(p).getIndexedLagCapacityUpScale(), parts.get(p).getIndexedConsumptionRateUpScale());
                    }
                    return cc;
                }
        ).collect(Collectors.toList());
    }

    public static Consumer convert(ConsumerCalculation cc) {
        Consumer c = new Consumer(cc.getId(), .0, .0);
        for (Partition p : cc.getAssignedPartitions()) {
            c.assignPartition(p);
        }
        return c;
    }

    public static List<Consumer> convertConsumers(List<ConsumerCalculation> ccList) {
        return ccList.stream().map(ConsumerConverter::convert).collect(Collectors.toList());
    }
}
