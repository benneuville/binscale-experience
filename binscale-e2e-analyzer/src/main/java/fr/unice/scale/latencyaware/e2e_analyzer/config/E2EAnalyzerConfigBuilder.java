package fr.unice.scale.latencyaware.e2e_analyzer.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import fr.unice.scale.latencyaware.e2e_analyzer.entity.E2EAnalyzerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static fr.unice.scale.latencyaware.e2e_analyzer.constant.Variables.TOPICS_CONFIG_PATH;

public class E2EAnalyzerConfigBuilder {

    private static final Logger log = LoggerFactory.getLogger(E2EAnalyzerConfigBuilder.class);

    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    public static E2EAnalyzerConfig fromEnv() {
        try {
            return mapper.readValue(new File(TOPICS_CONFIG_PATH), E2EAnalyzerConfig.class);
        } catch (IOException e) {
            log.warn("Failed to read distribution config from path: {}", TOPICS_CONFIG_PATH);
            log.warn("Consumer only mode enabled.");
            return new E2EAnalyzerConfig();
        }
    }
}
