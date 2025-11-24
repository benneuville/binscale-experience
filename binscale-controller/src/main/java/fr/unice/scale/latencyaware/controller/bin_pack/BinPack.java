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
    private static final Logger logger = LogManager.getLogger(BinPack.class);

    public static ScaleDecision scaleDecisionEventConsumerWithLag(ConsumerGroup group, CGMetaData cgdata) {
        logger.info("Currently we have this number of consumers group {} {}", group.getKafkaGroupName(), group.getSize());
        double maxLagCapacity = cgdata.getMaxLagCapacity();
        double maxConsumptionRate = cgdata.getMaxAverageConsumptionRate();

        double minLagCapacity = cgdata.getMinLagCapacity();
        double minConsumptionRate = cgdata.getMinAverageConsumptionRate();

        Map<Partition, PartitionCalculation> parts = computeConsumer(cgdata, maxLagCapacity, maxConsumptionRate, minLagCapacity, minConsumptionRate);

        List<ConsumerCalculation> upScaled = binPackAndScale(new ArrayList<>(parts.values()),
                maxLagCapacity,
                maxConsumptionRate,
                PartitionCalculation::getIndexedLagCapacityUpScale,
                PartitionCalculation::getIndexedConsumptionRateUpScale);
        // UP
        if (upScaled.size() > group.getSize()) {
            return new ScaleDecision(upScaled, Action.UP);
        }

        List<ConsumerCalculation> downScaled = binPackAndScale(new ArrayList<>(parts.values()),
                maxLagCapacity,
                maxConsumptionRate,
                PartitionCalculation::getIndexedLagCapacityDownScale,
                PartitionCalculation::getIndexedConsumptionRateDownScale);

        // DOWN
        if (downScaled.size() < group.getSize()) {
            return new ScaleDecision(downScaled, Action.DOWN);
        }

        // REASS
        if (assignmentViolatesTheSLA(parts, group, maxLagCapacity, maxConsumptionRate)) {
            return new ScaleDecision(ConsumerConverter.convert(group.getAssignment(), parts, maxLagCapacity, maxConsumptionRate), Action.REASS);
        }

        // NOTHING
        return new ScaleDecision(ConsumerConverter.convert(group.getAssignment(), parts, maxLagCapacity, maxConsumptionRate), Action.NONE);
    }

    private static Map<Partition, PartitionCalculation> computeConsumer(CGMetaData cgdatas,
                                                                        double maxLagCapacity,
                                                                        double maxConsumptionRate,
                                                                        double minLagCapacity,
                                                                        double minConsumptionRate) {

        Map<Partition, PartitionCalculation> parts = cgdatas.getPartitionsMetaData().values().stream()
                .collect(Collectors.toMap(PartitionMetaData::getPartition, PartitionCalculation::new));
        // min/max arrival rates and lags to partitions
        parts.forEach(
                (p, pc) -> {
                    pc.setMaxConsumptionRate(maxConsumptionRate);
                    pc.setMaxLagCapacity(maxLagCapacity);
                    pc.setMinConsumptionRate(minConsumptionRate);
                    pc.setMinLagCapacity(minLagCapacity);
                }
        );
        return parts;
    }

    private static List<ConsumerCalculation> binPackAndScale(List<PartitionCalculation> parts,
                                                             double maxLagCapacity,
                                                             double maxConsumptionRate,
                                                             Function<PartitionCalculation, Double> getLagCapacity,
                                                             Function<PartitionCalculation, Double> getConsumptionRate) {
        parts.sort(Collections.reverseOrder());

        int consumerCount = 1;
        List<ConsumerCalculation> consumers = new ArrayList<>();
        while (true) {
            int j;
            consumers.clear();
            for (int t = 0; t < consumerCount; t++) {
                consumers.add(new ConsumerCalculation(String.valueOf(t), maxLagCapacity, maxConsumptionRate));
            }

            for (j = 0; j < parts.size(); j++) {
                int i;
                consumers.sort(ConsumerCalculation::compareTo);
                PartitionCalculation currentPartCalc = parts.get(j);
                for (i = 0; i < consumerCount; i++) {
                    if (consumers.get(i).getRemainingLagCapacity() >= getLagCapacity.apply(currentPartCalc)
                            && consumers.get(i).getRemainingArrivalCapacity() >= getConsumptionRate.apply(currentPartCalc)) {
                        consumers.get(i).assignPartition(currentPartCalc.getPartition(),
                                getLagCapacity.apply(currentPartCalc),
                                getConsumptionRate.apply(currentPartCalc));
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

    private static boolean assignmentViolatesTheSLA(Map<Partition, PartitionCalculation> parts,
                                                    ConsumerGroup group,
                                                    double maxLatencyCapacity,
                                                    double maxConsumptionCapacity) {
        for (Consumer c : group.getAssignment()) {
            double sumArrivalRate = 0;
            double sumLag = 0;
            for (Partition p : c.getAssignedPartitions()) {
                PartitionCalculation pc = parts.get(p);
                sumLag += pc.getIndexedLagCapacityUpScale();
                sumArrivalRate += pc.getIndexedConsumptionRateUpScale();
            }
            if (sumLag > maxLatencyCapacity || sumArrivalRate > maxConsumptionCapacity)
                return true;
        }
        return false;
    }
}
