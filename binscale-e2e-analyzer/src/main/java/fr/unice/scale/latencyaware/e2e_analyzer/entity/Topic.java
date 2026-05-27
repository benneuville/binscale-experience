package fr.unice.scale.latencyaware.e2e_analyzer.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Topic {

    private String name;

    public Topic() {
        this.name = "";
    }

    @JsonCreator
    public Topic(@JsonProperty("name") String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
