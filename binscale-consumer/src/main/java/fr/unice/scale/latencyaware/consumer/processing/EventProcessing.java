package fr.unice.scale.latencyaware.consumer.processing;

import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import fr.unice.scale.latencyaware.consumer.entity.DistributedEventCustomer;
import fr.unice.scale.latencyaware.consumer.entity.DistributionConfig;
import fr.unice.scale.latencyaware.consumer.processing.strategy.ProcessStrategy;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EventProcessing {
    private final Logger log = LoggerFactory.getLogger(EventProcessing.class);
    private final ProcessStrategy processStrategy;
    private final DistributionConfig config;

    public EventProcessing(ProcessStrategy processStrategy, DistributionConfig config) {
        this.processStrategy = processStrategy;
        this.config = config;
    }

    public List<DistributedEventCustomer> distribute(ConsumerRecords<String, EventCustomer> events) {
        log.info("Processing {} events using strategy {}",
                events.count(),
                processStrategy.getClass().getSimpleName());
        return processStrategy.process(config, events);
    }
}
