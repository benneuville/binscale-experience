package fr.unice.scale.latencyaware.e2e_analyzer.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "e2e_event")
public class E2EEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "node_origin", nullable = false)
    private String nodeOrigin;

    @Column(nullable = false)
    private Instant timestamp;
    @ManyToOne
    @JoinColumn(name = "tracker_id", nullable = false)
    @JsonIgnore
    private E2EEventTracker tracker;

    public E2EEvent() {
    }

    public E2EEvent(String nodeOrigin, Instant timestamp) {
        this.nodeOrigin = nodeOrigin;
        this.timestamp = timestamp;
    }

    public E2EEvent(String nodeOrigin, Instant timestamp, E2EEventTracker tracker) {
        this.nodeOrigin = nodeOrigin;
        this.timestamp = timestamp;
        this.tracker = tracker;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNodeOrigin() {
        return nodeOrigin;
    }

    public void setNodeOrigin(String nodeOrigin) {
        this.nodeOrigin = nodeOrigin;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public E2EEventTracker getTracker() {
        return tracker;
    }

    public void setTracker(E2EEventTracker tracker) {
        this.tracker = tracker;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        E2EEvent e2EEvent = (E2EEvent) o;
        return Objects.equals(id, e2EEvent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return getNodeOrigin() + " at " + getTimestamp().toString();
    }
}