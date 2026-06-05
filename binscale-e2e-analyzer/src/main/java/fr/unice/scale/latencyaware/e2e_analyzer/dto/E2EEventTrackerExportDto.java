package fr.unice.scale.latencyaware.e2e_analyzer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.scale.latencyaware.e2e_analyzer.entity.model.E2EEvent;
import fr.unice.scale.latencyaware.e2e_analyzer.entity.model.E2EEventTracker;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class E2EEventTrackerExportDto {
    @JsonProperty
    private final String id;
    @JsonProperty
    private final List<E2EEvent> events;

    public E2EEventTrackerExportDto(E2EEventTracker tracker) {
        this.events = tracker.getEvents();
        this.id = tracker.getId();
    }

    @JsonProperty("endToEndDurationMs")
    public long getEndToEndDurationMs() {
        if (events == null || events.isEmpty()) {
            return -1;
        }

        Instant min = events.stream().map(E2EEvent::getTimestamp).min(Instant::compareTo).orElse(null);
        Instant max = events.stream().map(E2EEvent::getTimestamp).max(Instant::compareTo).orElse(null);

        return (min != null && max != null) ? Duration.between(min, max).toMillis() : -1;
    }

}