package fr.unice.scale.latencyaware.e2e_analyzer.constant;

import fr.unice.scale.latencyaware.common.constant.CommonVariables;
import fr.unice.scale.latencyaware.common.doc.EnvVar;
import fr.unice.scale.latencyaware.common.utils.EnvUtils;

public final class Variables extends CommonVariables {

    // Environment variables
    @EnvVar(description = "Async commit parameter. Have the Kafka commit to be asynchronous?")
    public static final Boolean ASYNC_COMMIT = EnvUtils.envBool("ASYNC_COMMIT");
    @EnvVar(description = "Bootstrap servers, Example : 'localhost:9092'")
    public static final String BOOTSTRAP_SERVERS = EnvUtils.envString("BOOTSTRAP_SERVERS");
    @EnvVar(description = "Additional consumer configuration in the form 'key1=value1,key2=value2'")
    public static final String ADDITIONAL_CONFIG = EnvUtils.envOrDefault("ADDITIONAL_CONFIG", "");
    @EnvVar(description = "Time to commit parameter")
    public static final Double TIME_TO_COMMIT = EnvUtils.envDouble("TIME_TO_COMMIT");
    @EnvVar(description = "Message count")
    public static final Long MESSAGE_COUNT = EnvUtils.envOrDefault("MESSAGE_COUNT", 10L);
    @EnvVar(description = "Client rack")
    public static final String CLIENT_RACK = EnvUtils.envOrDefault("CLIENT_RACK", null);
    public static final int MAX_POLL_RECORDS = EnvUtils.envOrDefault("MAX_POLL_RECORDS", 200);
    @EnvVar(description = "Kafka session timeout in milliseconds")
    public static final String SESSION_TIMEOUT_MS = EnvUtils.envOrDefault("SESSION_TIMEOUT_MS", "3000");
    @EnvVar(description = "Heartbeat interval in milliseconds")
    public static final String HEARTBEAT_INTERVAL_MS = EnvUtils.envOrDefault("HEARTBEAT_INTERVAL_MS", "1000");
    @EnvVar(description = "Path to topic config file")
    public static final String TOPICS_CONFIG_PATH = EnvUtils.envOrDefault("TOPICS_CONFIG_PATH", "/config/e2e-analyzer-config.yaml");

    @EnvVar(description = "Path to topic config file")
    public static final String EXPORT_PATH = EnvUtils.envOrDefault("EXPORT_PATH", "/export/export-e2e-analyze.json");

    public static final String E2E_EVENT_TRACKER_FETCH_ALL = "e2e_event_tracker_fetch_all";
}
