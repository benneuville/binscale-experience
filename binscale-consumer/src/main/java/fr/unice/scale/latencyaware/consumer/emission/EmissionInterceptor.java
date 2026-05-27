package fr.unice.scale.latencyaware.consumer.emission;


import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import fr.unice.scale.latencyaware.common.utils.MetricUtils;
import fr.unice.scale.latencyaware.consumer.metrics.PrometheusUtils;
import io.micrometer.core.instrument.DistributionSummary;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.HashMap;
import java.util.Map;

import static fr.unice.scale.latencyaware.common.utils.MetricUtils.MetricVariables;
import static fr.unice.scale.latencyaware.consumer.constant.Variables.GROUP_ID;
import static fr.unice.scale.latencyaware.consumer.constant.Variables.TOPIC;

public class EmissionInterceptor implements
        ProducerInterceptor<String, EventCustomer> {

    public static Map<String, DistributionSummary> topicToDist = new HashMap<>();

    @Override
    public ProducerRecord<String, EventCustomer> onSend
            (ProducerRecord<String, EventCustomer> producerRecord) {
        String topicto = producerRecord.topic();

        DistributionSummary dist = topicToDist.get(topicto);

        if (dist == null) {
            dist = DistributionSummary.builder(MetricUtils.publishedEventMetric(TOPIC))
                    .tag(MetricVariables.TAG_TOPIC, TOPIC)
                    .tag(MetricVariables.TAG_GROUP_ID, GROUP_ID)
                    .register(PrometheusUtils.prometheusRegistry);
            topicToDist.put(topicto, dist);
        }
        dist.record(1.0);

        return producerRecord;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception e) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}