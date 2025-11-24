package fr.unice.scale.latencyaware.controller.metric.prometheus;

import fr.unice.scale.latencyaware.common.utils.prometheus.SimpleQueryBuilder;
import fr.unice.scale.latencyaware.common.utils.prometheus.metrics.MetricBuilder;
import fr.unice.scale.latencyaware.common.utils.prometheus.metrics.RateMetricQueryBuilder;
import fr.unice.scale.latencyaware.controller.entity.ConsumerGroup;
import fr.unice.scale.latencyaware.controller.entity.graph.Graph;
import fr.unice.scale.latencyaware.controller.entity.graph.Vertex;
import fr.unice.scale.latencyaware.controller.entity.meta_data.CGMetaData;
import fr.unice.scale.latencyaware.controller.entity.metric.DoubleMetric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static fr.unice.scale.latencyaware.common.utils.MetricUtils.MetricVariables.*;
import static fr.unice.scale.latencyaware.controller.constant.Variables.REQUEST_TIME_RANGE;

public class PrometheusMetricCollector {

    private PrometheusClient clientMetricCollector;

    private Logger log = LoggerFactory.getLogger(PrometheusMetricCollector.class);

    public PrometheusMetricCollector(PrometheusClient clientMetricCollector) {
        this.clientMetricCollector = clientMetricCollector;
    }

    public PrometheusMetricCollector() {
        this.clientMetricCollector = new PrometheusClient();
    }

    /**
     * Raw lag metric collection per partition for a ConsumerGroup
     *
     * @return map (PartitionId, LagValue)
     */
    public Map<Integer, DoubleMetric> collectLagByPartition(ConsumerGroup consumerGroup) {
        SimpleQueryBuilder queryBuilder = SimpleQueryBuilder.builder()
                .query(
                        MetricBuilder.builder()
                                .name(KAFKA_CONSUMER_GROUP_LAG)
                                .addTag(TAG_KAFKA_CONSUMER_GROUP, consumerGroup.getKafkaGroupName())
                                .addTag(TAG_KAFKA_TOPIC, consumerGroup.getInputTopic())
                                .timeWindow(REQUEST_TIME_RANGE)
                                .build()
                );
        return clientMetricCollector.mappedResultQuery(queryBuilder.build(), TAG_KAFKA_PARTITION, Integer.class, DoubleMetric.class);
    }

    /**
     * Raw arrival rate metric collection per partition for a ConsumerGroup
     *
     * @return map (PartitionId, ArrivalRateValue)
     */
    public Map<Integer, DoubleMetric> collectArrivalRateByPartition(ConsumerGroup consumerGroup) {
        SimpleQueryBuilder queryBuilder = SimpleQueryBuilder.builder()
                .query(
                        RateMetricQueryBuilder.builder().metric(
                                MetricBuilder.builder()
                                        .name(KAFKA_TOPIC_PARTITION_CURRENT_OFFSET)
                                        .addTag(TAG_KAFKA_TOPIC, consumerGroup.getInputTopic())
                                        .timeWindow(REQUEST_TIME_RANGE)
                        ).build()
                );
        return clientMetricCollector.mappedResultQuery(queryBuilder.build(), TAG_KAFKA_PARTITION, Integer.class, DoubleMetric.class);
    }

    /**
     * For each ConsumerGroup, collects all the partitions(lag, arrival rate) data
     *
     * @param graph of ConsumerGroup
     * @return map (ConsumerGroup, CGMetaData)
     */
    public Map<ConsumerGroup, CGMetaData> collectRawMetaData(Graph<ConsumerGroup> graph) {
        Map<ConsumerGroup, CGMetaData> consumerGroupMetaDatas = new HashMap<>();
        for (ConsumerGroup cg : graph.topologicalSort().stream().map(Vertex::getGroup).collect(Collectors.toList())) {
            CGMetaData metaData = new CGMetaData(cg);

            Map<Integer, DoubleMetric> lagByPartition = collectLagByPartition(cg);

            for (Integer partitionId : lagByPartition.keySet()) {
                metaData.getPartitionMetaData(partitionId).setLag(lagByPartition.get(partitionId).getValue().longValue());
            }
            Map<Integer, DoubleMetric> arrivalRateByPartition = collectArrivalRateByPartition(cg);
            for (Integer partitionId : arrivalRateByPartition.keySet()) {
                metaData.getPartitionMetaData(partitionId).setArrivalRate(arrivalRateByPartition.get(partitionId).getValue());
            }
            consumerGroupMetaDatas.put(cg, metaData);
        }

        log.info(consumerGroupMetaDatas.toString());
        return consumerGroupMetaDatas;
    }

}
