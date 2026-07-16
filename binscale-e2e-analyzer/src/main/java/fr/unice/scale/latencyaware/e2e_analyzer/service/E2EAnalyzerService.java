package fr.unice.scale.latencyaware.e2e_analyzer.service;

import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import fr.unice.scale.latencyaware.e2e_analyzer.config.BinscaleE2EIngestionConfig;
import fr.unice.scale.latencyaware.e2e_analyzer.config.E2EAnalyzerConfigBuilder;
import fr.unice.scale.latencyaware.e2e_analyzer.entity.E2EAnalyzerConfig;
import fr.unice.scale.latencyaware.e2e_analyzer.entity.Topic;
import fr.unice.scale.latencyaware.e2e_analyzer.entity.model.E2EEvent;
import fr.unice.scale.latencyaware.e2e_analyzer.event_merger.EventMerger;
import fr.unice.scale.latencyaware.e2e_analyzer.ingestion.E2EAnalyzerEventIngestion;
import fr.unice.scale.latencyaware.e2e_analyzer.repository.KafkaOffsetRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class E2EAnalyzerService {
    private static final int BATCH_SIZE = 1000;
    private final EventMerger eventMerger;
    private final KafkaOffsetRepository offsetRepository;
    private final Logger log = LoggerFactory.getLogger(E2EAnalyzerService.class);
    private final List<E2EAnalyzerEventIngestion> evIngest;
    private final List<ConsumerRecord<String, EventCustomer>> messageBuffer = new ArrayList<>(1000);
    private final ScheduledExecutorService batchSender = Executors.newSingleThreadScheduledExecutor();

    public E2EAnalyzerService(EventMerger eventMerger, KafkaOffsetRepository offsetRepository) {
        this.eventMerger = eventMerger;
        this.offsetRepository = offsetRepository;
        this.evIngest = new ArrayList<>();

        batchSender.scheduleAtFixedRate(this::sendBatch, 5, 5, TimeUnit.SECONDS);
    }

    public void run() {
        E2EAnalyzerConfig config = E2EAnalyzerConfigBuilder.fromEnv();

        for (Topic topic : config.getTopics()) {
            evIngest.add(new E2EAnalyzerEventIngestion(BinscaleE2EIngestionConfig.fromEnv(topic.getName()), offsetRepository));
        }

        while (true) {
            boolean isAssigned = false;
            for (E2EAnalyzerEventIngestion ei : evIngest) {
                ei.poll(Duration.ofMillis(100));

                if (!ei.assignment().isEmpty()) {
                    isAssigned = true;
                    break;
                }
            }

            if (isAssigned) {
                log.info("✅ Partition assignée. Démarrage du traitement...");
                break;
            }
            log.info("⏳ En attente de l'assignation des partitions...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }

        try {
            while (true) {
                List<ConsumerRecords<String, EventCustomer>> recordsList = evIngest.stream()
                        .map(ei -> ei.poll(Duration.ofMillis(100)))
                        .collect(Collectors.toList());

                for (ConsumerRecords<String, EventCustomer> records : recordsList) {
                    for (ConsumerRecord<String, EventCustomer> record : records) {
                        synchronized (messageBuffer) {
                            messageBuffer.add(record);
                            if (messageBuffer.size() >= BATCH_SIZE) {
                                sendBatch();
                            }
                        }
                    }
                }

                if (isAllEventProcessed()) {
                    sendBatch();
                    break;
                }
            }
        } finally {
            batchSender.shutdown();
            try {
                if (!batchSender.awaitTermination(5, TimeUnit.MINUTES)) {
                    batchSender.shutdownNow();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private synchronized void sendBatch() {
        if (messageBuffer.isEmpty()) {
            return;
        }
        List<ConsumerRecord<String, EventCustomer>> batchToSend = new ArrayList<>(messageBuffer);
        messageBuffer.clear();

        log.debug("Envoi d'un batch de {} messages", batchToSend.size());
        eventMerger.eventMerger(batchToSend);
    }

    private boolean isAllEventProcessed() {
        for (E2EAnalyzerEventIngestion consumer : evIngest) {
            Set<TopicPartition> partitions = consumer.assignment();
            if (partitions.isEmpty()) continue;

            Map<TopicPartition, Long> endOffsets = consumer.getConsumer().endOffsets(partitions);
            for (TopicPartition tp : partitions) {
                Long endOffset = endOffsets.get(tp);
                if (endOffset == null) continue;
                long currentOffset = consumer.getConsumer().position(tp);
                if (endOffset - currentOffset > 0) return false;
            }
        }
        return true;
    }

    public Map<String, List<E2EEvent>> getAllEventTrackers() {
        return eventMerger.getAllEventTrackers();
    }

    public void cleanTables() {
        eventMerger.cleanTables();
        offsetRepository.deleteAll();
    }
}