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

public class CustomProcessStrategy extends ProcessStrategy {
    public CustomProcessStrategy() {
        super();
    }

    @Override
    public List<DistributedEventCustomer> process(DistributionConfig config, ConsumerRecords<String, EventCustomer> events, Consumer<ConsumerRecord<String, EventCustomer>> eventProcessor) {
        List<DistributedEventCustomer> distributedEvents = new ArrayList<>();
        int originalEventSize = events.count();
        int index = 0;
        List<ConsumerRecord<String, EventCustomer>> eventList = new ArrayList<>();

        for (ConsumerRecord<String, EventCustomer> eventCustomer : events) {
            // Simulate processing
            eventProcessor.accept(eventCustomer);
            eventList.add(eventCustomer);
        }

        for (ProducerTopicDistribution topic : config.getOutput()) {
            int numberEventsForTopic = Math.round(originalEventSize * topic.getRatio());
            DistributedEventCustomer distributedEvent = new DistributedEventCustomer(topic);
            for (int i = 0; i < numberEventsForTopic; i++) {
                distributedEvent.addEvent(eventList.get((index + i) % originalEventSize));
            }
            distributedEvents.add(distributedEvent);
            index = (index + numberEventsForTopic) % originalEventSize;
        }
        return distributedEvents;
    }

}
