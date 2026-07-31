package fr.unice.scale.latencyaware.exporter.config;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import fr.unice.scale.latencyaware.exporter.entity.KafkaHeaderConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static fr.unice.scale.latencyaware.exporter.constant.Variables.HEADERS_CONFIG_PATH;

public class KafkaHeaderConfigBuilder {

    private static final Logger log = LoggerFactory.getLogger(KafkaHeaderConfigBuilder.class);

    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    public static KafkaHeaderConfig fromEnv() {
        try {
            System.out.println(mapper.readValue(new File(HEADERS_CONFIG_PATH), KafkaHeaderConfig.class));
            return mapper.readValue(new File(HEADERS_CONFIG_PATH), KafkaHeaderConfig.class);
        } catch (DatabindException e) {
            log.error("Header config at path: {} is malformed", HEADERS_CONFIG_PATH, e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error("Failed to read header config from path: {} {}", HEADERS_CONFIG_PATH, e);
            return new KafkaHeaderConfig();
        }
    }
}
