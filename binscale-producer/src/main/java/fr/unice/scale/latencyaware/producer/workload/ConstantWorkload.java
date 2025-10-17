package fr.unice.scale.latencyaware.producer.workload;

import fr.unice.scale.latencyaware.common.entity.Customer;
import fr.unice.scale.latencyaware.producer.config.KafkaProducerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;

public class ConstantWorkload extends AbstractWorkload {
    static Instant start = Instant.now();
    final Logger log = LogManager.getLogger(ConstantWorkload.class);

    @Override
    public void startWorkload(KafkaProducerConfig config, KafkaProducer<String, Customer> producer) throws InterruptedException {

        Random rnd = new Random();
        //During 10 minutes
        while (Duration.between(start, Instant.now()).getSeconds() < 60 * 10) {

            log.debug("Current time: {}", Instant.now());
            log.debug("Start time: {}", start);
            log.debug("Elapsed time (seconds): {}", Duration.between(start, Instant.now()).getSeconds());


            //   loop over each sample
            for (long j = 0; j < 150; j++) {
                Customer custm = new Customer(rnd.nextInt(), UUID.randomUUID().toString());
                producer.send(new ProducerRecord<String, Customer>(config.getTopic(),
                        null, null, UUID.randomUUID().toString(), custm));
            }

            log.info("sent {} events Per Second ", 150);
            ArrivalRate = 150;
            Thread.sleep(config.getDelay());
        }

        ArrivalRate = 0;
    }
}
