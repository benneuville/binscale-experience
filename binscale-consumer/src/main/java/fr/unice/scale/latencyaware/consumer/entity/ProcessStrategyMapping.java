package fr.unice.scale.latencyaware.consumer.entity;

import fr.unice.scale.latencyaware.common.error.exception.NotFoundException;
import fr.unice.scale.latencyaware.consumer.processing.strategy.BalanceProcessStrategy;
import fr.unice.scale.latencyaware.consumer.processing.strategy.CustomProcessStrategy;
import fr.unice.scale.latencyaware.consumer.processing.strategy.DuplicateProcessStrategy;
import fr.unice.scale.latencyaware.consumer.processing.strategy.ProcessStrategy;

import java.util.Arrays;

public enum ProcessStrategyMapping {
    BALANCED("balanced", new BalanceProcessStrategy()),
    DUPLICATED("duplicated", new DuplicateProcessStrategy()),
    CUSTOM("custom", new CustomProcessStrategy());

    public final static ProcessStrategyMapping defaultStrategy = BALANCED;
    private final String name;
    private final ProcessStrategy strategyInstance;

    ProcessStrategyMapping(String strategyName, ProcessStrategy strategyInstance) {
        this.name = strategyName;
        this.strategyInstance = strategyInstance;
    }

    public static ProcessStrategyMapping getByName(String name) {
        return Arrays.stream(ProcessStrategyMapping.values()).filter(w -> w.getName().equals(name)).findFirst().orElseThrow(() -> new NotFoundException(name + " not found in " + ProcessStrategyMapping.class.getName()));
    }

    public String getName() {
        return name;
    }

    public ProcessStrategy getStrategyInstance() {
        return strategyInstance;
    }
}
