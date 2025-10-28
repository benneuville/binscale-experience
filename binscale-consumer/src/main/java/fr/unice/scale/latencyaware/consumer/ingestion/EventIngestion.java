package fr.unice.scale.latencyaware.consumer.ingestion;

import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import fr.unice.scale.latencyaware.consumer.config.BinscaleConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static fr.unice.scale.latencyaware.common.constant.CommonVariables.DATE_FORMAT;
import static fr.unice.scale.latencyaware.consumer.constant.Variables.ASYNC_COMMIT;
import static fr.unice.scale.latencyaware.consumer.constant.Variables.TIME_TO_COMMIT;

public class EventIngestion extends KafkaConsumer<String, EventCustomer> {
    private Instant lastCommitTime;

    private final Logger logger = LogManager.getLogger(EventIngestion.class);

    public EventIngestion(BinscaleConsumerConfig config) {
        this(config.toProperties(), Collections.singletonList(config.getTopic()));
    }
    protected EventIngestion(Properties properties, List<String> topics) {
        super(properties);
        subscribe(topics);
        lastCommitTime = Instant.now();
    }

    public void commit() {
        if (Math.abs(Duration.between(lastCommitTime, Instant.now()).toMillis()) >= TIME_TO_COMMIT) {
            if (ASYNC_COMMIT) {
                this.commitAsync();
            } else {
                this.commitSync();
            }
            logger.info("Committed offset at time {}", DATE_FORMAT.format(new Date(System.currentTimeMillis())));
            lastCommitTime = Instant.now();
        }
    }
}
