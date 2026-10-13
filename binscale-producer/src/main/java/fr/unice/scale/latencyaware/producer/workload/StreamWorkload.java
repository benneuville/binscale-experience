package fr.unice.scale.latencyaware.producer.workload;

import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import fr.unice.scale.latencyaware.producer.config.BinscaleKafkaProducerConfig;
import fr.unice.scale.latencyaware.producer.entity.Workload;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static fr.unice.scale.latencyaware.common.constant.CommonVariables.HEADER_EVENT_ID;
import static fr.unice.scale.latencyaware.producer.constant.Variables.PARTITION_WEIGHTS;
import static fr.unice.scale.latencyaware.producer.constant.Variables.PRODUCER_ID;

public class StreamWorkload extends AbstractWorkload {
    private static final Logger log = LogManager.getLogger(StreamWorkload.class);

    @Override
    public void startWorkload(BinscaleKafkaProducerConfig config,
                              KafkaProducer<String, EventCustomer> producer) throws IOException, InterruptedException {
        Workload workload = new Workload();
        Random random = new Random();
        List<Integer> partitionWeights = PARTITION_WEIGHTS;
        int totalWeight = partitionWeights.stream().mapToInt(Integer::intValue).sum();
        Map<Integer, Long> partitionMessageCounts = new HashMap<>();

        for (int i = 0; i < partitionWeights.size(); i++) {
            partitionMessageCounts.put(i, 0L);
        }

        long periodNanos = TimeUnit.MILLISECONDS.toNanos(config.getDelay());
        for (int i = 0; i < workload.getTimestamps().size(); i++) {
            ArrivalRate = (float) Math.ceil(workload.getRate(i));
            long totalMessages = Math.round(ArrivalRate);
            long batchStart = System.nanoTime();
            long sentMessages = 0;

            log.info("streaming a batch of {} events over {} ms", totalMessages, config.getDelay());

            for (int partitionIndex = 0; partitionIndex < partitionWeights.size(); partitionIndex++) {
                int weight = partitionWeights.get(partitionIndex);
                long messagesPerPartition = totalMessages * weight / totalWeight;

                for (long j = 0; j < messagesPerPartition; j++) {
                    awaitEventTime(batchStart, eventOffset(periodNanos, sentMessages, totalMessages));
                    sendEvent(config, producer, random, partitionIndex);
                    partitionMessageCounts.put(partitionIndex, partitionMessageCounts.get(partitionIndex) + 1);
                    sentMessages++;
                }
            }

            long remainingMessages = totalMessages % totalWeight;
            for (long j = 0; j < remainingMessages; j++) {
                int partition = (int) (j % partitionWeights.size());
                awaitEventTime(batchStart, eventOffset(periodNanos, sentMessages, totalMessages));
                sendEvent(config, producer, random, partition);
                partitionMessageCounts.put(partition, partitionMessageCounts.get(partition) + 1);
                sentMessages++;
            }

            awaitEventTime(batchStart, periodNanos);
            log.info("streamed {} events over {} ms", sentMessages, config.getDelay());
        }

        log.info("Total number of messages sent to each partition:");
        for (Map.Entry<Integer, Long> entry : partitionMessageCounts.entrySet()) {
            log.info("Partition {}: {} messages", entry.getKey(), entry.getValue());
        }
    }

    private static long eventOffset(long periodNanos, long eventIndex, long totalMessages) {
        if (totalMessages <= 0) {
            return periodNanos;
        }
        return (long) (periodNanos * ((double) eventIndex / totalMessages));
    }

    private static void awaitEventTime(long batchStart, long offsetNanos) throws InterruptedException {
        long remainingNanos = batchStart + offsetNanos - System.nanoTime();
        if (remainingNanos > 0) {
            long milliseconds = TimeUnit.NANOSECONDS.toMillis(remainingNanos);
            int nanoseconds = (int) (remainingNanos - TimeUnit.MILLISECONDS.toNanos(milliseconds));
            Thread.sleep(milliseconds, nanoseconds);
        }
    }

    private static void sendEvent(BinscaleKafkaProducerConfig config,
                                  KafkaProducer<String, EventCustomer> producer,
                                  Random random,
                                  int partition) {
        String id = PRODUCER_ID + "-" + UUID.randomUUID();
        EventCustomer customer = new EventCustomer(random.nextInt(), id);
        ProducerRecord<String, EventCustomer> event = new ProducerRecord<>(
                config.getTopic(), partition, null, id, customer);
        event.headers().add(HEADER_EVENT_ID, id.getBytes());
        producer.send(event);
    }
}
