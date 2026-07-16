package fr.unice.scale.latencyaware.e2e_analyzer.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "e2e_event")
@BatchSize(size = 1000)
public class E2EEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "node_origin", nullable = false)
    private String nodeOrigin;

    @Column(nullable = false)
    private Instant timestamp;

    @Column(name = "tracker_id", nullable = false)
    @JsonIgnore
    private String trackerId;

    public E2EEvent() {
    }

    public E2EEvent(String nodeOrigin, Instant timestamp) {
        this.nodeOrigin = nodeOrigin;
        this.timestamp = timestamp;
    }

    public E2EEvent(String nodeOrigin, Instant timestamp, String trackerId) {
        this.nodeOrigin = nodeOrigin;
        this.timestamp = timestamp;
        this.trackerId = trackerId;
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

    @JsonIgnore
    public String getTrackerId() {
        return trackerId;
    }

    @JsonIgnore
    public void setTrackerId(String trackerId) {
        this.trackerId = trackerId;
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