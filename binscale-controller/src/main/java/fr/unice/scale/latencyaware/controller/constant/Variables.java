package fr.unice.scale.latencyaware.controller.constant;

import fr.unice.scale.latencyaware.common.doc.EnvVar;
import fr.unice.scale.latencyaware.common.utils.EnvUtils;

public final class Variables {
    @EnvVar(description = "DI value in seconds for the controller loop sleep time")
    public static final Double DI = EnvUtils.envDouble("DI");
    @EnvVar(description = "WSLA value in seconds")
    public static final Double WSLA = EnvUtils.envDouble("WSLA");
    // REBALANCING TIME will have to be determined and not a static value
    @EnvVar(description = "REB_TIME value in seconds for the rebalancing time")
    public static final Double REB_TIME = EnvUtils.envDouble("REB_TIME");
    @EnvVar(description = "MU value in seconds for the controller calculations")
    public static final Double MU = EnvUtils.envDouble("MU");
    @EnvVar(description = "FUP value for the upscaling threshold")
    public static final Double FUP = EnvUtils.envDouble("FUP");
    @EnvVar(description = "FDOWN value for the downscaling threshold")
    public static final Double FDOWN = EnvUtils.envDouble("FDOWN");
    @EnvVar(description = "Initial size of the consumer group")
    public static final Integer INIT_SIZE = EnvUtils.envInt("INIT_SIZE");
    @EnvVar(description = "Bootstrap servers. Example : 'localhost:9092'")
    public static final String BOOTSTRAP_SERVERS = EnvUtils.envString("BOOTSTRAP_SERVERS");
    @EnvVar(description = "Topic name. Example : 'testtopic1'")
    public static final String TOPIC = EnvUtils.envString("TOPIC");
    @EnvVar(description = "Group id. Example : 'testgroup1'")
    public static final String GROUP_ID = EnvUtils.envString("GROUP_ID");
    @EnvVar(description = "Number of partitions for the topic")
    public static final Integer NUMBER_PARTITIONS = EnvUtils.envInt("NUMBER_PARTITIONS");
}
