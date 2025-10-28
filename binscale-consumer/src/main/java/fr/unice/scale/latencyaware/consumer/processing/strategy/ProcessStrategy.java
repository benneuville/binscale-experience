package fr.unice.scale.latencyaware.consumer.processing.strategy;

import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import fr.unice.scale.latencyaware.consumer.entity.DistributedEventCustomer;
import fr.unice.scale.latencyaware.consumer.entity.DistributionConfig;
import fr.unice.scale.latencyaware.consumer.metrics.MetricsCollector;
import org.apache.commons.math3.distribution.ParetoDistribution;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.List;
import java.util.function.Consumer;

import static fr.unice.scale.latencyaware.consumer.constant.Variables.SCALE;
import static fr.unice.scale.latencyaware.consumer.constant.Variables.SHAPE;

public abstract class ProcessStrategy {
    private final ParetoDistribution paretoDistribution = new ParetoDistribution(SCALE, SHAPE);

    public List<DistributedEventCustomer> process(DistributionConfig config, ConsumerRecords<String, EventCustomer> events) {
        return process(config, events, this::processEvent);
    }

    public abstract List<DistributedEventCustomer> process(DistributionConfig config, ConsumerRecords<String, EventCustomer> events, Consumer<ConsumerRecord<String, EventCustomer>> eventProcessor);

    private void processEvent(ConsumerRecord<String, EventCustomer> record) {
        double sleep = paretoDistribution.sample();
        MetricsCollector.getInstance().collect(record, sleep);
        try {
            Thread.sleep((long) sleep);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
