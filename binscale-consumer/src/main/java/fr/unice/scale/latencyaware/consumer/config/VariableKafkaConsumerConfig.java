package fr.unice.scale.latencyaware.consumer.config;

import fr.unice.scale.latencyaware.common.config.KafkaConsumerConfig;
import fr.unice.scale.latencyaware.common.utils.CustomerDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.Properties;
import java.util.StringTokenizer;

import static fr.unice.scale.latencyaware.consumer.constant.Variables.*;

public class VariableKafkaConsumerConfig extends KafkaConsumerConfig {
    private final static String HEARTBEAT_INTERVAL_MS = "3000";
    private final static int MAX_POLL_RECORDS = 500;
    private final static String autoOffsetReset = "earliest";
    private final static String enableAutoCommit = "false";

    private final String clientRack;
    private final int sleep;
    private final Long messageCount;
    private final String additionalConfig;

    public VariableKafkaConsumerConfig(String bootstrapServers, String topic, String groupId,
                                       String clientRack, Long messageCount, int sleep,
                                       String additionalConfig) {
        super(bootstrapServers, topic, groupId);
        this.clientRack = clientRack;
        this.messageCount = messageCount;
        this.sleep = sleep;
        this.additionalConfig = additionalConfig;
    }

    public static VariableKafkaConsumerConfig fromEnv() {
        return new VariableKafkaConsumerConfig(BOOTSTRAP_SERVERS, TOPIC, GROUP_ID, CLIENT_RACK,
                MESSAGE_COUNT, SLEEP, ADDITIONAL_CONFIG);
    }

    public static Properties createProperties(VariableKafkaConsumerConfig config) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, config.getGroupId());

        if (config.getClientRack() != null) {
            props.put(ConsumerConfig.CLIENT_RACK_CONFIG, config.getClientRack());
        }
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, config.getAutoOffsetReset());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, MAX_POLL_RECORDS);

        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, config.getEnableAutoCommit());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                CustomerDeserializer.class.getName());
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, HEARTBEAT_INTERVAL_MS);

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

    public int getSleep() {
        return sleep;
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
                '}';
    }
}
