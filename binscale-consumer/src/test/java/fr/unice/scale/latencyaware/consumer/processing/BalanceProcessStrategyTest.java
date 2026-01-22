package fr.unice.scale.latencyaware.consumer.processing;

import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import fr.unice.scale.latencyaware.consumer.entity.DistributedEventCustomer;
import fr.unice.scale.latencyaware.consumer.entity.DistributionConfig;
import fr.unice.scale.latencyaware.consumer.processing.strategy.BalanceProcessStrategy;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junitpioneer.jupiter.SetEnvironmentVariable;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BalanceProcessStrategyTest {
    BalanceProcessStrategy balanceProcessStrategy;

    DistributionConfig distributionConfig;

    ConsumerRecords<String, EventCustomer> consumerRecords;

    @BeforeEach
    public void setUp() {
        balanceProcessStrategy = new BalanceProcessStrategy(3., 2.75);
        distributionConfig = new DistributionConfig();
        Map<TopicPartition, List<ConsumerRecord<String, EventCustomer>>> events = new HashMap<>();
        events.put(new TopicPartition("topicA", 0), List.of(
                new ConsumerRecord<>("topicA", 0, 0L, "key1", new EventCustomer(1, "event1")),
                new ConsumerRecord<>("topicA", 0, 1L, "key2", new EventCustomer(2, "event2"))
        ));
        consumerRecords = new ConsumerRecords<>(events);
    }

    @Test
    @SetEnvironmentVariable(key = "TOPIC", value = "topicA")
    public void testStrategy() {
        List<DistributedEventCustomer> eventsDistributed = balanceProcessStrategy.process(distributionConfig, consumerRecords);
        assertEquals(0, eventsDistributed.size());
    }

}
