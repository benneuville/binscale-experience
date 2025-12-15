package fr.unice.scale.latencyaware.controller.bin_pack;

import fr.unice.scale.latencyaware.controller.constant.Action;
import fr.unice.scale.latencyaware.controller.entity.Consumer;
import fr.unice.scale.latencyaware.controller.entity.ConsumerGroup;
import fr.unice.scale.latencyaware.controller.entity.Partition;
import fr.unice.scale.latencyaware.controller.entity.calculation.ConsumerCalculation;
import fr.unice.scale.latencyaware.controller.entity.calculation.PartitionCalculation;
import fr.unice.scale.latencyaware.controller.entity.decision.ScaleDecision;
import fr.unice.scale.latencyaware.controller.entity.meta_data.CGMetaData;
import fr.unice.scale.latencyaware.controller.entity.meta_data.PartitionMetaData;
import fr.unice.scale.latencyaware.controller.utils.ConsumerConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BinPack {
    private static final Logger log = LogManager.getLogger(BinPack.class);

    public static ScaleDecision scaleDecisionEventConsumerWithLag(ConsumerGroup group, CGMetaData cgdata) {
        log.info("Currently we have this number of consumers group {} {}", group.getKafkaGroupName(), group.getAssignment().size());
        log.info("Average processing rate for group {} : {}", group.getKafkaGroupName(), cgdata.getAvgEventProcessingRate());
        double maxLagCapacity = cgdata.getMaxLagCapacity();
        log.info("Max Lag Capacity for group {} is {}", group.getKafkaGroupName(), maxLagCapacity);
        double maxArrivalRate = cgdata.getMaxAverageArrivalRate();
        log.info("Max Processing Rate for group {} is {}", group.getKafkaGroupName(), maxArrivalRate);

        double minLagCapacity = cgdata.getMinLagCapacity();
        log.info("Min Lag Capacity for group {} is {}", group.getKafkaGroupName(), minLagCapacity);
        double minProcessingRate = cgdata.getMinAverageArrivalRate();
        log.info("Min Processing Rate for group {} is {}", group.getKafkaGroupName(), minProcessingRate);

        Map<Partition, PartitionCalculation> parts = computeConsumer(cgdata, maxLagCapacity, maxArrivalRate, minLagCapacity, minProcessingRate);

        log.info("Binpack (UP) on -> Consumer group {}", group.getKafkaGroupName());
        List<ConsumerCalculation> upScaled = binPack(new ArrayList<>(parts.values()),
                maxLagCapacity,
                maxArrivalRate,
                PartitionCalculation::getIndexedLagCapacityUpScale,
                PartitionCalculation::getIndexedArrivalRateUpScale);
        // UP
        if (upScaled.size() > group.getAssignment().size()) {
            log.info("Decided to upscale from {} to {}", group.getAssignment().size(), upScaled.size());
            log.info("New assignment after upscale {}", upScaled.toString());
            return new ScaleDecision(upScaled, Action.UP);
        }

        log.info("Binpack (DOWN) on -> Consumer group {}", group.getKafkaGroupName());
        List<ConsumerCalculation> downScaled = binPack(new ArrayList<>(parts.values()),
                minLagCapacity,
                minProcessingRate,
                PartitionCalculation::getIndexedLagCapacityDownScale,
                PartitionCalculation::getIndexedArrivalRateDownScale);

        // DOWN
        if (downScaled.size() < group.getAssignment().size()) {
            log.info("Decided to downscale from {} to {}", group.getAssignment().size(), downScaled.size());
            log.info("New assignment after downscale {}", downScaled.toString());
            return new ScaleDecision(downScaled, Action.DOWN);
        }

        log.info("Binpack (REASS) on -> Consumer group {}", group.getKafkaGroupName());
        // REASS
        if (assignmentViolatesTheSLA(parts, group, maxLagCapacity, maxArrivalRate)) {
            return new ScaleDecision(ConsumerConverter.convert(group.getAssignment(), parts, maxLagCapacity, maxArrivalRate), Action.REASS);
        }

        log.info("Binpack (NONE) on -> Consumer group {}", group.getKafkaGroupName());
        // NOTHING
        return new ScaleDecision(ConsumerConverter.convert(group.getAssignment(), parts, maxLagCapacity, maxArrivalRate), Action.NONE);
    }

    public static Map<Partition, PartitionCalculation> computeConsumer(CGMetaData cgdatas,
                                                                       double maxLagCapacity,
                                                                       double maxArrivalRate,
                                                                       double minLagCapacity,
                                                                       double minArrivalRate) {

        Map<Partition, PartitionCalculation> parts = cgdatas.getPartitionsMetaData().values().stream()
                .collect(Collectors.toMap(PartitionMetaData::getPartition, PartitionCalculation::new));
        // min/max arrival rates and lags to partitions
        parts.forEach(
                (p, pc) -> {
                    pc.setMaxArrivalRate(maxArrivalRate);
                    pc.setMaxLagCapacity(maxLagCapacity);
                    pc.setMinArrivalRate(minArrivalRate);
                    pc.setMinLagCapacity(minLagCapacity);
                }
        );
        return parts;
    }

    public static List<ConsumerCalculation> binPack(List<PartitionCalculation> parts,
                                                    double maxLagCapacity,
                                                    double maxConsumptionRate,
                                                    Function<PartitionCalculation, Double> getAvgLagCapacity,
                                                    Function<PartitionCalculation, Double> getAvgEventProcessRate) {
        parts.sort(Collections.reverseOrder());

        int consumerCount = 1;
        List<ConsumerCalculation> consumers = new ArrayList<>();
        while (true) {
            int j;
            consumers.clear();
            for (int t = 0; t < consumerCount; t++) {
                consumers.add(new ConsumerCalculation(String.valueOf(t), maxLagCapacity, maxConsumptionRate));
            }
            log.info("Creating {} consumers for binpack", consumerCount);

            for (j = 0; j < parts.size(); j++) {
                int i;
                consumers.sort(Collections.reverseOrder());
                PartitionCalculation currentPartCalc = parts.get(j);
                for (i = 0; i < consumerCount; i++) {
                    if (consumers.get(i).getRemainingLagCapacity() >= getAvgLagCapacity.apply(currentPartCalc)
                            && consumers.get(i).getRemainingProcessingCapacity() >= getAvgEventProcessRate.apply(currentPartCalc)) {
                        consumers.get(i).assignPartition(currentPartCalc.getPartition(),
                                getAvgLagCapacity.apply(currentPartCalc),
                                getAvgEventProcessRate.apply(currentPartCalc));
                        break;
                    }
                }
                if (i == consumerCount) {
                    consumerCount++;
                    break;
                }
            }
            if (j == parts.size()) break;
        }

        return consumers;
    }

    public static boolean assignmentViolatesTheSLA(Map<Partition, PartitionCalculation> parts,
                                                   ConsumerGroup group,
                                                   double maxLagCapacity,
                                                   double maxProcessingCapacity) {
        for (Consumer c : group.getAssignment()) {
            double sumProcessingCapacityRate = 0;
            double sumLag = 0;
            for (Partition p : c.getAssignedPartitions()) {
                PartitionCalculation pc = parts.get(p);
                sumLag += pc.getIndexedLagCapacityUpScale();
                sumProcessingCapacityRate += pc.getIndexedArrivalRateUpScale();
            }
            if (sumLag > maxLagCapacity || sumProcessingCapacityRate > maxProcessingCapacity)
                return true;
        }
        return false;
    }
}
