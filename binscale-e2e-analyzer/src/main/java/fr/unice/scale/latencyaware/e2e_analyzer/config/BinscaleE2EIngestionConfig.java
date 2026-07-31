package fr.unice.scale.latencyaware.e2e_analyzer.config;

import fr.unice.scale.latencyaware.common.config.KafkaConsumerConfig;
import fr.unice.scale.latencyaware.common.utils.CustomerDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.StickyAssignor;

import java.util.Properties;
import java.util.StringTokenizer;

import static fr.unice.scale.latencyaware.common.constant.CommonVariables.STRING_DESERIALIZER;
import static fr.unice.scale.latencyaware.e2e_analyzer.constant.Variables.*;

public class BinscaleE2EIngestionConfig extends KafkaConsumerConfig {
    private final static String autoOffsetReset = "earliest";
    private final static String enableAutoCommit = "false";
    private final String heartbeatIntervalMs;
    private final String sessionTimeoutMs;
    private final int maxPollRecords;
    private final String clientRack;
    private final Long messageCount;
    private final String additionalConfig;

    public BinscaleE2EIngestionConfig(String bootstrapServers, String topic, String groupId,
                                      String clientRack, Long messageCount,
                                      String additionalConfig, int maxPollRecords, String sessionTimeout, String heartbeatIntervalMs) {
        super(bootstrapServers, topic, groupId);
        this.clientRack = clientRack;
        this.messageCount = messageCount;
        this.additionalConfig = additionalConfig;
        this.maxPollRecords = maxPollRecords;
        this.heartbeatIntervalMs = heartbeatIntervalMs;
        this.sessionTimeoutMs = sessionTimeout;
    }

    public static BinscaleE2EIngestionConfig fromEnv(String topic) {
        return new BinscaleE2EIngestionConfig(BOOTSTRAP_SERVERS, topic, "e2e_analyzer", CLIENT_RACK,
                MESSAGE_COUNT, ADDITIONAL_CONFIG, MAX_POLL_RECORDS, SESSION_TIMEOUT_MS, HEARTBEAT_INTERVAL_MS);
    }

    public static Properties createProperties(BinscaleE2EIngestionConfig config) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, config.getGroupId());

        if (config.getClientRack() != null) {
            props.put(ConsumerConfig.CLIENT_RACK_CONFIG, config.getClientRack());
        }
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, config.getAutoOffsetReset());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, config.getMaxPollRecords());
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 600000);

        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, config.getEnableAutoCommit());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, STRING_DESERIALIZER);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                CustomerDeserializer.class.getName());
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, config.getHeartbeatIntervalMs());
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, config.getSessionTimeoutMs());
        props.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, StickyAssignor.class.getName());
        // Added for sync
        props.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, "300000");
        props.put(ConsumerConfig.RECONNECT_BACKOFF_MS_CONFIG, "1000");
        props.put(ConsumerConfig.RECONNECT_BACKOFF_MAX_MS_CONFIG, "10000");
        props.put(ConsumerConfig.RETRY_BACKOFF_MS_CONFIG, "1000");
        props.put(ConsumerConfig.RETRY_BACKOFF_MAX_MS_CONFIG, "5000");
        

        if (!config.getAdditionalConfig().isEmpty()) {
            StringTokenizer tok = new StringTokenizer(config.getAdditionalConfig(), ", \t\n\r");
            while (tok.hasMoreTokens()) {
                String record = tok.nextToken();
                int endIndex = record.indexOf('=');
                if (endIndex == -1) {
                    throw new RuntimeException("Failed to parse Map from String");
                }
                String key = record.substring(0, endIndex);
                String value = record.substring(endIndex + 1);
                props.put(key.trim(), value.trim());
            }
        }
        return props;
    }

    public String getSessionTimeoutMs() {
        return sessionTimeoutMs;
    }

    public Properties toProperties() {
        return createProperties(this);
    }

    public String getAutoOffsetReset() {
        return autoOffsetReset;
    }

    public String getEnableAutoCommit() {
        return enableAutoCommit;
    }

    public String getClientRack() {
        return clientRack;
    }

    public Long getMessageCount() {
        return messageCount;
    }

    public String getAdditionalConfig() {
        return additionalConfig;
    }

    public int getMaxPollRecords() {
        return maxPollRecords;
    }

    public String getHeartbeatIntervalMs() {
        return heartbeatIntervalMs;
    }

    @Override
    public String toString() {
        return "KafkaConsumerConfig{" +
                "bootstrapServers='" + getBootstrapServers() + '\'' +
                ", topic='" + getTopic() + '\'' +
                ", groupId='" + getGroupId() + '\'' +
                ", autoOffsetReset='" + autoOffsetReset + '\'' +
                ", enableAutoCommit='" + enableAutoCommit + '\'' +
                ", clientRack='" + clientRack + '\'' +
                ", messageCount=" + messageCount +
                ", additionalConfig='" + additionalConfig + '\'' +
                '}' ;
    }
}

