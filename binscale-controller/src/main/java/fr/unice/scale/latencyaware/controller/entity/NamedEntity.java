package fr.unice.scale.latencyaware.controller.entity;

import fr.unice.scale.latencyaware.controller.entity.graph.Group;

public interface NamedEntity extends Group {
    String getName();

    String getGroupName();
}
