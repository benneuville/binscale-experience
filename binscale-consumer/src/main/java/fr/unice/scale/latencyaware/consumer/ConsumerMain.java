package fr.unice.scale.latencyaware.consumer;
import fr.unice.scale.latencyaware.common.entity.Customer;
import fr.unice.scale.latencyaware.consumer.config.VariableKafkaConsumerConfig;
import fr.unice.scale.latencyaware.consumer.metrics.PrometheusUtils;
import org.apache.commons.math3.distribution.ParetoDistribution;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.time.Instant;

import static fr.unice.scale.latencyaware.consumer.constant.Variables.*;

public class ConsumerMain {
    private static final Logger log = LogManager.getLogger(ConsumerMain.class);
    public static KafkaConsumer<String, Customer> consumer = null;
    public static double eventsViolating = 0;
    public static double eventsNonViolating = 0;
    public static double totalEvents = 0;

    public static KafkaProducer<String, Customer> producer;
    public static ParetoDistribution dist = new ParetoDistribution(SCALE, SHAPE);


    public static void main(String[] args) {
        PrometheusUtils.initPrometheus();
        VariableKafkaConsumerConfig config = VariableKafkaConsumerConfig.fromEnv();
        log.info(VariableKafkaConsumerConfig.class.getName() + ": {}", config.toString());
        Properties props = VariableKafkaConsumerConfig.createProperties(config);

        props.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, StickyAssignor.class.getName());

        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(config.getTopic()));
        log.info("Subscribed to topic {}", config.getTopic());

        addShutDownHook();


        double max = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy'T'HH:mm:ss.SSS");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));  // Définir le fuseau horaire sur UTC

        Logger logger = LogManager.getLogger(ConsumerMain.class);

        Instant lastCommitTime = Instant.now();
        logger.info("Async commit is {}", ASYNC_COMMIT);
        try {
            while (true) {
                //Check for commit even if no records are received
                //just in case we have commited while processing the last batch
                lastCommitTime = checkForCommit(simpleDateFormat, logger, lastCommitTime);  

                ConsumerRecords<String, Customer> records = consumer.poll(Duration.ofMillis(TIME_TO_COMMIT.longValue()));
                if (records.count() != 0) {
                    for (ConsumerRecord<String, Customer> record : records) {
                        totalEvents++;
                        try {
                            double sleep = dist.sample();
                            if (max < sleep) {
                                max = sleep;
                            }

                            log.info("sleep is {}", sleep);
                            log.info("long sleep  {}", (long) sleep);

                            Thread.sleep((long) sleep);

                            PrometheusUtils.processingTime.setDuration(sleep);
                            PrometheusUtils.totalLatencyTime.setDuration(System.currentTimeMillis() - record.timestamp());

                            if (System.currentTimeMillis() - record.timestamp() <= WSLA_S) {
                                eventsNonViolating++;
                            } else {
                                eventsViolating++;
                            }

                            long currentTimeMillis = System.currentTimeMillis();
                            Date currentDate = new Date(currentTimeMillis);

                            Timestamp timestamp = new Timestamp(record.timestamp());
                            Date insertionDate = new Date(timestamp.getTime());

                            logger.info("latency is {}, insertion time is {}, processing time is {}",
                                    currentTimeMillis - record.timestamp(), simpleDateFormat.format(insertionDate), simpleDateFormat.format(currentDate));


                            lastCommitTime = checkForCommit(simpleDateFormat, logger, lastCommitTime);                      

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }


                PrometheusUtils.processingTime.setDuration(max);
                max = 0;
                log.info("In this poll, received {} events", records.count());
            }
        } catch (WakeupException e) {
            // Handle exception
        } finally {
            consumer.close();
            log.info("Closed consumer and we are done");
        }
    }

    private static Instant checkForCommit(SimpleDateFormat simpleDateFormat, Logger logger, Instant lastCommitTime) {
        if (Math.abs(Duration.between(lastCommitTime, Instant.now()).toMillis()) >= TIME_TO_COMMIT) {
            if (ASYNC_COMMIT) {
                consumer.commitAsync();
            } else {
               consumer.commitSync();
            }
            logger.info("Committed offset at time {}", simpleDateFormat.format(new Date(System.currentTimeMillis())));
            lastCommitTime = Instant.now();
        }
        return lastCommitTime;
    }

    private static void addShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                log.info("Starting exit...");
                consumer.wakeup();
                try {
                    this.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
