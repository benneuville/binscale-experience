package fr.unice.scale.latencyaware.e2e_analyzer.event_merger;

import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import fr.unice.scale.latencyaware.common.error.exception.NotFoundException;
import fr.unice.scale.latencyaware.e2e_analyzer.entity.model.E2EEvent;
import fr.unice.scale.latencyaware.e2e_analyzer.entity.model.E2EEventTracker;
import fr.unice.scale.latencyaware.e2e_analyzer.mapper.E2EEventMapper;
import fr.unice.scale.latencyaware.e2e_analyzer.repository.E2EEventRepository;
import fr.unice.scale.latencyaware.e2e_analyzer.repository.E2EEventTrackerRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.stereotype.Service;

import java.util.List;

import static fr.unice.scale.latencyaware.e2e_analyzer.constant.Variables.E2E_EVENT_TRACKER_FETCH_ALL;

@Service
public class EventMerger {

    private final E2EEventTrackerRepository trackerRepository;
    private final E2EEventRepository eventRepository;

    public EventMerger(E2EEventTrackerRepository e2EEventTrackerRepository, E2EEventRepository eventRepository) {
        this.trackerRepository = e2EEventTrackerRepository;
        this.eventRepository = eventRepository;
    }

    public void eventMerger(List<ConsumerRecords<String, EventCustomer>> events) {
        for (ConsumerRecords<String, EventCustomer> eventList : events) {
            for (ConsumerRecord<String, EventCustomer> event : eventList) {
                if (!trackerRepository.exist(event.key())) {
                    trackerRepository.save(E2EEventMapper.toE2EEventTracker(event));
                } else {
                    E2EEvent e2eEvent = E2EEventMapper.toE2EEvent(event);
                    e2eEvent.setTracker(trackerRepository.findById(event.key()).orElseThrow(() -> new NotFoundException(event.key() + " not found in database.")));
                    eventRepository.save(e2eEvent);
                }
            }
        }
    }

    public List<E2EEventTracker> getAllEventTrackers() {
        return trackerRepository.findAll(E2E_EVENT_TRACKER_FETCH_ALL);
    }

    public void cleanTables() {
        eventRepository.cleanTable();
        trackerRepository.cleanTable();
    }
}
