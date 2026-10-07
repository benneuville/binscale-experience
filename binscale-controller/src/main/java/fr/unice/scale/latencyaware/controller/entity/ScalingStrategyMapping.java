package fr.unice.scale.latencyaware.controller.entity;

import fr.unice.scale.latencyaware.controller.processing.*;

import java.util.function.Supplier;

public enum ScalingStrategyMapping {
    BINPACK_NAIVE("naive", ClassicScalerProcessor::new),
    BINPACK_EHANCED("ehanced", SeparateArrivalRateClassicScalerProcessor::new),
    BINPACK_BURST_EHANCED("burst", UpscalePropagateLagInSARClassicScalerProcessor::new),
    BINPACK_REACTIVE("reactive", ReactiveScalerProcessor::new);
    private final String name;

    private final Supplier<ScalerProcessor> processor;

    ScalingStrategyMapping(String name, Supplier<ScalerProcessor> processor) {
        this.name = name;
        this.processor = processor;
    }

    public static ScalingStrategyMapping getByName(String name) {
        for (ScalingStrategyMapping strategy : ScalingStrategyMapping.values()) {
            if (strategy.name.equalsIgnoreCase(name)) {
                return strategy;
            }
        }
        return null;
    }

    public ScalerProcessor getProcessor() {
        return processor.get();
    }
}
