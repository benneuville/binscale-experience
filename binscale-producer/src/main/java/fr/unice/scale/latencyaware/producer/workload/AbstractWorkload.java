package fr.unice.scale.latencyaware.producer.workload;

import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import fr.unice.scale.latencyaware.producer.config.BinscaleKafkaProducerConfig;
import fr.unice.scale.latencyaware.producer.entity.Workload;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractWorkload {
    public static float ArrivalRate = 0F;

    public abstract void startWorkload(BinscaleKafkaProducerConfig config, KafkaProducer<String, EventCustomer> producer) throws IOException, URISyntaxException, InterruptedException;
}
