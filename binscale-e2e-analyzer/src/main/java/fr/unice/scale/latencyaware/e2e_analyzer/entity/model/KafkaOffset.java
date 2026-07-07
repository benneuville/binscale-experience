package fr.unice.scale.latencyaware.e2e_analyzer.entity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "kafka_offsets")
public class KafkaOffset {
    @Id
    @Column(name = "topic_partition", unique = true)
    private String topicPartition;

    @Column(name = "last_consumed_offset")
    private long offset;

    @Column(name = "last_updated")
    private Instant lastUpdated;

    public KafkaOffset() {
    }

    public KafkaOffset(String topicPartition, long offset) {
        this.topicPartition = topicPartition;
        this.offset = offset;
        this.lastUpdated = Instant.now();
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }
}