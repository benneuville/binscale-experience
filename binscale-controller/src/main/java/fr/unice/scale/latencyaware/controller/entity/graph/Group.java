package fr.unice.scale.latencyaware.controller.entity.graph;

import fr.unice.scale.latencyaware.controller.entity.Consumer;

import java.util.List;

public interface Group {
    public void setAssignment(List<Consumer> assignments);
}
