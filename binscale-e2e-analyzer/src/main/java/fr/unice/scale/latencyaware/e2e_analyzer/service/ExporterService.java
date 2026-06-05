package fr.unice.scale.latencyaware.e2e_analyzer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fr.unice.scale.latencyaware.e2e_analyzer.dto.E2EEventTrackerExportDto;
import fr.unice.scale.latencyaware.e2e_analyzer.entity.model.E2EEventTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static fr.unice.scale.latencyaware.e2e_analyzer.constant.Variables.EXPORT_PATH;

@Service
public class ExporterService {
    private static final Logger logger = LoggerFactory.getLogger(ExporterService.class);
    private final ObjectMapper objectMapper;

    public ExporterService() {
        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void exportEvents(List<E2EEventTracker> eventTrackers) {
        exportEvents(eventTrackers, EXPORT_PATH);
    }

    public void exportEvents(List<E2EEventTracker> eventTrackers, String filename) {
        try {
            List<E2EEventTrackerExportDto> dtos = eventTrackers.stream()
                    .map(E2EEventTrackerExportDto::new)
                    .collect(Collectors.toList());
            Map<String, Object> exportData = Map.of(
                    "metadata", Map.of(
                            "timestamp", Instant.now().toString(),
                            "count", eventTrackers.size()
                    ),
                    "data", dtos
            );

            objectMapper.writeValue(new File(filename), exportData);
            logger.info("Exported : {}", filename);
        } catch (IOException e) {
            logger.error("Error in export", e);
            throw new RuntimeException("Error export JSON file", e);
        }
    }
}