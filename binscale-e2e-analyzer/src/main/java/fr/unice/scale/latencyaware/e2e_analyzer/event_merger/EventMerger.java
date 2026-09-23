package fr.unice.scale.latencyaware.e2e_analyzer.event_merger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import fr.unice.scale.latencyaware.e2e_analyzer.entity.model.E2EEvent;
import fr.unice.scale.latencyaware.e2e_analyzer.mapper.E2EEventMapper;
import fr.unice.scale.latencyaware.e2e_analyzer.repository.E2EEventRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static fr.unice.scale.latencyaware.common.constant.CommonVariables.EXTERNAL_GROUP_NAME;

@Service
public class EventMerger {
    private final E2EEventRepository eventRepository;
    private final ObjectMapper objectMapper;

    public EventMerger(E2EEventRepository eventRepository) {
        this.eventRepository = eventRepository;
        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .enable(SerializationFeature.INDENT_OUTPUT);
        ;
    }

    public void eventMerger(List<ConsumerRecord<String, EventCustomer>> batch) {
        List<E2EEvent> events = batch.stream()
                .filter(Objects::nonNull)
                .map(E2EEventMapper::toE2EEvent)
                .collect(Collectors.toList());

        eventRepository.saveAll(events);
    }

    public Map<String, List<E2EEvent>> getAllEventTrackers() {
        List<Object[]> results = eventRepository.findAllGroupedByTrackerIdAsJson();
        Map<String, List<E2EEvent>> groupedEvents = new HashMap<>();

        for (Object[] row : results) {
            String trackerId = (String) row[0];
            String json = (String) row[1];

            List<E2EEvent> events = null;
            try {
                events = buildEventLatency(new HashSet<>(parseEventsFromJson(json)));


            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            groupedEvents.put(trackerId, events);
        }
        return groupedEvents;
    }

    public void cleanTables() {
        eventRepository.flush();
    }


    private List<E2EEvent> parseEventsFromJson(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, new TypeReference<List<E2EEvent>>() {
        });
    }

    private List<E2EEvent> buildEventLatency(Set<E2EEvent> e2EEventSet) {
        e2EEventSet.forEach(no -> {
            if (!no.getNodeOrigin().equals(EXTERNAL_GROUP_NAME))
                for (E2EEvent pn : e2EEventSet) {
                    if (pn.getNodeOrigin().equals(no.getPreviousNode())) {
                        no.setLatency(pn.getTimestamp());
                        break;
                    }
                }
        });
        return new ArrayList<>(e2EEventSet);
    }
}
