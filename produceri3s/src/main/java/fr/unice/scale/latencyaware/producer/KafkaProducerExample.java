package fr.unice.scale.latencyaware.producer;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.Random;

import fr.unice.scale.latencyaware.common.entity.Customer;
import fr.unice.scale.latencyaware.producer.config.KafkaProducerConfig;
import fr.unice.scale.latencyaware.producer.constant.WorkloadMapping;
import fr.unice.scale.latencyaware.producer.workload.BiasedWorkload;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static fr.unice.scale.latencyaware.producer.constant.Variables.INPUT_WORKLOAD;


public class KafkaProducerExample {
    private static final Logger log = LogManager.getLogger(KafkaProducerExample.class);

    public static KafkaProducerConfig config;
    public static KafkaProducer<String, Customer> producer;
    public static WorkloadMapping workloadMapping;

    //random number gen
    static Random rnd;


    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
        rnd = new Random();
        config = KafkaProducerConfig.fromEnv();
        log.info(KafkaProducerConfig.class.getName() + ": {}", config.toString());
        Properties props = KafkaProducerConfig.createProperties(config);
        producer = new KafkaProducer<>(props);
        startServer();

        workloadMapping = WorkloadMapping.getByName(INPUT_WORKLOAD);

        workloadMapping.getWorkload().startWorkload();
    }

    private static void startServer() {
        Thread server = new Thread(new ServerThread());
        server.start();
        
    }
}