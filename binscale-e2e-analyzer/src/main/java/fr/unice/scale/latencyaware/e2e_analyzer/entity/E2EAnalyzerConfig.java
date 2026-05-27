package fr.unice.scale.latencyaware.e2e_analyzer.entity;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class E2EAnalyzerConfig {
    private List<Topic> topics;

    public E2EAnalyzerConfig() {
        this.topics = new ArrayList<>();
    }

    @JsonCreator
    public E2EAnalyzerConfig(@JsonProperty("topics") List<Topic> topics) {
        this.topics = topics;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }
}