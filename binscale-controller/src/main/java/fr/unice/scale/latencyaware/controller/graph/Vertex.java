package fr.unice.scale.latencyaware.controller.graph;

import fr.unice.scale.latencyaware.controller.entity.ConsumerGroup;

public class Vertex {

    int label;
    ConsumerGroup g;
    boolean isVisited;

    Vertex(int label,  ConsumerGroup g) {
        this.label = label;
        isVisited = false;
        this.g = g;
    }

    public ConsumerGroup getG() {
        return g;
    }


    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }


    @Override
    public String toString() {
        return "Vertex{" +
                "label=" + label +
                '}' + "\n";
    }
}
