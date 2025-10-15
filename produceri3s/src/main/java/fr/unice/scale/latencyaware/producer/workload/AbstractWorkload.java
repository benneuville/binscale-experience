package fr.unice.scale.latencyaware.producer.workload;

import fr.unice.scale.latencyaware.common.entity.Customer;
import fr.unice.scale.latencyaware.producer.config.KafkaProducerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractWorkload {
    public static float ArrivalRate = 0F;

    public abstract void startWorkload(KafkaProducerConfig config, KafkaProducer<String, Customer> producer) throws IOException, URISyntaxException, InterruptedException;
}
