package fr.unice.scale.latencyaware.exporter.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KafkaHeader {
    private String name;
    private String defaultValue;

    public KafkaHeader(@JsonProperty("name") String name, @JsonProperty("default") String defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getName() {
        return name;
    }
}
