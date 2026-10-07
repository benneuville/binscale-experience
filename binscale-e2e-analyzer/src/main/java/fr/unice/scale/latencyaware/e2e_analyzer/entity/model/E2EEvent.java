package fr.unice.scale.latencyaware.e2e_analyzer.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;

import java.time.Duration;
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

    @Column(name = "previous_node", nullable = false)
    private String previousNode;

    @Column(nullable = false)
    private Instant timestamp;

    @Column(name = "tracker_id", nullable = false)
    @JsonIgnore
    private String trackerId;

    // Latency between previousNode and nodeOrigin
    @Transient
    private long latency = 0;

    public E2EEvent() {
    }

    public E2EEvent(String nodeOrigin, Instant timestamp) {
        this.nodeOrigin = nodeOrigin;
        this.timestamp = timestamp;
    }

    public E2EEvent(String nodeOrigin, String previousNode, Instant timestamp, String trackerId) {
        this.nodeOrigin = nodeOrigin;
        this.previousNode = previousNode;
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

    public String getPreviousNode() {
        return this.previousNode;
    }

    public void setPreviousNode(String previousNode) {
        this.previousNode = previousNode;
    }

    public long getLatency() {
        return this.latency;
    }

    public void setLatency(int latency) {
        this.latency = latency;
    }

    public void setLatency(Instant previousNodeInstant) {
        this.latency = Math.abs(Duration.between(previousNodeInstant, timestamp).toMillis());
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
        return Objects.equals(id, e2EEvent.id)
                || (
                Objects.equals(timestamp, e2EEvent.timestamp)
                        && nodeOrigin.equals(e2EEvent.nodeOrigin)
                        && previousNode.equals(e2EEvent.previousNode));
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp.getEpochSecond()) + Objects.hash(nodeOrigin) + Objects.hash(previousNode);
    }

    @Override
    public String toString() {
        return getNodeOrigin() + " at " + getTimestamp().toString();
    }
}