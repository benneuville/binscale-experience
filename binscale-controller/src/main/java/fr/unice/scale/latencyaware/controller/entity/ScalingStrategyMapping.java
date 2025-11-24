package fr.unice.scale.latencyaware.controller.entity;

public enum ScalingStrategyMapping {
    BINPACK_NAIVE("naive");
    private final String name;

    ScalingStrategyMapping(String name) {
        this.name = name;
    }

    public static ScalingStrategyMapping getByName(String name) {
        for (ScalingStrategyMapping strategy : ScalingStrategyMapping.values()) {
            if (strategy.name.equalsIgnoreCase(name)) {
                return strategy;
            }
        }
        return null;
    }
}
