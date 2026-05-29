package fr.unice.scale.latencyaware.e2e_analyzer.entity.event;

import java.util.HashMap;
import java.util.Map;

public class E2EEventTracker {
    private String id;
    private Map<String, E2EEvent> events;

    public E2EEventTracker(String id, E2EEvent event) {
        this.id = id;
        events = new HashMap<>();
        events.put(event.getNodeOrigin(), event);
    }

    public E2EEventTracker(String id) {
        this.id = id;
        events = new HashMap<>();
    }

    public Map<String, E2EEvent> getEvents() {
        return events;
    }

    public void setEvents(Map<String, E2EEvent> events) {
        this.events = events;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addEvent(E2EEvent event) {
        events.put(event.getNodeOrigin(), event);
    }
}
