package fr.unice.scale.latencyaware.consumer.metrics;

import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Meter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static fr.unice.scale.latencyaware.common.constant.CommonVariables.*;
import static fr.unice.scale.latencyaware.common.utils.MetricUtils.MetricVariables;
import static fr.unice.scale.latencyaware.common.utils.MetricUtils.MetricVariables.*;

public class MetricsCollector {
    private static final Logger logger = LoggerFactory.getLogger(MetricsCollector.class);
    public static Map<Integer, TimeMeasure> latencygaugemeasure;
    /**
     * This MeterProvider(DistributionSummary) give for each partition of a topic, the sum of processingTime/ the number (count) of processed events
     */
    public static Meter.MeterProvider<DistributionSummary> processingTimeSummary;
    private static MetricsCollector INSTANCE;

    protected MetricsCollector() {
        latencygaugemeasure = new HashMap<>();

        processingTimeSummary = DistributionSummary.builder(EVENTS_PROCESSING_TIME)
                .tag(MetricVariables.TAG_TOPIC, TOPIC)
                .tag(MetricVariables.TAG_GROUP_ID, GROUP_ID)
                .withRegistry(PrometheusUtils.prometheusRegistry);
    }

    public static MetricsCollector getInstance() {
        if (INSTANCE == null) {
            logger.info("Creating MetricsCollector instance");
            INSTANCE = new MetricsCollector();
        }
        return INSTANCE;
    }

    public TimeMeasure getLatencyTimeMeasureByPartition(int partition) {
        TimeMeasure timeMeasure = latencygaugemeasure.get(partition);
        if (timeMeasure == null) {
            TimeMeasure latencyMeasure = new TimeMeasure(0.0);
            Gauge.builder(LATENCY_GAUGE,
                            latencyMeasure,
                            TimeMeasure::getDuration)
                    .tag(MetricVariables.TAG_TOPIC, TOPIC)
                    .tag(MetricVariables.TAG_GROUP_ID, GROUP_ID)
                    .tag(MetricVariables.TAG_KAFKA_PARTITION, String.valueOf(partition))
                    .register(PrometheusUtils.prometheusRegistry);
            latencygaugemeasure.put(partition, latencyMeasure);
            return latencyMeasure;
        }
        return timeMeasure;
    }

    public void collect(ConsumerRecord<String, EventCustomer> record, double processTime) {

        long currentTimeMillis = System.currentTimeMillis();
        Date currentDate = new Date(currentTimeMillis);

        Timestamp timestamp = new Timestamp(record.timestamp());
        Date insertionDate = new Date(timestamp.getTime());

        // export data in logs for Filebeat
        logger.info("latency is {}, insertion time is {}, processing time is {}, event come from partition {} and position {}",
                currentTimeMillis - record.timestamp(),
                DATE_FORMAT.format(insertionDate),
                DATE_FORMAT.format(currentDate),
                record.partition(),
                record.offset());


        getLatencyTimeMeasureByPartition(record.partition())
                .setDuration(System.currentTimeMillis() - record.timestamp());
        processingTimeSummary.withTag(TAG_KAFKA_PARTITION, String.valueOf(record.partition())).record(processTime);
    }

    public void resetLatency() {
        latencygaugemeasure.forEach((integer, timeMeasure) -> timeMeasure.setDuration(0.0));
    }
}
