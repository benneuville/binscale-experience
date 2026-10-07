package fr.unice.scale.latencyaware.common.config;

import org.apache.kafka.clients.admin.AdminClientConfig;

import java.util.Properties;

public class KafkaAdminConfig {
    private final String bootstrapServers;

    public KafkaAdminConfig(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public static Properties createProperties(KafkaAdminConfig config) {
        Properties props = new Properties();
        props.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
        return props;
    }

    public String getBootstrapServers() {
        return bootstrapServers;
    }
}
