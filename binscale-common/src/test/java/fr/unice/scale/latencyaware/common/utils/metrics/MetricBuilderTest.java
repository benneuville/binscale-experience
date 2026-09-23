package fr.unice.scale.latencyaware.common.utils.metrics;

import fr.unice.scale.latencyaware.common.utils.prometheus.SimpleQueryBuilder;
import fr.unice.scale.latencyaware.common.utils.prometheus.metrics.CounterMetricQueryBuilder;
import fr.unice.scale.latencyaware.common.utils.prometheus.metrics.MetricBuilder;
import fr.unice.scale.latencyaware.common.utils.prometheus.metrics.RateMetricQueryBuilder;
import fr.unice.scale.latencyaware.common.utils.prometheus.metrics.SumMetricBuilder;
import org.junit.jupiter.api.Test;

import static fr.unice.scale.latencyaware.common.constant.CommonVariables.MESSAGE_COUNTER_NAME;
import static fr.unice.scale.latencyaware.common.utils.MetricUtils.MetricVariables.*;

public class MetricBuilderTest {

    @Test
    public void testMetricBuild() {

        SimpleQueryBuilder queryBuilder = SimpleQueryBuilder.builder()
                .query(
                        SumMetricBuilder.builder()
                                .metric(RateMetricQueryBuilder.builder()
                                        .metric(MetricBuilder.builder()
                                                .name(CounterMetricQueryBuilder.builder()
                                                        .metric(MESSAGE_COUNTER_NAME)
                                                        .build()
                                                )
                                                .addTag(TAG_KAFKA_TOPIC, "topic")
                                                .timeWindow("5s")
                                                .build()
                                        )
                                        .build()
                                )
//                                .addByTag(TAG_KAFKA_PARTITION)
                                .addByTag(TAG_PROVIDER_GROUP_ID)
                                .build()
                );
        System.out.println(queryBuilder.build());

        queryBuilder = SimpleQueryBuilder.builder()
                .query(
                        MetricBuilder.builder()
                                .name(KAFKA_CONSUMER_GROUP_LAG)
                                .addTag(TAG_KAFKA_CONSUMER_GROUP, "consumer-group")
                                .addTag(TAG_KAFKA_TOPIC, "topic")
                                .timeWindow("5s")
                                .build()
                );
        System.out.println(queryBuilder.build());
    }

    public void testMapResult() {
        String res = "{\"status\":\"success\",\"data\":{\"resultType\":\"vector\",\"result\":[{\"metric\":{\"groupId\":\"latencyehancedgroup1\"},\"value\":[1785228624.654,\"39.833333333333336\"]},{\"metric\":{\"groupId\":\"unknown\"},\"value\":[1785228624.654,\"279.6666666666667\"]}]}}";
    }
}
