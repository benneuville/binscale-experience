package fr.unice.scale.latencyaware.consumer.emission;

import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import fr.unice.scale.latencyaware.consumer.config.BinscaleProducerConfig;
import fr.unice.scale.latencyaware.consumer.entity.DistributedEventCustomer;
import fr.unice.scale.latencyaware.consumer.entity.DistributionConfig;
import fr.unice.scale.latencyaware.consumer.entity.ProducerTopicDistribution;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventEmission {
    private final Map<ProducerTopicDistribution, KafkaProducer<String, EventCustomer>> producers;

    private long timestampNow = System.currentTimeMillis();

    public EventEmission(DistributionConfig config) {
        this.producers = new HashMap<>();
        if (config.getOutputTopics().isEmpty()) return;
        for (ProducerTopicDistribution topic : config.getOutputTopics()) {
            BinscaleProducerConfig producerConfig = new BinscaleProducerConfig(topic.getTopicName());
            KafkaProducer<String, EventCustomer> producer = new KafkaProducer<>(BinscaleProducerConfig.createProperties(producerConfig));
            this.producers.put(topic, producer);
        }
    }

    public void setTimestampNow() {
        this.timestampNow = System.currentTimeMillis();
    }

    public void publish(List<DistributedEventCustomer> processed) {
        if (producers.isEmpty()) return;
        for (DistributedEventCustomer distributedEvent : processed) {
            KafkaProducer<String, EventCustomer> producer = producers.get(distributedEvent.getTargetTopic());
            for (EventCustomer event : distributedEvent.getEvents()) {
                producer.send(new ProducerRecord<>(
                        distributedEvent.getTargetTopic().getTopicName(),
                        event
                ));
            }
        }
    }

    public void close() {
        producers.values().forEach(KafkaProducer::close);
    }
}
