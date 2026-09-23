package fr.unice.scale.latencyaware.exporter.processor;

import fr.unice.scale.latencyaware.exporter.entity.KafkaHeaderConfig;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorSupplier;

public class CounterProcessorSupplier implements ProcessorSupplier<String, byte[], String, byte[]> {
    private final KafkaHeaderConfig config;
    private final PrometheusMeterRegistry registry;
    private final String topic;

    public CounterProcessorSupplier(KafkaHeaderConfig config, PrometheusMeterRegistry registry, String topic) {
        this.config = config;
        this.registry = registry;
        this.topic = topic;
    }

    @Override
    public Processor<String, byte[], String, byte[]> get() {
        return new HeaderProcessingProcessor(config, registry, topic);
    }
}
