package fr.unice.scale.latencyaware.producer.constant;

import fr.unice.scale.latencyaware.common.doc.EnvVar;
import fr.unice.scale.latencyaware.common.utils.EnvUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Define environment variables and theirs default value
 */
public abstract class Variables {

    @EnvVar(description = "List of partition weights, comma separated. Example : '1,1,1,1,1'")
    public static final List<Integer> PARTITION_WEIGHTS = EnvUtils.env("PARTITION_WEIGHTS", s -> Arrays.stream((s.split(",")))
            .map(Integer::parseInt)
            .collect(Collectors.toList()));
    @EnvVar(description = "Input workload file name. Example : 'defaultArrivalRatesm.csv'")
    public static final String INPUT_WORKLOAD = EnvUtils.envOrDefault("INPUT_WORKLOAD", "defaultArrivalRatesm.csv");
    @EnvVar(description = "Bootstrap servers, Example : 'localhost:9092'")
    public static final String BOOTSTRAP_SERVERS = EnvUtils.envString("BOOTSTRAP_SERVERS");
    @EnvVar(description = "Topic name. Example : 'test-topic'")
    public static final String TOPIC = EnvUtils.envString("TOPIC");
    @EnvVar(description = "Delay between two messages in milliseconds. Example : 1000")
    public static final Integer DELAY_MS = EnvUtils.envInt("DELAY_MS");
    @EnvVar(description = "Number of messages to send. Example : 10")
    public static final Long MESSAGES_COUNT = EnvUtils.envOrDefault("MESSAGE_COUNT", 10L);
    @EnvVar(description = "Message content. Example : 'Hello World !'")
    public static final String MESSAGE = EnvUtils.envOrDefault("MESSAGE", "Hello World !");
    @EnvVar(description = "Producer acks config. Example : '0', '1' or 'all'")
    public static final String PRODUCER_ACKS = EnvUtils.envOrDefault("PRODUCER_ACKS", "0");
    @EnvVar(description = "Headers to add to each message, comma separated. Example : 'header1:value1,header2:value2'", defaultValue = "\"\"")
    public static final String HEADERS = EnvUtils.envOrDefault("HEADERS", "");
    @EnvVar(description = "Additional producer configuration in the form 'key1=value1,key2=value2'", defaultValue = "\"\"")
    public static final String ADDITIONAL_CONFIG = EnvUtils.envOrDefault("ADDITIONAL_CONFIG", "");
    @EnvVar(description = "Workload mapping strategy. Example : 'constant'", defaultValue = "\"constant\"")
    public static final WorkloadMapping WORKLOAD = EnvUtils.envOrDefault("WORKLOAD", WorkloadMapping.defaultWorkload, WorkloadMapping::getByName);
    @EnvVar(description = "Server port for the health check endpoint")
    public static final Integer SERVER_PORT = EnvUtils.envOrDefault("SERVER_PORT", 5002);


}
