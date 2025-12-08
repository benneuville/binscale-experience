package fr.unice.scale.latencyaware.producer.constant;

import fr.unice.scale.latencyaware.common.error.exception.NotFoundException;
import fr.unice.scale.latencyaware.producer.workload.*;

import java.util.Arrays;

public enum WorkloadMapping {
    BIASED("biased", new BiasedWorkload()),
    CONSTANT("constant", new ConstantWorkload()),
    NON_UNIFORM("non_uniform", new NonUniformWorkload()),
    @Deprecated
    OLD("old", new OldWorkload()),
    @Deprecated
    OLD_SKEWED("old_skewed", new OldWorkloadSkewed());

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
