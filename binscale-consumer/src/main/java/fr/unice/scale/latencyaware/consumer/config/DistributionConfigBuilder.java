package fr.unice.scale.latencyaware.consumer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import fr.unice.scale.latencyaware.consumer.entity.DistributionConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static fr.unice.scale.latencyaware.consumer.constant.Variables.TOPICS_DISTRIBUTION_CONFIG_PATH;

public class DistributionConfigBuilder {

    private static final Logger log = LoggerFactory.getLogger(DistributionConfigBuilder.class);

    public static DistributionConfig fromEnv() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(new File(TOPICS_DISTRIBUTION_CONFIG_PATH), DistributionConfig.class);
        } catch (IOException e) {
            log.warn("Failed to read distribution config from path: {}", TOPICS_DISTRIBUTION_CONFIG_PATH);
            log.warn("Consumer only mode enabled.");
            return new DistributionConfig();
        }
    }
}
