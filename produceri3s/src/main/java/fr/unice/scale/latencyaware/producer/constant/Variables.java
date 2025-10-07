package fr.unice.scale.latencyaware.producer.constant;

import fr.unice.scale.latencyaware.common.error.exception.EnvironmentValueUndefineException;
import fr.unice.scale.latencyaware.common.utils.EnvUtils;

/**
 * Define environment variables and theirs default value
 */
public abstract class Variables {
    public static final String INPUT_WORKLOAD = EnvUtils.envOrDefault("INPUT_WORKLOAD", "defaultArrivalRatesm.csv");
    public static final String BOOTSTRAP_SERVERS = EnvUtils.envString("BOOTSTRAP_SERVERS");
    public static final String TOPIC = EnvUtils.envString("TOPIC");
    public static final int DELAY_MS = EnvUtils.envInt("DELAY_MS");
    public static final Long MESSAGES_COUNT = EnvUtils.envOrDefault("MESSAGE_COUNT", 10L);
    public static final String MESSAGE = EnvUtils.envOrDefault("MESSAGE", "Let's test assignors");
    public static final String PRODUCER_ACKS = EnvUtils.envOrDefault("PRODUCER_ACKS", "1");
    public static final String HEADERS = EnvUtils.envString("HEADERS");
    public static final String ADDITIONAL_CONFIG = EnvUtils.envOrDefault("ADDITIONAL_CONFIG", "");
    public static final WorkloadMapping WORKLOAD = EnvUtils.envOrDefault("WORKLOAD", WorkloadMapping.defaultWorkload, WorkloadMapping::getByName);
    public static final int SERVER_PORT = EnvUtils.envOrDefault("SERVER_PORT", 5002);


}
