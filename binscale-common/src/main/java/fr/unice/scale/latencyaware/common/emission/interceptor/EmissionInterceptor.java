package fr.unice.scale.latencyaware.common.emission.interceptor;


import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import fr.unice.scale.latencyaware.common.prometheus.PrometheusUtils;
import fr.unice.scale.latencyaware.common.utils.MetricUtils;
import io.micrometer.core.instrument.DistributionSummary;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static fr.unice.scale.latencyaware.common.constant.CommonVariables.EXTERNAL_GROUP_NAME;
import static fr.unice.scale.latencyaware.common.utils.MetricUtils.MetricVariables;

public class EmissionInterceptor implements
        ProducerInterceptor<String, EventCustomer> {
    public static Map<String, DistributionSummary> topicToDist = new HashMap<>();
    public Logger log = LoggerFactory.getLogger(EmissionInterceptor.class);
    private String topic;

    private String groupId;

    public EmissionInterceptor() {
    }

    public EmissionInterceptor(String topic) {
        this.topic = topic;
        this.groupId = EXTERNAL_GROUP_NAME;
    }

    public EmissionInterceptor(String topic, String groupId) {
        this.topic = topic;
        this.groupId = groupId;
    }

    @Override
    public ProducerRecord<String, EventCustomer> onSend
            (ProducerRecord<String, EventCustomer> producerRecord) {
        String topicto = producerRecord.topic();
        DistributionSummary dist = topicToDist.get(topicto);

        if (dist == null) {
            dist = DistributionSummary.builder(MetricUtils.publishedEventMetric(topic))
                    .tag(MetricVariables.TAG_TOPIC, topic)
                    .tag(MetricVariables.TAG_GROUP_ID, groupId)
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
    public void configure(Map<String, ?> configs) {
        this.topic = (String) configs.get("emission.interceptor.topic");
        this.groupId = (String) configs.get("emission.interceptor.groupId");
        if (this.groupId == null) {
            this.groupId = EXTERNAL_GROUP_NAME;
        }
        log.info(groupId);
    }
}