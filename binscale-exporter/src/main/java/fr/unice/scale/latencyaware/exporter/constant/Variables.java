package fr.unice.scale.latencyaware.exporter.constant;

import fr.unice.scale.latencyaware.common.constant.CommonVariables;
import fr.unice.scale.latencyaware.common.doc.EnvVar;
import fr.unice.scale.latencyaware.common.utils.EnvUtils;

public class Variables extends CommonVariables {

    @EnvVar(description = "Path to config file")
    public static final String HEADERS_CONFIG_PATH = EnvUtils.envOrDefault("HEADERS_CONFIG_PATH", "/config/exporter-config.yaml");

    public static final String GROUP_ID = "binscale_exporter";

    public static final String BLACK_LIST_TOPIC = "final-queue";
}
