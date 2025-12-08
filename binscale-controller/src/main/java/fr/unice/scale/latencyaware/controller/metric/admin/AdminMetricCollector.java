package fr.unice.scale.latencyaware.controller.metric.admin;

import fr.unice.scale.latencyaware.controller.entity.ConsumerGroup;
import fr.unice.scale.latencyaware.controller.entity.metric.DoubleMetric;
import fr.unice.scale.latencyaware.controller.metric.prometheus.PrometheusMetricCollector;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListOffsetsResult;
import org.apache.kafka.clients.admin.OffsetSpec;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static fr.unice.scale.latencyaware.common.constant.CommonVariables.DATE_FORMAT;
import static fr.unice.scale.latencyaware.controller.constant.Variables.BOOTSTRAP_SERVERS;

public class AdminMetricCollector extends PrometheusMetricCollector {

    private static Logger log = LogManager.getLogger(AdminMetricCollector.class);

    public AdminClient admin;

    public AdminMetricCollector() {
        super();
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        this.admin = AdminClient.create(props);
    }

    @Override
    public Map<Integer, DoubleMetric> collectLagByPartition(ConsumerGroup consumerGroup) {
        double totalLag = 0.0;
        Map<Integer, DoubleMetric> lagMetrics = new HashMap<>();
        try {

            Map<TopicPartition, OffsetAndMetadata> committedOffset = admin.listConsumerGroupOffsets(consumerGroup.getGroupName())
                    .partitionsToOffsetAndMetadata().get();
            Map<TopicPartition, OffsetSpec> requestLatestOffsets = new HashMap<>();
            for (int i = 0; i < consumerGroup.getTopicPartitions().size(); i++) {
                requestLatestOffsets.put(new TopicPartition(consumerGroup.getInputTopic(), i), OffsetSpec.latest());
            }
            Map<TopicPartition, ListOffsetsResult.ListOffsetsResultInfo> latestOffsets =
                    admin.listOffsets(requestLatestOffsets).all().get();

            for (int i = 0; i < consumerGroup.getTopicPartitions().size(); i++) {
                TopicPartition t = new TopicPartition(consumerGroup.getInputTopic(), i);
                Double latestOffset = latestOffsets.get(t) != null ? (double) latestOffsets.get(t).offset() : 0.0;
                Double committed = committedOffset.get(t) != null ? (double) committedOffset.get(t).offset() : 0.0;
                lagMetrics.put(i, new DoubleMetric(Instant.now().toEpochMilli(), String.valueOf(latestOffset - committed)));
                log.info("partition {} has lag {}", i, latestOffset - committed);
                totalLag += (latestOffset - committed);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String currentTime = DATE_FORMAT.format(new Date(System.currentTimeMillis()));
        log.info("total lag {} at {}", totalLag, currentTime);
        return lagMetrics;
    }

}
