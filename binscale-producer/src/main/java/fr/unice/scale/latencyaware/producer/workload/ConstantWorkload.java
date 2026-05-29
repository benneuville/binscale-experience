package fr.unice.scale.latencyaware.producer.workload;

import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import fr.unice.scale.latencyaware.producer.config.BinscaleKafkaProducerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;

import static fr.unice.scale.latencyaware.common.constant.CommonVariables.HEADER_EVENT_ID;
import static fr.unice.scale.latencyaware.producer.constant.Variables.PRODUCER_ID;

public class ConstantWorkload extends AbstractWorkload {
    static Instant start = Instant.now();
    final Logger log = LogManager.getLogger(ConstantWorkload.class);

    @Override
    public void startWorkload(BinscaleKafkaProducerConfig config, KafkaProducer<String, EventCustomer> producer) throws InterruptedException {

        Random rnd = new Random();
        //During 10 minutes
        while (Duration.between(start, Instant.now()).getSeconds() < 60 * 10) {

            log.debug("Current time: {}", Instant.now());
            log.debug("Start time: {}", start);
            log.debug("Elapsed time (seconds): {}", Duration.between(start, Instant.now()).getSeconds());


            //   loop over each sample
            for (long j = 0; j < 150; j++) {
                String id = PRODUCER_ID + "-" + UUID.randomUUID();
                EventCustomer custm = new EventCustomer(rnd.nextInt(), id);
                ProducerRecord<String, EventCustomer> ev = new ProducerRecord<>(config.getTopic(),
                        null, null, id, custm);
                producer.send(ev);
            }

            log.info("sent {} events Per Second ", 150);
            ArrivalRate = 150;
            Thread.sleep(config.getDelay());
        }

        ArrivalRate = 0;
    }
}
