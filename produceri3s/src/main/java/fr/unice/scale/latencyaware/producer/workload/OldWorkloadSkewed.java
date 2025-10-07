package fr.unice.scale.latencyaware.producer.workload;
import fr.unice.scale.latencyaware.common.entity.Customer;
import fr.unice.scale.latencyaware.producer.config.KafkaProducerConfig;
import fr.unice.scale.latencyaware.producer.entity.Workload;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.UUID;

@Deprecated
public class OldWorkloadSkewed extends AbstractWorkload {
    final Logger log = LogManager.getLogger(OldWorkloadSkewed.class);
    @Override
    public void startWorkload(KafkaProducerConfig config, KafkaProducer<String, Customer> producer) throws IOException, URISyntaxException, InterruptedException {
        Workload wrld = new Workload();
        double first3Partitions = 0.0;
        double remaining6Partitions = 0.0;

        Random rnd = new Random();
        // over all the workload
        for (int i = 0; i < wrld.getDatax().size(); i++) {
            log.info("sending a batch of authorizations of size:{}",
                    Math.ceil(wrld.getDatay().get(i)));
            ArrivalRate = (float) Math.ceil(wrld.getDatay().get(i));

            first3Partitions = ArrivalRate * 0.5;
            remaining6Partitions = ArrivalRate * 0.5;

            //   sendToFirst 3 partitions
            for (long j = 0; j < first3Partitions/2.0; j++) {
                Customer custm = new Customer(rnd.nextInt(), UUID.randomUUID().toString());
                       producer.send(new ProducerRecord<>(config.getTopic(), 
                               0, null, UUID.randomUUID().toString(), custm));
                       
                       producer.send(new ProducerRecord<>(config.getTopic(), 
                               1, null, UUID.randomUUID().toString(), custm));
            }

            //   sendTo remaining partitions
            for (long j = 0; j <remaining6Partitions /7.0; j++) {

                Customer custm = new Customer(rnd.nextInt(), UUID.randomUUID().toString());
                
                        producer.send(new ProducerRecord<>(config.getTopic(),
                                2, null, UUID.randomUUID().toString(), custm));

                        producer.send(new ProducerRecord<>(config.getTopic(),
                                3, null, UUID.randomUUID().toString(), custm));
                
                        producer.send(new ProducerRecord<>(config.getTopic(),
                                4, null, UUID.randomUUID().toString(), custm));
                
                        producer.send(new ProducerRecord<>(config.getTopic(),
                                5, null, UUID.randomUUID().toString(), custm));

                        producer.send(new ProducerRecord<>(config.getTopic(),
                                6, null, UUID.randomUUID().toString(), custm));
                
                        producer.send(new ProducerRecord<>(config.getTopic(),
                                7, null, UUID.randomUUID().toString(), custm));

                        producer.send(new ProducerRecord<>(config.getTopic(),
                                8, null, UUID.randomUUID().toString(), custm));

            }
            log.info("sent {} events Per Second ", Math.ceil(wrld.getDatay().get(i)));
            Thread.sleep(config.getDelay());
        }
    }

}
