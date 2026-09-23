package fr.unice.scale.latencyaware.exporter.processor;

import fr.unice.scale.latencyaware.exporter.entity.KafkaHeaderConfig;
import fr.unice.scale.latencyaware.exporter.extractor.HeaderExtractor;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.ImmutableTag;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import org.apache.kafka.streams.processor.api.ContextualProcessor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.Record;

import java.util.Map;
import java.util.stream.Collectors;

import static fr.unice.scale.latencyaware.common.constant.CommonVariables.MESSAGE_COUNTER_NAME;
import static fr.unice.scale.latencyaware.common.utils.MetricUtils.MetricVariables.TAG_KAFKA_TOPIC;


public class HeaderProcessingProcessor extends ContextualProcessor<String, byte[], String, byte[]> {
    private final KafkaHeaderConfig config;
    private final PrometheusMeterRegistry registry;
    private final String topic;
    private ProcessorContext<String, byte[]> context;

    public HeaderProcessingProcessor(KafkaHeaderConfig config, PrometheusMeterRegistry registry, String topic) {
        this.config = config;
        this.registry = registry;
        this.topic = topic;
    }

    @Override
    public void init(ProcessorContext<String, byte[]> context) {
        this.context = context;
    }

    @Override
    public void process(Record<String, byte[]> record) {
        Map<String, String> extractedHeaders = HeaderExtractor.extractHeaders(record, config);
        extractedHeaders.put(TAG_KAFKA_TOPIC, topic);

        Counter counter = Counter.builder(MESSAGE_COUNTER_NAME)
                .tags(extractedHeaders.entrySet().stream()
                        .map(e -> new ImmutableTag(e.getKey(), e.getValue()))
                        .collect(Collectors.toList()))
                .register(registry);

        counter.increment();
        System.out.println(">" + topic);
        context.forward(record);
    }
}
