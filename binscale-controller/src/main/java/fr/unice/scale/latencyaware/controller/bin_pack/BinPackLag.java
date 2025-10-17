package fr.unice.scale.latencyaware.controller.bin_pack;

import fr.unice.scale.latencyaware.common.config.KafkaConsumerConfig;
import fr.unice.scale.latencyaware.controller.ArrivalProducer;
import fr.unice.scale.latencyaware.controller.constant.Action;
import fr.unice.scale.latencyaware.controller.entity.Consumer;
import fr.unice.scale.latencyaware.controller.entity.Partition;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static fr.unice.scale.latencyaware.controller.constant.Variables.*;

public class BinPackLag {
    private static final Logger log = LogManager.getLogger(BinPackLag.class);

    public static Instant LastUpScaleDecision = Instant.now();

    public static List<Consumer> assignment = new ArrayList<>();
    public static List<Consumer> currentAssignment = assignment;

    private static KafkaConsumer<byte[], byte[]> metadataConsumer;


    static {
        currentAssignment.add(new Consumer("0", (long) (MU * WSLA * FUP),
                MU * FUP));
        for (Partition p : ArrivalProducer.topicpartitions) {
            currentAssignment.get(0).assignPartition(p);
        }
    }


    public static void scaleAsPerBinPack() {

        log.info("Currently we have this number of consumers group {} {}", GROUP_ID, BinPackState.size);

        for (int i = 0; i < NUMBER_PARTITIONS; i++) {
            ArrivalProducer.topicpartitions.get(i).setLag(ArrivalProducer.topicpartitions.get(i).getLag()
                    + (long) ((ArrivalProducer.totalArrivalrate * REB_TIME) / (NUMBER_PARTITIONS * 1.0)));
        }

        if (BinPackState.action.equals(Action.UP) || BinPackState.action.equals(Action.REASS)) {
            int neededsize = binPackAndScale();
            log.info("We currently need the following consumers for group1 (as per the bin pack) {}", neededsize);
            int replicasForscale = neededsize - BinPackState.size;
            if (replicasForscale > 0) {
                //TODO IF and Else IF can be in the same logic
                log.info("We have to upscale  group1 by {}", replicasForscale);
                BinPackState.size = neededsize;
                LastUpScaleDecision = Instant.now();
                currentAssignment = assignment;
                try (final KubernetesClient k8s = new KubernetesClientBuilder().build()) {
                    k8s.apps().deployments().inNamespace("default").withName("latency").scale(neededsize);
                    log.info("I have Upscaled group {} you should have {}", GROUP_ID, neededsize);
                }
            } else if (replicasForscale == 0) {
                if (metadataConsumer == null) {
                    KafkaConsumerConfig config = KafkaConsumerConfig.fromEnv();
                    Properties props = KafkaConsumerConfig.createProperties(config);
                    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                            "org.apache.kafka.common.serialization.StringDeserializer");
                    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                            "org.apache.kafka.common.serialization.StringDeserializer");
                    metadataConsumer = new KafkaConsumer<>(props);
                    metadataConsumer.enforceRebalance();
                }
                currentAssignment = assignment;
                metadataConsumer.enforceRebalance();
            }
        } else if (BinPackState.action.equals(Action.DOWN)) {
            int neededsized = binPackAndScaled();
            int replicasForscaled = BinPackState.size - neededsized;
            if (replicasForscaled < BinPackState.size) {
                log.info("We have to downscale  group by {} {}", GROUP_ID, replicasForscaled);
                BinPackState.size = neededsized;
                LastUpScaleDecision = Instant.now();
                try (final KubernetesClient k8s = new KubernetesClientBuilder().build()) {
                    k8s.apps().deployments().inNamespace("default").withName("latency").scale(neededsized);
                    log.info("I have downscaled group {} you should have {}", GROUP_ID, neededsized);
                }
                currentAssignment = assignment;
            }
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
                log.info("Since partition {} has lag {} higher than consumer capacity times WSLA {}" +
                        " we are truncating its lag", partition.getId(), partition.getLag(), MU * WSLA * FUP);
                partition.setLag((long) (MU * WSLA * FUP));
            }
        }
        //if a certain partition has an arrival rate  higher than R  set its arrival rate  to R
        //that should not happen in a well partionned topic
        for (Partition partition : parts) {
            if (partition.getArrivalRate() > MU * FUP) {
                log.info("Since partition {} has arrival rate {} higher than consumer service rate {}" +
                                " we are truncating its arrival rate", partition.getId(),
                        String.format("%.2f", partition.getArrivalRate()),
                        String.format("%.2f", MU * FUP));
                partition.setArrivalRate(MU * FUP);
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
        log.info(" The BP up scaler recommended for group {} {}", GROUP_ID, consumers.size());
        return consumers.size();
    }

    private static int binPackAndScaled() {
        log.info(" shall we down scale group {} ", GROUP_ID);
        List<Consumer> consumers = new ArrayList<>();
        int consumerCount = 1;
        List<Partition> parts = new ArrayList<>(ArrivalProducer.topicpartitions);
        double fractiondynamicAverageMaxConsumptionRate = MU * FDOWN;
        for (Partition partition : parts) {
            if (partition.getLag() > fractiondynamicAverageMaxConsumptionRate * WSLA) {
                log.info("Since partition {} has lag {} higher than consumer capacity times WSLA {}" +
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
        log.info(" The BP down scaler recommended for group {} {}", GROUP_ID, consumers.size());
        return consumers.size();
    }
}
