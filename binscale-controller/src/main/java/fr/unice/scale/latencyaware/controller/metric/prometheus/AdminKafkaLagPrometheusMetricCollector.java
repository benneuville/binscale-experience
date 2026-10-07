package fr.unice.scale.latencyaware.controller.metric.prometheus;

import fr.unice.scale.latencyaware.common.error.exception.MetricResultEmptyException;
import fr.unice.scale.latencyaware.controller.admin.AdminComponent;
import fr.unice.scale.latencyaware.controller.entity.ConsumerGroup;
import fr.unice.scale.latencyaware.controller.entity.metric.DoubleMetric;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class AdminKafkaLagPrometheusMetricCollector extends PrometheusMetricCollector {
    private AdminComponent adminComponent;

    public AdminKafkaLagPrometheusMetricCollector(AdminComponent adminComponent) {
        super();
        this.adminComponent = adminComponent;
    }

    @Override
    public Map<Integer, DoubleMetric> collectLagByPartition(ConsumerGroup consumerGroup) throws MetricResultEmptyException {
        Map<Integer, DoubleMetric> partitionLags = adminComponent.collectLagByPartition(consumerGroup);
        if (partitionLags.isEmpty()) {
            return partitionLags;
        }

        double meanLag = partitionLags.values().stream()
                .mapToDouble(DoubleMetric::getValue)
                .average()
                .orElseThrow();
        long timestamp = Instant.now().toEpochMilli();
        Map<Integer, DoubleMetric> meanLagByPartition = new HashMap<>();

        for (Integer partitionId : partitionLags.keySet()) {
            meanLagByPartition.put(partitionId, new DoubleMetric(timestamp, String.valueOf(meanLag)));
        }

        return meanLagByPartition;
    }

}
