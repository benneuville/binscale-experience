package fr.unice.scale.latencyaware.consumer.metrics;

import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import fr.unice.scale.latencyaware.common.utils.MetricUtils;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Gauge;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Date;

import static fr.unice.scale.latencyaware.common.constant.CommonVariables.*;
import static fr.unice.scale.latencyaware.common.utils.MetricUtils.MetricVariables;

public class MetricsCollector {
    private static final Logger logger = LoggerFactory.getLogger(MetricsCollector.class);
    public static TimeMeasure latencygaugemeasure;
    public static Gauge latencygauge;
    public static DistributionSummary distributionSummary;
    private static MetricsCollector INSTANCE;

    protected MetricsCollector() {
        latencygaugemeasure = new TimeMeasure(0.0);
        latencygauge = Gauge.builder(MetricVariables.LATENCY_GAUGE, latencygaugemeasure, TimeMeasure::getDuration)
                .tag(MetricVariables.TAG_TOPIC, TOPIC)
                .tag(MetricVariables.TAG_GROUP_ID, GROUP_ID)
                .register(PrometheusUtils.prometheusRegistry);

        distributionSummary = DistributionSummary.builder(MetricUtils.eventLatencyMetricName(TOPIC))
                .tag(MetricVariables.TAG_TOPIC, TOPIC)
                .tag(MetricVariables.TAG_GROUP_ID, GROUP_ID)
                .register(PrometheusUtils.prometheusRegistry);
    }

    public static MetricsCollector getInstance() {
        if (INSTANCE == null) {
            logger.info("Creating MetricsCollector instance");
            INSTANCE = new MetricsCollector();
        }
        return INSTANCE;
    }

    public void collect(ConsumerRecord<String, EventCustomer> record, double processTime) {

        long currentTimeMillis = System.currentTimeMillis();
        Date currentDate = new Date(currentTimeMillis);

        Timestamp timestamp = new Timestamp(record.timestamp());
        Date insertionDate = new Date(timestamp.getTime());

        // export data in logs for Filebeat
        logger.info("latency is {}, insertion time is {}, processing time is {}",
                currentTimeMillis - record.timestamp(), DATE_FORMAT.format(insertionDate), DATE_FORMAT.format(currentDate));

        latencygaugemeasure
                .setDuration(System.currentTimeMillis() - record.timestamp());
        distributionSummary.record(processTime);
    }

    public void resetLatency() {
        latencygaugemeasure.setDuration(0.0);
    }
}
