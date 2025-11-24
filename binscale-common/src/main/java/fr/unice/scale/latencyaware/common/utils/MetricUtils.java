package fr.unice.scale.latencyaware.common.utils;

public class MetricUtils {
    public static String eventLatencyMetricName(String topic) {
        return MetricVariables.EVENTS_LATENCY_PREFIX + topic;
    }

    public static String publishedEventMetric(String topic) {
        return MetricVariables.EVENTS_PUBLISHED_PREFIX + topic;
    }

    public static class MetricVariables {
        public static final String EVENTS_LATENCY_PREFIX = "events_latency_";
        public static final String EVENTS_PUBLISHED_PREFIX = "events_published_";
        public static final String LATENCY_GAUGE = "latency_gauge";
        public static final String TAG_TOPIC = "topic";
        public static final String TAG_GROUP_ID = "group_id";
        public static final String KAFKA_CONSUMER_GROUP_LAG = "kafka_consumergroup_lag";
        public static final String KAFKA_TOPIC_PARTITION_CURRENT_OFFSET = "kafka_topic_partition_current_offset";
        public static final String TAG_KAFKA_CONSUMER_GROUP = "consumergroup";
        public static final String TAG_KAFKA_PARTITION = "partition";
        public static final String TAG_KAFKA_TOPIC = "topic";
    }


}
