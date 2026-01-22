package fr.unice.scale.latencyaware.controller.metric.prometheus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import fr.unice.scale.latencyaware.common.error.exception.MetricResultEmptyException;
import fr.unice.scale.latencyaware.common.utils.prometheus.SimpleQueryBuilder;
import fr.unice.scale.latencyaware.common.utils.prometheus.enums.DistributionSummarySuffix;
import fr.unice.scale.latencyaware.common.utils.prometheus.metrics.DistributionSummaryMetricQueryBuilder;
import fr.unice.scale.latencyaware.common.utils.prometheus.metrics.MetricBuilder;
import fr.unice.scale.latencyaware.common.utils.prometheus.metrics.RateMetricQueryBuilder;
import fr.unice.scale.latencyaware.common.utils.prometheus.metrics.SumMetricBuilder;
import fr.unice.scale.latencyaware.controller.entity.Consumer;
import fr.unice.scale.latencyaware.controller.entity.ConsumerGroup;
import fr.unice.scale.latencyaware.controller.entity.Partition;
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
import static fr.unice.scale.latencyaware.controller.constant.Variables.REB_TIME;
import static fr.unice.scale.latencyaware.controller.constant.Variables.getTimeRange;

public class PrometheusMetricCollector {

    private PrometheusClient clientMetricCollector;
    private ObjectWriter objectWriter = new ObjectMapper().writer();

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
    public Map<Integer, DoubleMetric> collectLagByPartition(ConsumerGroup consumerGroup) throws MetricResultEmptyException {
        SimpleQueryBuilder queryBuilder = SimpleQueryBuilder.builder()
                .query(
                        MetricBuilder.builder()
                                .name(KAFKA_CONSUMER_GROUP_LAG)
                                .addTag(TAG_KAFKA_CONSUMER_GROUP, consumerGroup.getKafkaGroupName())
                                .addTag(TAG_KAFKA_TOPIC, consumerGroup.getInputTopic())
                                .timeWindow(getTimeRange())
                                .build()
                );
        return clientMetricCollector.mappedResultQuery(queryBuilder.build(), TAG_KAFKA_PARTITION, Integer.class, DoubleMetric.class);
    }

    /**
     * Raw arrival rate metric collection per partition for a ConsumerGroup
     *
     * @return map (PartitionId, ArrivalRateValue)
     */
    public Map<Integer, DoubleMetric> collectArrivalRateByPartition(ConsumerGroup consumerGroup) throws MetricResultEmptyException {
        SimpleQueryBuilder queryBuilder = SimpleQueryBuilder.builder()
                .query(
                        RateMetricQueryBuilder.builder().metric(
                                MetricBuilder.builder()
                                        .name(KAFKA_TOPIC_PARTITION_CURRENT_OFFSET)
                                        .addTag(TAG_KAFKA_TOPIC, consumerGroup.getInputTopic())
                                        .timeWindow(getTimeRange())
                                        .build()
                        ).build()
                );
        return clientMetricCollector.mappedResultQuery(queryBuilder.build(), TAG_KAFKA_PARTITION, Integer.class, DoubleMetric.class);
    }

    public Map<Integer, DoubleMetric> latencyByPartition(ConsumerGroup consumerGroup) throws MetricResultEmptyException {
        SimpleQueryBuilder queryBuilder = SimpleQueryBuilder.builder()
                .query(
                        SumMetricBuilder.builder()
                                .metric(
                                        MetricBuilder.builder()
                                                .name(LATENCY_GAUGE)
                                                .addTag(TAG_KAFKA_TOPIC, consumerGroup.getInputTopic()))
                                .addByTag(TAG_KAFKA_PARTITION)
                                .build()
                );
        return clientMetricCollector.mappedResultQuery(queryBuilder.build(), TAG_KAFKA_PARTITION, Integer.class, DoubleMetric.class);
    }

    public Map<Integer, DoubleMetric> processingTimeByPartition(ConsumerGroup consumerGroup) throws MetricResultEmptyException {
        SimpleQueryBuilder queryBuilder = SimpleQueryBuilder.builder()
                .query(
                        RateMetricQueryBuilder.builder()
                                .metric(MetricBuilder.builder()
                                        .name(DistributionSummaryMetricQueryBuilder.builder()
                                                .metric(EVENTS_PROCESSING_TIME)
                                                .suffix(DistributionSummarySuffix.SUM).build())
                                        .addTag(TAG_KAFKA_TOPIC, consumerGroup.getInputTopic())
                                        .timeWindow(getTimeRange())
                                ).build()
                );
        return clientMetricCollector.mappedResultQuery(queryBuilder.build(), TAG_KAFKA_PARTITION, Integer.class, DoubleMetric.class);
    }

    public Map<Integer, DoubleMetric> processingCountByPartition(ConsumerGroup consumerGroup) throws MetricResultEmptyException {
        SimpleQueryBuilder queryBuilder = SimpleQueryBuilder.builder()
                .query(
                        RateMetricQueryBuilder.builder()
                                .metric(MetricBuilder.builder()
                                        .name(DistributionSummaryMetricQueryBuilder.builder()
                                                .metric(EVENTS_PROCESSING_TIME)
                                                .suffix(DistributionSummarySuffix.COUNT).build())
                                        .addTag(TAG_KAFKA_TOPIC, consumerGroup.getInputTopic())
                                        .timeWindow(getTimeRange())
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
        try {
            Map<ConsumerGroup, CGMetaData> consumerGroupMetaDatas = new HashMap<>();
            for (ConsumerGroup cg : graph.topologicalSort().stream().map(Vertex::getGroup).collect(Collectors.toList())) {
                CGMetaData metaData = new CGMetaData(cg, REB_TIME);

                Map<Integer, DoubleMetric> lagByPartition = collectLagByPartition(cg);

                for (Integer partitionId : lagByPartition.keySet()) {
                    metaData.getPartitionMetaData(partitionId).setLag(lagByPartition.get(partitionId).getValue().longValue());
                }
                Map<Integer, DoubleMetric> arrivalRateByPartition = collectArrivalRateByPartition(cg);
                for (Integer partitionId : arrivalRateByPartition.keySet()) {
                    metaData.getPartitionMetaData(partitionId).setArrivalRate(arrivalRateByPartition.get(partitionId).getValue());
                }

                Map<Integer, DoubleMetric> latency = latencyByPartition(cg);
                for (Integer partitionId : latency.keySet()) {
                    metaData.getPartitionMetaData(partitionId).setLatency(latency.get(partitionId).getValue());
                }

                Map<Integer, DoubleMetric> processingTime = processingTimeByPartition(cg);

                for (Integer partitionId : processingTime.keySet()) {
                    metaData.getPartitionMetaData(partitionId).setProcessingTime(processingTime.get(partitionId).getValue());
                }

                Map<Integer, DoubleMetric> processingCount = processingCountByPartition(cg);

                for (Integer partitionId : processingCount.keySet()) {
                    metaData.getPartitionMetaData(partitionId).setProcessingCount(processingCount.get(partitionId).getValue().longValue());
                }

                for (Consumer consumer : metaData.getConsumersMetaData().keySet()) {
                    double nbOfEventPolled = 0;
                    double processTimeOfEventPolled = .0;
                    for (Partition p : consumer.getAssignedPartitions()) {
                        nbOfEventPolled += metaData.getPartitionMetaData(p).getProcessingCount();
                        processTimeOfEventPolled += metaData.getPartitionMetaData(p).getProcessingTime();
                    }
                }
                consumerGroupMetaDatas.put(cg, metaData);
            }

            log.info("Pulled data from Prometheus : {}", objectWriter.writeValueAsString(consumerGroupMetaDatas.values()));
//        return new HashMap<>();
            return consumerGroupMetaDatas;
        } catch (MetricResultEmptyException e) {
            log.warn("MetricResultEmptyException occurred during metrics collection: {}", e.getMessage());
            return new HashMap<>();
        } catch (JsonProcessingException e) {
            log.warn("JsonProcessingException occurred during logging: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
