package fr.unice.scale.latencyaware.consumer.processing.strategy;

import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import fr.unice.scale.latencyaware.consumer.entity.DistributedEventCustomer;
import fr.unice.scale.latencyaware.consumer.entity.DistributionConfig;
import fr.unice.scale.latencyaware.consumer.entity.ProducerTopicDistribution;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BalanceProcessStrategy extends ProcessStrategy {
    private static int currentTopicDistributionPosition = 0;

    public BalanceProcessStrategy() {
        super();
    }

    @Override
    public List<DistributedEventCustomer> process(DistributionConfig config, ConsumerRecords<String, EventCustomer> events, Consumer<ConsumerRecord<String, EventCustomer>> eventProcessor) {
        List<DistributedEventCustomer> distributedEvents = new ArrayList<>();
        for (ProducerTopicDistribution topic : config.getOutput()) {
            distributedEvents.add(new DistributedEventCustomer(topic));
        }
        for (ConsumerRecord<String, EventCustomer> eventCustomer : events) {
            if (!config.getOutput().isEmpty()) {
                currentTopicDistributionPosition = (currentTopicDistributionPosition + 1) % config.getOutput().size();
                DistributedEventCustomer distributedEvent = distributedEvents.get(currentTopicDistributionPosition);
                distributedEvent.addEvent(eventCustomer.value());
            }
            // Simulate processing
            eventProcessor.accept(eventCustomer);
        }
        return distributedEvents;
    }
}
