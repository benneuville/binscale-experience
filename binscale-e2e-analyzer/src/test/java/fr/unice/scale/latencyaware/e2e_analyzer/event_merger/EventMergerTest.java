package fr.unice.scale.latencyaware.e2e_analyzer.event_merger;

import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import fr.unice.scale.latencyaware.e2e_analyzer.entity.event.E2EEventMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.unice.scale.latencyaware.common.constant.CommonVariables.EXTERNAL_GROUP_NAME;
import static fr.unice.scale.latencyaware.common.constant.CommonVariables.HEADER_GROUP_ID_KEY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class EventMergerTest {

    ConsumerRecords<String, EventCustomer> events;
    TopicPartition tp;
    EventMerger eventMerger;

    E2EEventMapper map;

    @BeforeEach
    public void setUp() {
        eventMerger = new EventMerger();
        tp = new TopicPartition("topic", 1);

        Map<TopicPartition, List<ConsumerRecord<String, EventCustomer>>> eventIns = new HashMap<>();
        eventIns.put(tp, new ArrayList<>());
        eventIns.get(tp).add(new ConsumerRecord<>("topic", 1, 0, "10", new EventCustomer(10, "yes")));

        events = new ConsumerRecords<>(eventIns);
        map = new E2EEventMapper();
    }


    @Test
    public void testCreateE2EEventTrackerFromExternalFlow() {
        eventMerger.eventMerger(map, List.of(events));

        assertEquals(1, map.size());
        assertTrue(map.containsKey("10"));
        assertEquals(1, map.get("10").getEvents().size());
        assertTrue(map.get("10").getEvents().containsKey(EXTERNAL_GROUP_NAME));
    }

    @Test
    public void testAddE2EEvent() {
        eventMerger.eventMerger(map, List.of(events));

        Map<TopicPartition, List<ConsumerRecord<String, EventCustomer>>> eventIns = new HashMap<>();
        eventIns.put(tp, new ArrayList<>());
        ConsumerRecord<String, EventCustomer> ev = new ConsumerRecord<>("topic", 1, 0, "10", new EventCustomer(10, "yes"));
        ev.headers().add(HEADER_GROUP_ID_KEY, "test".getBytes());
        eventIns.get(tp).add(ev);

        events = new ConsumerRecords<>(eventIns);
        eventMerger.eventMerger(map, List.of(events));

        assertEquals(1, map.size());
        assertTrue(map.containsKey("10"));
        assertEquals(2, map.get("10").getEvents().size());
        assertTrue(map.get("10").getEvents().containsKey("test"));

    }
}
