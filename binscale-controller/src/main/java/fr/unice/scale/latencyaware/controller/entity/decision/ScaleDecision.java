package fr.unice.scale.latencyaware.controller.entity.decision;

import fr.unice.scale.latencyaware.controller.constant.Action;
import fr.unice.scale.latencyaware.controller.entity.Consumer;
import fr.unice.scale.latencyaware.controller.entity.Partition;
import fr.unice.scale.latencyaware.controller.entity.calculation.ConsumerCalculation;

import java.util.List;

public class ScaleDecision {
    private Action action;

    private List<ConsumerCalculation> associations;

    public ScaleDecision(List<ConsumerCalculation> associations, Action action) {
        this.associations = associations;
        this.action = action;
    }

    public ScaleDecision() {
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public List<ConsumerCalculation> getAssociations() {
        return associations;
    }

    public void setAssociations(List<ConsumerCalculation> associations) {
        this.associations = associations;
    }
}
