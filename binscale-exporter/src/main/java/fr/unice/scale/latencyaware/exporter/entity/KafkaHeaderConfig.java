package fr.unice.scale.latencyaware.exporter.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class KafkaHeaderConfig {

    List<KafkaHeader> headers;
    List<String> blackListedTopics;

    public KafkaHeaderConfig(@JsonProperty("headers") List<KafkaHeader> headers, @JsonProperty("blackListedTopics") List<String> blackListedTopics) {
        this.headers = headers;
        this.blackListedTopics = blackListedTopics;
    }

    public KafkaHeaderConfig() {
        this.headers = new ArrayList<>();
    }

    public List<KafkaHeader> getKafkaHeader() {
        return headers;
    }

    public boolean isInBlackListedTopics(String topic) {
        return blackListedTopics.contains(topic);
    }

}
