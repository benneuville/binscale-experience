package fr.unice.scale.latencyaware.producer.constant;

import fr.unice.scale.latencyaware.common.error.exception.NotFoundException;
import fr.unice.scale.latencyaware.producer.workload.AbstractWorkload;
import fr.unice.scale.latencyaware.producer.workload.BiasedWorkload;
import fr.unice.scale.latencyaware.producer.workload.ConstantWorkload;
import fr.unice.scale.latencyaware.producer.workload.NonUniformWorkload;

import java.util.Arrays;

public enum WorkloadMapping {
    BIASED("biased", new BiasedWorkload()),
    CONSTANT("constant", new ConstantWorkload()),
    NON_UNIFORM("non_uniform", new NonUniformWorkload());

    public final static WorkloadMapping defaultWorkload = CONSTANT;
    private final String name;
    private final AbstractWorkload workload;

    WorkloadMapping(String name, AbstractWorkload workload) {
        this.name = name;
        this.workload = workload;
    }

    public static WorkloadMapping getByName(String name) throws NotFoundException {
        return Arrays.stream(WorkloadMapping.values()).filter(w -> w.getName().equals(name)).findFirst().orElseThrow(() -> new NotFoundException(name + " not found in " + WorkloadMapping.class.getName()));
    }

    public String getName() {
        return name;
    }

    public AbstractWorkload getWorkload() {
        return workload;
    }
}
