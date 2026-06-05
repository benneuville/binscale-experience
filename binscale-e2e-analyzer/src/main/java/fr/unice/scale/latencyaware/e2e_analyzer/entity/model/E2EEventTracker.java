package fr.unice.scale.latencyaware.e2e_analyzer.entity.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static fr.unice.scale.latencyaware.e2e_analyzer.constant.Variables.E2E_EVENT_TRACKER_FETCH_ALL;

@Entity
@NamedEntityGraph(
        name = E2E_EVENT_TRACKER_FETCH_ALL,
        attributeNodes = {
                @NamedAttributeNode("events")
        }
)
@Table(name = "e2e_event_tracker")
public class E2EEventTracker {
    @Id
    @Column(name = "event_id", nullable = false)
    private String id;

    @OneToMany(
            mappedBy = "tracker",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<E2EEvent> events = new ArrayList<>();

    public E2EEventTracker() {
    }

    public E2EEventTracker(String id) {
        this.id = id;
    }

    public E2EEventTracker(String id, List<E2EEvent> events) {
        this.id = id;
        this.events = events;
        this.events.forEach(e -> e.setTracker(this));
    }

    public void addEvent(E2EEvent event) {
        events.add(event);
        event.setTracker(this);
    }

    public void removeEvent(E2EEvent event) {
        events.remove(event);
        event.setTracker(null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<E2EEvent> getEvents() {
        return events;
    }

    public void setEvents(List<E2EEvent> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        E2EEventTracker that = (E2EEventTracker) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "EventTracker on " + getId() + " : " + getEvents();
    }
}