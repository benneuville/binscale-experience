package fr.unice.scale.latencyaware.controller.metric;

import fr.unice.scale.latencyaware.common.utils.prometheus.SimpleQueryBuilder;
import fr.unice.scale.latencyaware.common.utils.prometheus.enums.DistributionSummarySuffix;
import fr.unice.scale.latencyaware.common.utils.prometheus.metrics.DistributionSummaryMetricQueryBuilder;
import fr.unice.scale.latencyaware.common.utils.prometheus.metrics.MetricBuilder;
import fr.unice.scale.latencyaware.common.utils.prometheus.metrics.RateMetricQueryBuilder;
import fr.unice.scale.latencyaware.common.utils.prometheus.metrics.SumMetricBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junitpioneer.jupiter.SetEnvironmentVariable;
import org.mockito.junit.jupiter.MockitoExtension;

import static fr.unice.scale.latencyaware.common.utils.MetricUtils.MetricVariables.EVENTS_PROCESSING_TIME;
import static fr.unice.scale.latencyaware.common.utils.MetricUtils.MetricVariables.TAG_KAFKA_TOPIC;

@ExtendWith(MockitoExtension.class)
public class PrometheusMetricCollectorTest {

    @Test
    @SetEnvironmentVariable(key = "TOPIC", value = "test-topic")
    @SetEnvironmentVariable(key = "DI", value = "5")
    @SetEnvironmentVariable(key = "NUMBER_PARTITIONS", value = "10")
    @SetEnvironmentVariable(key = "REB_TIME", value = "5")
    @SetEnvironmentVariable(key = "FUP", value = ".2")
    @SetEnvironmentVariable(key = "FDOWN", value = ".9")
    @SetEnvironmentVariable(key = "BOOTSTRAP_SERVERS", value = "localhost:9092")
    public void buildProcessingPerProviderMetricTest() {
        SimpleQueryBuilder queryBuilder = SimpleQueryBuilder.builder()
                .query(
                        SumMetricBuilder.builder()
                                .metric(RateMetricQueryBuilder.builder()
                                        .metric(MetricBuilder.builder()
                                                .name(DistributionSummaryMetricQueryBuilder.builder()
                                                        .metric(EVENTS_PROCESSING_TIME)
                                                        .suffix(DistributionSummarySuffix.COUNT).build())
                                                .addTag(TAG_KAFKA_TOPIC, "topic")
                                                .timeWindow("5s")
                                        ).build()
                                ).build()
                                + "/" +
                                SumMetricBuilder.builder()
                                        .metric(RateMetricQueryBuilder.builder()
                                                .metric(MetricBuilder.builder()
                                                        .name(DistributionSummaryMetricQueryBuilder.builder()
                                                                .metric(EVENTS_PROCESSING_TIME)
                                                                .suffix(DistributionSummarySuffix.SUM).build())
                                                        .addTag(TAG_KAFKA_TOPIC, "topic")
                                                        .timeWindow("5s")
                                                ).build()
                                        ).build()
                );
        System.out.println(queryBuilder.build());

    }
}
