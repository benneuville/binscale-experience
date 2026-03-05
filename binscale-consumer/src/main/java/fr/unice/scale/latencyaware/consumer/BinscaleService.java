package fr.unice.scale.latencyaware.consumer;

import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import fr.unice.scale.latencyaware.consumer.config.BinscaleConsumerConfig;
import fr.unice.scale.latencyaware.consumer.config.DistributionConfigBuilder;
import fr.unice.scale.latencyaware.consumer.emission.EventEmission;
import fr.unice.scale.latencyaware.consumer.entity.DistributedEventCustomer;
import fr.unice.scale.latencyaware.consumer.entity.DistributionConfig;
import fr.unice.scale.latencyaware.consumer.ingestion.EventIngestion;
import fr.unice.scale.latencyaware.consumer.metrics.MetricsCollector;
import fr.unice.scale.latencyaware.consumer.metrics.PrometheusUtils;
import fr.unice.scale.latencyaware.consumer.processing.EventProcessing;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

import static fr.unice.scale.latencyaware.consumer.constant.Variables.*;

public class BinscaleService implements Runnable {
    private final Logger log = LoggerFactory.getLogger(BinscaleService.class);

    private final EventIngestion consumer;
    private final EventProcessing distributor;
    private final EventEmission producer;
    private volatile boolean running = true;

    public BinscaleService(EventIngestion consumer,
                           EventProcessing distributor,
                           EventEmission producer) {
        this.consumer = consumer;
        this.distributor = distributor;
        this.producer = producer;
    }

    public BinscaleService() {
        DistributionConfig config = DistributionConfigBuilder.fromEnv();
        this.consumer = new EventIngestion(BinscaleConsumerConfig.fromEnv());
        this.distributor = new EventProcessing(PROCESSING_STRATEGY.getStrategyInstance(), config);
        this.producer = new EventEmission(config);
    }

    public void addShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                log.info("Starting exit...");
                consumer.wakeup();
                try {
                    this.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void run() {
        log.info("Starting Binscale Consumer Service...");
        if (TIME_BEFORE_AVAILABILITY != 0L) {
            try {
                log.info("Consumer in launch phase, waiting {} ms before being available", TIME_BEFORE_AVAILABILITY);
                Thread.sleep(TIME_BEFORE_AVAILABILITY);
            } catch (InterruptedException e) {
                shutdown();
                throw new RuntimeException(e);
            }
        }

        try {
            while (running) {
                ConsumerRecords<String, EventCustomer> events = consumer.poll(Duration.ofMillis(TIME_TO_COMMIT.longValue()));
                if (!events.isEmpty()) {
                    List<DistributedEventCustomer> processed = distributor.distribute(events);

                    producer.publish(processed);
                    consumer.commit();
                } else {
                    MetricsCollector.getInstance().resetLatency();
                }
            }
        } catch (Exception e) {
            shutdown();
            throw new RuntimeException(e);
        } finally {
            shutdown();
        }
    }

    public void init() {
        log.info("Init Binscale Consumer Service...");
        PrometheusUtils.initPrometheus();
    }

    public void shutdown() {
        log.info("Shutdown Binscale Consumer Service...");
        running = false;
        consumer.close();
        producer.close();
    }
}