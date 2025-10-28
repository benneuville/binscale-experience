package fr.unice.scale.latencyaware.consumer.config;

import fr.unice.scale.latencyaware.common.utils.CustomerSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;
import java.util.StringTokenizer;

import static fr.unice.scale.latencyaware.common.constant.CommonVariables.STRING_DESERIALIZER;
import static fr.unice.scale.latencyaware.consumer.constant.Variables.*;
import static org.apache.kafka.clients.producer.ProducerConfig.BATCH_SIZE_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.MAX_BLOCK_MS_CONFIG;


public class BinscaleProducerConfig {
    private static final Logger log = LogManager.getLogger(BinscaleProducerConfig.class);
    private final String bootstrapServers;
    private final String acks;
    private final String headers;
    private final String additionalConfig;
    private String topic;

    public BinscaleProducerConfig(String bootstrapServers, String acks, String additionalConfig, String headers) {
        this.bootstrapServers = bootstrapServers;
        this.acks = acks;
        this.headers = headers;
        this.additionalConfig = additionalConfig;
    }

    public BinscaleProducerConfig(String topic) {
        this.topic = topic;
        this.bootstrapServers = BOOTSTRAP_SERVERS;
        this.acks = PRODUCER_ACKS;
        this.headers = HEADERS;
        this.additionalConfig = ADDITIONAL_CONFIG;
    }

    @Deprecated
    public static BinscaleProducerConfig fromEnv() {
        return new BinscaleProducerConfig(BOOTSTRAP_SERVERS,
                PRODUCER_ACKS, ADDITIONAL_CONFIG, HEADERS);
    }

    /* The Properties class represents a persistent set of properties. The Properties can be saved to a stream or loaded from a stream. Each key and its corresponding value in the property list is a string.

    A property list can contain another property list as its "defaults"; this second property list is searched if the property key is not found in the original property list.

    Because Properties inherits from Hashtable, the put and putAll methods can be applied to a Properties object. Their use is strongly discouraged as they allow the caller to insert entries whose keys or values are not Strings. The setProperty method should be used instead. If the store or save method is called on a "compromised" Properties object that contains a non-String key or value, the call will fail. Similarly, the call to the propertyNames or list method will fail if it is called on a "compromised" Properties object that contains a non-String key. */
    public static Properties createProperties(BinscaleProducerConfig config) {
        log.info("==================================================");
        log.info("Creating Properties");
        log.info("==================================================");
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, STRING_DESERIALIZER);
        // ACK
        props.put(ProducerConfig.ACKS_CONFIG, config.getAcks());
        // NO BLOCK, EVEN IF BROKER NOT AVAILABLE
        props.put(MAX_BLOCK_MS_CONFIG, MAX_BLOCK_MS_CONFIG);
        // NO BATCH SENDING
        props.put(BATCH_SIZE_CONFIG, BATCH_SIZE_CONFIG);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CustomerSerializer.class.getName());
        if (!config.getAdditionalConfig().isEmpty()) {
            StringTokenizer tok =
                    new StringTokenizer(config.getAdditionalConfig(), ", \t\n\r");
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

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public String getTopic() {
        return topic;
    }

    public String getAcks() {
        return acks;
    }

    public String getHeaders() {
        return headers;
    }

    public String getAdditionalConfig() {
        return additionalConfig;
    }

    @Override
    public String toString() {
        return "KafkaProducerConfig{" +
                "bootstrapServers='" + bootstrapServers + '\'' +
                ", topic='" + topic + '\'' +
                ", acks='" + acks + '\'' +
                ", headers='" + headers + '\'' +
                ", additionalConfig='" + additionalConfig + '\'' +
                '}';
    }
}
