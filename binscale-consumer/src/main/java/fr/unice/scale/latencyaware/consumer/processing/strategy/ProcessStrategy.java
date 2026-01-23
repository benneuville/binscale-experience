package fr.unice.scale.latencyaware.consumer.processing.strategy;

import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import fr.unice.scale.latencyaware.consumer.entity.DistributedEventCustomer;
import fr.unice.scale.latencyaware.consumer.entity.DistributionConfig;
import fr.unice.scale.latencyaware.consumer.metrics.MetricsCollector;
import org.apache.commons.math3.distribution.ParetoDistribution;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;

public abstract class ProcessStrategy {
    private final ParetoDistribution paretoDistribution;

    private final Logger logger = LoggerFactory.getLogger(ProcessStrategy.class);

    public ProcessStrategy(Double scale, Double shape) {
        this.paretoDistribution = new ParetoDistribution(scale, shape);
    }

    public List<DistributedEventCustomer> process(DistributionConfig config, ConsumerRecords<String, EventCustomer> events) {
        List<DistributedEventCustomer> res = process(config, events, this::processEvent);
        this.logger.info(res.toString());
        return res;
    }

    public abstract List<DistributedEventCustomer> process(DistributionConfig config, ConsumerRecords<String, EventCustomer> events, Consumer<ConsumerRecord<String, EventCustomer>> eventProcessor);

    private void processEvent(ConsumerRecord<String, EventCustomer> record) {
        double sleep = paretoDistribution.sample();
        try {
            Thread.sleep((long) sleep);
            MetricsCollector.getInstance().collect(record, sleep);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
