package fr.unice.scale.latencyaware.e2e_analyzer.ingestion;


import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import fr.unice.scale.latencyaware.e2e_analyzer.config.BinscaleE2EIngestionConfig;
import fr.unice.scale.latencyaware.e2e_analyzer.entity.model.KafkaOffset;
import fr.unice.scale.latencyaware.e2e_analyzer.repository.KafkaOffsetRepository;
import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;

public class E2EAnalyzerEventIngestion {
    private final KafkaConsumer<String, EventCustomer> consumer;
    private final KafkaOffsetRepository offsetRepository;
    private final String topic;

    private final Logger log = LoggerFactory.getLogger(E2EAnalyzerEventIngestion.class);

    public E2EAnalyzerEventIngestion(BinscaleE2EIngestionConfig config, KafkaOffsetRepository offsetRepository) {
        Properties props = config.toProperties();
        this.topic = config.getTopic();
        this.offsetRepository = offsetRepository;
        this.consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(config.getTopic()), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                for (TopicPartition tp : partitions) {
                    String partitionKey = topic + "-" + tp.partition();

                    if (offsetRepository.existsById(partitionKey)) {
                        KafkaOffset offset = offsetRepository.findById(partitionKey).get();
                        consumer.seek(tp, offset.getOffset());
                        log.info("offset {} for partition {}", offset.getOffset(), partitionKey);
                    } else {
                        consumer.seekToBeginning(Collections.singletonList(tp));
                        log.info("Partition {}, offset from beginning", partitionKey);
                    }
                }
            }

            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                for (TopicPartition tp : partitions) {
                    long position = consumer.position(tp);
                    String partitionKey = topic + "-" + tp.partition();
                    offsetRepository.save(new KafkaOffset(partitionKey, position + 1));
                }
                try {
                    consumer.commitSync(Duration.ofSeconds(5));
                } catch (CommitFailedException e) {
                    log.error("Échec du commit avant rééquilibrage", e);
                }
            }
        });
        consumer.poll(Duration.ofMillis(100));
    }

    public ConsumerRecords<String, EventCustomer> poll(Duration timeout) {
        return consumer.poll(timeout);
    }

    public Set<TopicPartition> assignment() {
        return consumer.assignment();
    }

    public KafkaConsumer<String, EventCustomer> getConsumer() {
        return this.consumer;
    }
}