package fr.unice.scale.latencyaware.consumer.constant;

import fr.unice.scale.latencyaware.common.constant.CommonVariables;
import fr.unice.scale.latencyaware.common.doc.EnvVar;
import fr.unice.scale.latencyaware.common.utils.EnvUtils;
import fr.unice.scale.latencyaware.consumer.entity.ProcessStrategyMapping;

public final class Variables extends CommonVariables {
    // Environment variables
    @EnvVar(description = "Scale parameter")
    public static final Double SCALE = EnvUtils.envDouble("SCALE");
    @EnvVar(description = "Time to commit parameter")
    public static final Double TIME_TO_COMMIT = EnvUtils.envDouble("TIME_TO_COMMIT");
    @EnvVar(description = "Shape parameter")
    public static final Double SHAPE = EnvUtils.envDouble("SHAPE");
    @EnvVar(description = "WSLA parameter")
    public static final Double WSLA_S = EnvUtils.envDouble("WSLA");
    @EnvVar(description = "Async commit parameter. Have the Kafka commit to be asynchronous?")
    public static final Boolean ASYNC_COMMIT = EnvUtils.envBool("ASYNC_COMMIT");
    @EnvVar(description = "Bootstrap servers, Example : 'localhost:9092'")
    public static final String BOOTSTRAP_SERVERS = EnvUtils.envString("BOOTSTRAP_SERVERS");
    @EnvVar(description = "Sleep time")
    public static final Integer SLEEP = EnvUtils.envOrDefault("SLEEP", 0);
    @EnvVar(description = "Additional consumer configuration in the form 'key1=value1,key2=value2'")
    public static final String ADDITIONAL_CONFIG = EnvUtils.envOrDefault("ADDITIONAL_CONFIG", "");

    @EnvVar(description = "Message count")
    public static final Long MESSAGE_COUNT = EnvUtils.envOrDefault("MESSAGE_COUNT", 10L);
    @EnvVar(description = "Client rack")
    public static final String CLIENT_RACK = EnvUtils.envOrDefault("CLIENT_RACK", null);
    @EnvVar(description = "Max poll records parameter. Max number of events returned in a call to Kafka topic.")
    public static final int MAX_POLL_RECORDS = EnvUtils.envOrDefault("MAX_POLL_RECORDS", 500);
    @EnvVar(description = "Heartbeat interval in milliseconds")
    public static final String HEARTBEAT_INTERVAL_MS = EnvUtils.envOrDefault("HEARTBEAT_INTERVAL_MS", "3000");
    @EnvVar(description = "Processing strategy. Example : 'balanced', 'dupplicated', custom'")
    public static final ProcessStrategyMapping PROCESSING_STRATEGY = EnvUtils.envOrDefault("PROCESSING_STRATEGY", ProcessStrategyMapping.defaultStrategy, ProcessStrategyMapping::getByName);
    @EnvVar(description = "Headers to add to each message, comma separated. Example : 'header1:value1,header2:value2'", defaultValue = "\"\"")
    public static final String HEADERS = EnvUtils.envOrDefault("HEADERS", "");
    @EnvVar(description = "Producer acks config. Example : '0', '1' or 'all'")
    public static final String PRODUCER_ACKS = EnvUtils.envOrDefault("PRODUCER_ACKS", "0");
    @EnvVar(description = "Config path for Topics distribution")
    public static final String TOPICS_DISTRIBUTION_CONFIG_PATH = EnvUtils.envOrDefault("TOPICS_DISTRIBUTION_CONFIG_PATH", "/config/topics-config.yaml");
}
