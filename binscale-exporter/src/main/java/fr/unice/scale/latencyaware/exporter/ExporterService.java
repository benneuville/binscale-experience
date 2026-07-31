package fr.unice.scale.latencyaware.exporter;

import fr.unice.scale.latencyaware.common.prometheus.PrometheusUtils;
import fr.unice.scale.latencyaware.exporter.admin.AdminComponent;
import fr.unice.scale.latencyaware.exporter.config.KafkaHeaderConfigBuilder;
import fr.unice.scale.latencyaware.exporter.entity.KafkaHeaderConfig;
import fr.unice.scale.latencyaware.exporter.processor.CounterProcessorSupplier;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static fr.unice.scale.latencyaware.common.constant.CommonVariables.KAFKA_BOOTSTRAP_SERVERS;

public class ExporterService {
    private static final Logger log = LoggerFactory.getLogger(ExporterService.class);
    private final KafkaHeaderConfig config;
    private final PrometheusMeterRegistry registry;
    private final AdminComponent adminComponent;
    private final Object lock = new Object();
    private final ScheduledExecutorService scheduler;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private KafkaStreams streams;
    private Set<String> topics = new HashSet<>();

    public ExporterService() {
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.adminComponent = new AdminComponent();
        this.config = KafkaHeaderConfigBuilder.fromEnv();
        PrometheusUtils.initPrometheus();
        this.registry = PrometheusUtils.prometheusRegistry;
    }

    public void start() {
        if (running.compareAndSet(false, true)) {
            log.info("Démarrage de l'exporter Kafka Streams...");
            rebuildStreamsSafely();
            scheduler.scheduleAtFixedRate(
                    this::checkForNewTopics,
                    5,
                    5,
                    TimeUnit.SECONDS
            );
        }
    }

    private void checkForNewTopics() {
        try {
            Set<String> newTopics = adminComponent.discoverTopics();
            if (!newTopics.equals(topics)) {
                log.info("Nouveaux topics détectés: {}. Reconstruisons KafkaStreams...", newTopics);
                rebuildStreamsSafely(newTopics);
            }
        } catch (Exception e) {
            log.error("Erreur lors de la découverte des topics", e);
        }
    }

    private void rebuildStreamsSafely() {
        try {
            rebuildStreamsSafely(adminComponent.discoverTopics());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void rebuildStreamsSafely(Set<String> newTopics) {
        synchronized (lock) {
            if (newTopics.equals(topics)) {
                return;
            }
            log.info("Resynchronization KafkaStreams...");

            if (streams != null) {
                log.info("Stop Previous streams...");
                streams.close(Duration.ofSeconds(5));
            }

            log.info("Starting KafkaStreams...");
            topics.addAll(newTopics);

            Topology builder = new Topology();
            for (String topic : topics) {
                if (config.isInBlackListedTopics(topic)) continue;
                String sourceName = topic + "-counter";
                builder.addSource(sourceName, topic)
                        .addProcessor(sourceName + "-processor", new CounterProcessorSupplier(config, registry, topic), sourceName);
            }

            Properties props = buildStreamsProperties();
            if (!topics.isEmpty()) {
                this.streams = new KafkaStreams(builder, props);
                this.streams.start();
            }
            log.info("KafkaStreams up for {} topics: {}", topics.size(), topics);
        }
    }


    private Properties buildStreamsProperties() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "latency-aware-exporter-" + System.currentTimeMillis());
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVERS);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.ByteArray().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.ByteArray().getClass());
        props.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE_V2);
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 100);
        return props;
    }

    public void stop() {
        streams.close();
    }
}