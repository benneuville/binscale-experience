package fr.unice.scale.latencyaware.controller.bin_pack;

import fr.unice.scale.latencyaware.controller.constant.Action;
import fr.unice.scale.latencyaware.controller.entity.Consumer;
import fr.unice.scale.latencyaware.controller.entity.ConsumerGroup;
import fr.unice.scale.latencyaware.controller.entity.Partition;
import fr.unice.scale.latencyaware.controller.entity.calculation.ConsumerCalculation;
import fr.unice.scale.latencyaware.controller.entity.calculation.PartitionCalculation;
import fr.unice.scale.latencyaware.controller.entity.decision.ScaleDecision;
import fr.unice.scale.latencyaware.controller.entity.meta_data.CGMetaData;
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
        double maxLagCapacity = cgdata.getMaxLagCapacity();
        double maxArrivalRate = cgdata.getMaxAverageArrivalRate();

        double minLagCapacity = cgdata.getMinLagCapacity();
        double minProcessingRate = cgdata.getMinAverageArrivalRate();

        Map<Partition, PartitionCalculation> parts = computeConsumer(cgdata, maxLagCapacity, maxArrivalRate, minLagCapacity, minProcessingRate);

        List<ConsumerCalculation> upScaled = binPackAndScale(new ArrayList<>(parts.values()),
                maxLagCapacity,
                maxArrivalRate,
                PartitionCalculation::getIndexedLagCapacityUpScale,
                PartitionCalculation::getIndexedArrivalRateUpScale);
        // UP
        if (upScaled.size() > group.getAssignment().size()) {
            log.info("Binpack (UP) from {} to {}", group.getAssignment().size(), upScaled.size());
            log.info("New assignment {}", upScaled.toString());
            return new ScaleDecision(upScaled, Action.UP);
        }

        List<ConsumerCalculation> downScaled = binPackAndScale(new ArrayList<>(parts.values()),
                minLagCapacity,
                minProcessingRate,
                PartitionCalculation::getIndexedLagCapacityDownScale,
                PartitionCalculation::getIndexedArrivalRateDownScale);

        // DOWN
        if (downScaled.size() < group.getAssignment().size()) {
            log.info("Binpack (DOWN) from {} to {}", group.getAssignment().size(), downScaled.size());
            log.info("New assignment {}", downScaled.toString());
            return new ScaleDecision(downScaled, Action.DOWN);
        }

        // REASS
        if (assignmentViolatesTheSLA(parts, group, maxLagCapacity, maxArrivalRate)) {
            log.info("Binpack (REASS) {}", group.getKafkaGroupName());
            return new ScaleDecision(ConsumerConverter.convert(group.getAssignment(), parts, maxLagCapacity, maxArrivalRate), Action.REASS);
        }

        log.info("Binpack (NONE) {}", group.getKafkaGroupName());
        // NOTHING
        return new ScaleDecision(ConsumerConverter.convert(group.getAssignment(), parts, maxLagCapacity, maxArrivalRate), Action.NONE);
    }

    public static Map<Partition, PartitionCalculation> computeConsumer(CGMetaData cgdatas,
                                                                       double maxLagCapacity,
                                                                       double maxArrivalRate,
                                                                       double minLagCapacity,
                                                                       double minArrivalRate) {

        Map<Partition, PartitionCalculation> parts = cgdatas.getPartitionsMetaData().values().stream().map(
                pmd -> new PartitionCalculation(
                        pmd, cgdatas.getAvgParentArrivalRate()
                )
        ).collect(Collectors.toMap(
                PartitionCalculation::getPartition,
                pc -> pc
        ));

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

    public static List<ConsumerCalculation> binPackAndScale(List<PartitionCalculation> parts,
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
