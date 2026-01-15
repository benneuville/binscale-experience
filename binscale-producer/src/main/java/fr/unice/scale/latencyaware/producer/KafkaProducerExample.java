package fr.unice.scale.latencyaware.producer;

import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import fr.unice.scale.latencyaware.producer.config.BinscaleKafkaProducerConfig;
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
        BinscaleKafkaProducerConfig config = BinscaleKafkaProducerConfig.fromEnv();
        log.info(BinscaleKafkaProducerConfig.class.getName() + ": {}", config.toString());
        Properties props = BinscaleKafkaProducerConfig.createProperties(config);
        KafkaProducer<String, EventCustomer> producer = new KafkaProducer<>(props);
        startServer();

        WORKLOAD.getWorkload().startWorkload(config, producer);
        producer.close();
        log.info("Workload completed. Exiting...");
        System.exit(0);
    }

    private static void startServer() {
        Thread server = new Thread(new ServerThread());
        server.start();

    }
}