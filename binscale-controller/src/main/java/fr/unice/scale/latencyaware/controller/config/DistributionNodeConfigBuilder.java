package fr.unice.scale.latencyaware.controller.config;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import fr.unice.scale.latencyaware.controller.entity.distribution.GraphDistributionConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static fr.unice.scale.latencyaware.controller.constant.Variables.TOPICS_CONFIG_PATH;

public class DistributionNodeConfigBuilder {

    private static final Logger log = LoggerFactory.getLogger(DistributionNodeConfigBuilder.class);

    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    public static GraphDistributionConfig fromEnv() {
        try {
            return mapper.readValue(new File(TOPICS_CONFIG_PATH), GraphDistributionConfig.class);
        } catch (DatabindException e) {
            log.error("Graph distribution config at path: {} is malformed", TOPICS_CONFIG_PATH, e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error("Failed to read graph distribution config from path: {} {}", TOPICS_CONFIG_PATH, e);
            return new GraphDistributionConfig();
        }
    }
}
