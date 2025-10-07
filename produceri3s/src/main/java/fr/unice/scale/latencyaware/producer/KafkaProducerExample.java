package fr.unice.scale.latencyaware.producer;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.Random;

import fr.unice.scale.latencyaware.common.entity.Customer;
import fr.unice.scale.latencyaware.producer.config.KafkaProducerConfig;
import fr.unice.scale.latencyaware.producer.workload.BiasedWorkload;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class KafkaProducerExample {
    private static final Logger log = LogManager.getLogger(KafkaProducerExample.class);

    public static KafkaProducerConfig config;
    public static KafkaProducer<String, Customer> producer;
    //générateur de nombres aléatoires
    static Random rnd;


    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
        rnd = new Random();
        config = KafkaProducerConfig.fromEnv();
        log.info(KafkaProducerConfig.class.getName() + ": {}", config.toString());
        Properties props = KafkaProducerConfig.createProperties(config);
        producer = new KafkaProducer<String, Customer>(props);
        startServer();

        BiasedWorkload.startWorkload();
        // NonUniformWorkload.startWorkload();
        //ConstantWorkload.startWorkload();
        //OldWorkload.startWorkloadUniform();

    }

    private static void startServer() {
        Thread server = new Thread(new ServerThread());
        server.start();
        
    }
}