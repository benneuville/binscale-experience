package fr.unice.scale.latencyaware.controller.bin_pack;

import fr.unice.scale.latencyaware.controller.ArrivalProducer;
import fr.unice.scale.latencyaware.controller.constant.Action;
import fr.unice.scale.latencyaware.controller.entity.Consumer;
import fr.unice.scale.latencyaware.controller.entity.Partition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static fr.unice.scale.latencyaware.controller.constant.Variables.*;


public class BinPackState {
    private static final Logger log = LogManager.getLogger(BinPackState.class);
    public static int size = INIT_SIZE;
    public static Action action = Action.NONE;
    static List<Consumer> assignment = new ArrayList<Consumer>();
    static List<Consumer> currentAssignment = assignment;
    static List<Consumer> tempAssignment = assignment;

    public static void scaleAsPerBinPack() {
        action = Action.NONE;
        log.info("Currently we have this number of consumers group {} {}", GROUP_ID, size);
        int neededsize = binPackAndScale();
        log.info("We currently need the following consumers for group1 (as per the bin pack) {}", neededsize);
        int replicasForscale = neededsize - size;
        if (replicasForscale > 0) {
            action = Action.UP;
            //TODO IF and Else IF can be in the same logic
            log.info("We have to upscale  group1 by {}", replicasForscale);
            //currentAssignment = assignment;
            return;

        } else {
            int neededsized = binPackAndScaled();
            int replicasForscaled = size - neededsized;
            if (replicasForscaled > 0) {
                action = Action.DOWN;
                log.info("We have to downscale  group by {} {}", GROUP_ID, replicasForscaled);
                //currentAssignment = assignment;
                return;
            }
        }


        if (assignmentViolatesTheSLA2()) {
            action = Action.REASS;
        }
        log.info("===================================");
    }


    private static int binPackAndScale() {
        log.info(" shall we upscale group {}", GROUP_ID);
        List<Consumer> consumers = new ArrayList<>();
        int consumerCount = 1;
        List<Partition> parts = new ArrayList<>(ArrivalProducer.topicpartitions);


        for (Partition partition : parts) {
            if (partition.getLag() > MU * WSLA * FUP) {
                log.info("Since partition {} has lag {} higher than consumer capacity times wsla {}" +
                        " we are truncating its lag", partition.getId(), partition.getLag(), MU * WSLA * FUP);
                partition.setLag((long) (MU * WSLA * FUP/*dynamicAverageMaxConsumptionRate*wsla*/));
            }
        }
        //if a certain partition has an arrival rate  higher than R  set its arrival rate  to R
        //that should not happen in a well partionned topic
        for (Partition partition : parts) {
            if (partition.getArrivalRate() > MU * FUP/*dynamicAverageMaxConsumptionRate*wsla*/) {
                log.info("Since partition {} has arrival rate {} higher than consumer service rate {}" +
                                " we are truncating its arrival rate", partition.getId(),
                        String.format("%.2f", partition.getArrivalRate()),
                        String.format("%.2f", MU * FUP /*dynamicAverageMaxConsumptionRate*wsla*/));
                partition.setArrivalRate(MU * FUP /*dynamicAverageMaxConsumptionRate*wsla*/);
            }
        }
        //start the bin pack FFD with sort
        parts.sort(Collections.reverseOrder());

        while (true) {
            int j;
            consumers.clear();
            for (int t = 0; t < consumerCount; t++) {
                consumers.add(new Consumer((String.valueOf(t)), (long) (MU * WSLA * FUP),
                        MU * FUP));
            }

            for (j = 0; j < parts.size(); j++) {
                int i;
                consumers.sort(Collections.reverseOrder());
                for (i = 0; i < consumerCount; i++) {
                    if (consumers.get(i).getRemainingLagCapacity() >= parts.get(j).getLag() &&
                            consumers.get(i).getRemainingArrivalCapacity() >= parts.get(j).getArrivalRate()) {
                        consumers.get(i).assignPartition(parts.get(j));
                        break;
                    }
                }
                if (i == consumerCount) {
                    consumerCount++;
                    break;
                }
            }
            if (j == parts.size())
                break;
        }
        assignment = consumers;
        tempAssignment = consumers;
        log.info(" The BP up scaler recommended for group {} {}", GROUP_ID, consumers.size());
        return consumers.size();
    }

    static int binPackAndScaled() {
        log.info(" shall we down scale group {} ", GROUP_ID);
        List<Consumer> consumers = new ArrayList<>();
        int consumerCount = 1;
        List<Partition> parts = new ArrayList<>(ArrivalProducer.topicpartitions);
        double fractiondynamicAverageMaxConsumptionRate = MU * FDOWN;
        for (Partition partition : parts) {
            if (partition.getLag() > fractiondynamicAverageMaxConsumptionRate * WSLA) {
                log.info("Since partition {} has lag {} higher than consumer capacity times wsla {}" +
                                " we are truncating its lag", partition.getId(), partition.getLag(),
                        fractiondynamicAverageMaxConsumptionRate * WSLA);
                partition.setLag((long) (fractiondynamicAverageMaxConsumptionRate * WSLA));
            }
        }

        //if a certain partition has an arrival rate  higher than R  set its arrival rate  to R
        //that should not happen in a well partionned topic
        for (Partition partition : parts) {
            if (partition.getArrivalRate() > fractiondynamicAverageMaxConsumptionRate) {
                log.info("Since partition {} has arrival rate {} higher than consumer service rate {}" +
                                " we are truncating its arrival rate", partition.getId(),
                        String.format("%.2f", partition.getArrivalRate()),
                        String.format("%.2f", fractiondynamicAverageMaxConsumptionRate));
                partition.setArrivalRate(fractiondynamicAverageMaxConsumptionRate);
            }
        }
        //start the bin pack FFD with sort
        parts.sort(Collections.reverseOrder());
        while (true) {
            int j;
            consumers.clear();
            for (int t = 0; t < consumerCount; t++) {
                consumers.add(new Consumer((String.valueOf(consumerCount)),
                        (long) (fractiondynamicAverageMaxConsumptionRate * WSLA),
                        fractiondynamicAverageMaxConsumptionRate));
            }

            for (j = 0; j < parts.size(); j++) {
                int i;
                consumers.sort(Collections.reverseOrder());
                for (i = 0; i < consumerCount; i++) {

                    if (consumers.get(i).getRemainingLagCapacity() >= parts.get(j).getLag() &&
                            consumers.get(i).getRemainingArrivalCapacity() >= parts.get(j).getArrivalRate()) {
                        consumers.get(i).assignPartition(parts.get(j));
                        break;
                    }
                }
                if (i == consumerCount) {
                    consumerCount++;
                    break;
                }
            }
            if (j == parts.size())
                break;
        }

        assignment = consumers;
        log.info(" The BP down scaler recommended  for group {} {}", GROUP_ID, consumers.size());
        return consumers.size();
    }

    private static boolean assignmentViolatesTheSLA2() {
        List<Partition> partsReset = new ArrayList<>(ArrivalProducer.topicpartitions);
        for (Consumer cons : currentAssignment) {
            double sumPartitionsArrival = 0;
            double sumPartitionsLag = 0;
            for (Partition p : cons.getAssignedPartitions()) {
                sumPartitionsArrival += partsReset.get(p.getId()).getArrivalRate();
                sumPartitionsLag += partsReset.get(p.getId()).getLag();
            }

            if (sumPartitionsLag > (WSLA * MU * FUP)
                    || sumPartitionsArrival > MU * FUP) {
                return true;
            }
        }
        return false;
    }

}
