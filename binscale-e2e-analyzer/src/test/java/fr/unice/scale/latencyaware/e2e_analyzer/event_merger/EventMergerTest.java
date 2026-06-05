package fr.unice.scale.latencyaware.e2e_analyzer.event_merger;

import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EventMergerTest {

    ConsumerRecords<String, EventCustomer> events;
    TopicPartition tp;
    EventMerger eventMerger;


/*    @BeforeEach
    public void setUp() {
        eventMerger = new EventMerger();
        tp = new TopicPartition("topic", 1);

        Map<TopicPartition, List<ConsumerRecord<String, EventCustomer>>> eventIns = new HashMap<>();
        eventIns.put(tp, new ArrayList<>());
        eventIns.get(tp).add(new ConsumerRecord<>("topic", 1, 0, "10", new EventCustomer(10, "yes")));

        events = new ConsumerRecords<>(eventIns);
    }


    @Test
    public void testCreateE2EEventTrackerFromExternalFlow() {
        eventMerger.eventMerger(List.of(events));

    }

    @Test
    public void testAddE2EEvent() {
        eventMerger.eventMerger(List.of(events));

        Map<TopicPartition, List<ConsumerRecord<String, EventCustomer>>> eventIns = new HashMap<>();
        eventIns.put(tp, new ArrayList<>());
        ConsumerRecord<String, EventCustomer> ev = new ConsumerRecord<>("topic", 1, 0, "10", new EventCustomer(10, "yes"));
        ev.headers().add(HEADER_GROUP_ID_KEY, "test".getBytes());
        eventIns.get(tp).add(ev);

        events = new ConsumerRecords<>(eventIns);
        eventMerger.eventMerger(List.of(events));

    }*/
}
