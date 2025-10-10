package fr.unice.scale.latencyaware.producer;

import fr.unice.scale.latencyaware.common.entity.Customer;
import fr.unice.scale.latencyaware.producer.config.KafkaProducerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import static fr.unice.scale.latencyaware.producer.constant.Variables.WORKLOAD;


public class KafkaProducerExample {
    private static final Logger log = LogManager.getLogger(KafkaProducerExample.class);

    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
        KafkaProducerConfig config = KafkaProducerConfig.fromEnv();
        log.info(KafkaProducerConfig.class.getName() + ": {}", config.toString());
        Properties props = KafkaProducerConfig.createProperties(config);
        KafkaProducer<String, Customer> producer = new KafkaProducer<>(props);
        startServer();

        WORKLOAD.get().getWorkload().startWorkload(config, producer);
    }

    private static void startServer() {
        Thread server = new Thread(new ServerThread());
        server.start();

    }
}