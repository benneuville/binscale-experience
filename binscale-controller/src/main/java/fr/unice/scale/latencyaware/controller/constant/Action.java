package fr.unice.scale.latencyaware.controller.constant;

public enum Action {
    NONE,
    UP,
    DOWN,
    REASS;

    public boolean isAction() {
        return !this.equals(NONE);
    }
}
