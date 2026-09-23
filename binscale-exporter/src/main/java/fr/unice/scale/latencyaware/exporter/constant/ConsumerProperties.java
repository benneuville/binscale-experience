package fr.unice.scale.latencyaware.exporter.constant;

import fr.unice.scale.latencyaware.common.utils.CustomerDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.Properties;

import static fr.unice.scale.latencyaware.common.constant.CommonVariables.KAFKA_BOOTSTRAP_SERVERS;
import static fr.unice.scale.latencyaware.common.constant.CommonVariables.STRING_DESERIALIZER;
import static fr.unice.scale.latencyaware.exporter.constant.Variables.GROUP_ID;

public class ConsumerProperties {

    public static Properties generateProperties() {

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, STRING_DESERIALIZER);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                CustomerDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000);

        return props;
    }
}
