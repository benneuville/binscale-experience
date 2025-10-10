package fr.unice.scale.latencyaware.producer.constant;

import fr.unice.scale.latencyaware.common.utils.EnvUtils;

import java.util.function.Supplier;

/**
 * Define environment variables and theirs default value
 */
public abstract class Variables {
    public static final Supplier<String> INPUT_WORKLOAD = () -> EnvUtils.envOrDefault("INPUT_WORKLOAD", "defaultArrivalRatesm.csv");
    public static final Supplier<String> BOOTSTRAP_SERVERS = () -> EnvUtils.envString("BOOTSTRAP_SERVERS");
    public static final Supplier<String> TOPIC = () -> EnvUtils.envString("TOPIC");
    public static final Supplier<Integer> DELAY_MS = () -> EnvUtils.envInt("DELAY_MS");
    public static final Supplier<Long> MESSAGES_COUNT = () -> EnvUtils.envOrDefault("MESSAGE_COUNT", 10L);
    public static final Supplier<String> MESSAGE = () -> EnvUtils.envOrDefault("MESSAGE", "Let's test assignors");
    public static final Supplier<String> PRODUCER_ACKS = () -> EnvUtils.envOrDefault("PRODUCER_ACKS", "1");
    public static final Supplier<String> HEADERS = () -> EnvUtils.envString("HEADERS");
    public static final Supplier<String> ADDITIONAL_CONFIG = () -> EnvUtils.envOrDefault("ADDITIONAL_CONFIG", "");
    public static final Supplier<WorkloadMapping> WORKLOAD = () -> EnvUtils.envOrDefault("WORKLOAD", WorkloadMapping.defaultWorkload, WorkloadMapping::getByName);
    public static final Supplier<Integer> SERVER_PORT = () -> EnvUtils.envOrDefault("SERVER_PORT", 5002);


}
