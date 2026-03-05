package fr.unice.scale.latencyaware.consumer.entity;

import fr.unice.scale.latencyaware.common.error.exception.NotFoundException;
import fr.unice.scale.latencyaware.consumer.processing.strategy.BalanceProcessStrategy;
import fr.unice.scale.latencyaware.consumer.processing.strategy.CustomProcessStrategy;
import fr.unice.scale.latencyaware.consumer.processing.strategy.DuplicateProcessStrategy;
import fr.unice.scale.latencyaware.consumer.processing.strategy.ProcessStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static fr.unice.scale.latencyaware.consumer.constant.Variables.SCALE;
import static fr.unice.scale.latencyaware.consumer.constant.Variables.SHAPE;

public enum ProcessStrategyMapping {
    BALANCED("balanced", new BalanceProcessStrategy(SCALE, SHAPE)),
    DUPLICATED("duplicated", new DuplicateProcessStrategy(SCALE, SHAPE)),
    CUSTOM("custom", new CustomProcessStrategy(SCALE, SHAPE));

    public final static ProcessStrategyMapping defaultStrategy = BALANCED;
    private final Logger logger = LoggerFactory.getLogger(ProcessStrategyMapping.class);
    private final String name;
    private final ProcessStrategy strategyInstance;

    private ProcessStrategyMapping(String strategyName, ProcessStrategy strategyInstance) {
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
