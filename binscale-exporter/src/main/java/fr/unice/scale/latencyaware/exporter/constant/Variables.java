package fr.unice.scale.latencyaware.exporter.constant;

import fr.unice.scale.latencyaware.common.constant.CommonVariables;
import fr.unice.scale.latencyaware.common.doc.EnvVar;
import fr.unice.scale.latencyaware.common.utils.EnvUtils;

import java.util.ArrayList;
import java.util.List;

public class Variables extends CommonVariables {
    @EnvVar(description = "Headers targeted by exporter")
    public static final List<String> HEADERS_TARGETED = EnvUtils.envOrDefault("HEADERS_TARGETED", new ArrayList<>(), s -> List.of(s.split(",")));
    
}
