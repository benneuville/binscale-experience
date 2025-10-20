package fr.unice.scale.latencyaware.consumer.constant;

import fr.unice.scale.latencyaware.common.doc.EnvVar;
import fr.unice.scale.latencyaware.common.utils.EnvUtils;

public final class Variables {
    @EnvVar(description = "Scale parameter")
    public static final Double SCALE = EnvUtils.envDouble("SCALE");
    @EnvVar(description = "Time to commit parameter")
    public static final Double TIME_TO_COMMIT = EnvUtils.envDouble("TIME_TO_COMMIT");
    @EnvVar(description = "Shape parameter")
    public static final Double SHAPE = EnvUtils.envDouble("SHAPE");
    @EnvVar(description = "WSLA parameter")
    public static final Double WSLA_S = EnvUtils.envDouble("WSLA");
    @EnvVar(description = "Async commit parameter")
    public static final Boolean ASYNC_COMMIT = EnvUtils.envBool("ASYNC_COMMIT");
    @EnvVar(description = "Bootstrap servers, Example : 'localhost:9092'")
    public static final String BOOTSTRAP_SERVERS = EnvUtils.envString("BOOTSTRAP_SERVERS");
    @EnvVar(description = "Topic name. Example : 'testtopic1'")
    public static final String TOPIC = EnvUtils.envString("TOPIC");
    @EnvVar(description = "Group ID for the consumer. Example : 'testgroup1'")
    public static final String GROUP_ID = EnvUtils.envString("GROUP_ID");
    @EnvVar(description = "Sleep time")
    public static final Integer SLEEP = EnvUtils.envOrDefault("SLEEP", 0);
    @EnvVar(description = "Additional consumer configuration in the form 'key1=value1,key2=value2'")
    public static final String ADDITIONAL_CONFIG = EnvUtils.envOrDefault("ADDITIONAL_CONFIG", "");

    @EnvVar(description = "Message count")
    public static final Long MESSAGE_COUNT = EnvUtils.envOrDefault("MESSAGE_COUNT", 10L);
    @EnvVar(description = "Client rack")
    public static final String CLIENT_RACK = EnvUtils.envOrDefault("CLIENT_RACK", null);

}
