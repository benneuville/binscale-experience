package fr.unice.scale.latencyaware.e2e_analyzer.event_merger;

import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import fr.unice.scale.latencyaware.e2e_analyzer.entity.event.E2EEventMapper;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.header.Header;

import java.util.List;

import static fr.unice.scale.latencyaware.common.constant.CommonVariables.HEADER_EVENT_ID;
import static fr.unice.scale.latencyaware.common.constant.CommonVariables.HEADER_GROUP_ID_KEY;

public class EventMerger {

    public EventMerger() {

    }

    public void eventMerger(E2EEventMapper map, List<ConsumerRecords<String, EventCustomer>> events) {
        for (ConsumerRecords<String, EventCustomer> event : events) {
            event.forEach(map::addEvent);
        }
    }
}
