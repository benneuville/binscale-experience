package fr.unice.scale.latencyaware.common.emission.interceptor;

import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

public class BinscaleKafkaProducer<K, V> extends KafkaProducer<K, V> {
    public BinscaleKafkaProducer(Properties properties) {
        super(properties);
    }
}
