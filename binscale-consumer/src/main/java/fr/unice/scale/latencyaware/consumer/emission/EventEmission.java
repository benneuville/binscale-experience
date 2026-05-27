package fr.unice.scale.latencyaware.consumer.emission;

import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import fr.unice.scale.latencyaware.consumer.config.BinscaleProducerConfig;
import fr.unice.scale.latencyaware.consumer.entity.DistributedEventCustomer;
import fr.unice.scale.latencyaware.consumer.entity.DistributionConfig;
import fr.unice.scale.latencyaware.consumer.entity.ProducerTopicDistribution;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.unice.scale.latencyaware.common.constant.CommonVariables.HEADER_GROUP_ID_KEY;
import static fr.unice.scale.latencyaware.consumer.constant.Variables.GROUP_ID;

public class EventEmission {
    private final static Logger logger = LoggerFactory.getLogger(EventEmission.class);
    private final Map<ProducerTopicDistribution, KafkaProducer<String, EventCustomer>> producers;

    public EventEmission(DistributionConfig config) {
        this.producers = new HashMap<>();
        if (config.getOutput().isEmpty()) return;
        for (ProducerTopicDistribution topic : config.getOutput()) {
            BinscaleProducerConfig producerConfig = new BinscaleProducerConfig(topic.getName());
            KafkaProducer<String, EventCustomer> producer = new KafkaProducer<>(BinscaleProducerConfig.createProperties(producerConfig));
            this.producers.put(topic, producer);
        }
        logger.info("Initialized EventEmission with {} producers", this.producers.size());
    }

    public void publish(List<DistributedEventCustomer> processed) {
        if (producers.isEmpty()) return;
        for (DistributedEventCustomer distributedEvent : processed) {
            KafkaProducer<String, EventCustomer> producer = producers.get(distributedEvent.getTargetTopic());
            logger.info("Publishing {} events to topic {}",
                    distributedEvent.getEvents().size(),
                    distributedEvent.getTargetTopic().getName());
            for (EventCustomer event : distributedEvent.getEvents()) {
                ProducerRecord<String, EventCustomer> record = new ProducerRecord<>(
                        distributedEvent.getTargetTopic().getName(),
                        event
                );
                record.headers().add(HEADER_GROUP_ID_KEY, GROUP_ID.getBytes());
                producer.send(record);
            }
        }
    }

    public void close() {
        producers.values().forEach(KafkaProducer::close);
    }
}
