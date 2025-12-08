package fr.unice.scale.latencyaware.controller.constant;

import fr.unice.scale.latencyaware.common.constant.CommonVariables;
import fr.unice.scale.latencyaware.common.doc.EnvVar;
import fr.unice.scale.latencyaware.common.utils.EnvUtils;
import fr.unice.scale.latencyaware.controller.entity.ScalingStrategyMapping;

public final class Variables extends CommonVariables {

    @EnvVar(description = "DI value in milliseconds for the controller loop sleep time")
    public static final Double DI = EnvUtils.envDouble("DI");
    @EnvVar(description = "Number of partitions for the topic")
    public static final Integer NUMBER_PARTITIONS = EnvUtils.envInt("NUMBER_PARTITIONS");
    // REBALANCING TIME will have to be determined and not a static value
    @EnvVar(description = "REB_TIME value in seconds for the rebalancing time")
    public static final Double REB_TIME = EnvUtils.envDouble("REB_TIME");
    @EnvVar(description = "FUP value for the upscaling threshold")
    public static final Double FUP = EnvUtils.envDouble("FUP");
    @EnvVar(description = "FDOWN value for the downscaling threshold")
    public static final Double FDOWN = EnvUtils.envDouble("FDOWN");
    @EnvVar(description = "Bootstrap servers. Example : 'localhost:9092'")
    public static final String BOOTSTRAP_SERVERS = EnvUtils.envString("BOOTSTRAP_SERVERS");
    @EnvVar(description = "Path to config file")
    public static final String TOPICS_CONFIG_PATH = EnvUtils.envOrDefault("TOPICS_CONFIG_PATH", "/config/controller-config.yaml");
    @EnvVar(description = "Range time in seconds for the metrics calculation")
    public static final Integer REQUEST_TIME_RANGE = EnvUtils.envOrDefault("REQUEST_TIME_RANGE", 2);
    @EnvVar(description = "Range time in seconds for the metrics calculation")
    public static final String REQUEST_TIME_UNIT = EnvUtils.envOrDefault("REQUEST_TIME_UNIT", "s");
    @EnvVar(description = "Scaling strategy selector between : 'naive'")
    public static final ScalingStrategyMapping SCALING_STRATEGY = EnvUtils.envOrDefault("SCALING_STRATEGY", ScalingStrategyMapping.BINPACK_NAIVE, ScalingStrategyMapping::getByName);
    public static final String ARRIVAL_SERVICE = "arrivalservice";

    // Constants

    public static String getTimeRange() {
        return REQUEST_TIME_RANGE + REQUEST_TIME_UNIT;
    }
}
