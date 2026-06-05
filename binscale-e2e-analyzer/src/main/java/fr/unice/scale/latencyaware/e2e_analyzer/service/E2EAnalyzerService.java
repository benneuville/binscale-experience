package fr.unice.scale.latencyaware.e2e_analyzer.service;

import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import fr.unice.scale.latencyaware.e2e_analyzer.config.BinscaleE2EIngestionConfig;
import fr.unice.scale.latencyaware.e2e_analyzer.config.E2EAnalyzerConfigBuilder;
import fr.unice.scale.latencyaware.e2e_analyzer.entity.E2EAnalyzerConfig;
import fr.unice.scale.latencyaware.e2e_analyzer.entity.ModeState;
import fr.unice.scale.latencyaware.e2e_analyzer.entity.Topic;
import fr.unice.scale.latencyaware.e2e_analyzer.entity.model.E2EEventTracker;
import fr.unice.scale.latencyaware.e2e_analyzer.event_merger.EventMerger;
import fr.unice.scale.latencyaware.e2e_analyzer.ingestion.E2EAnalyzerEventIngestion;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static fr.unice.scale.latencyaware.e2e_analyzer.constant.Variables.TIME_TO_COMMIT;

@Service
public class E2EAnalyzerService {
    private final EventMerger eventMerger;
    private final ExporterService exporter;
    private final Logger log = LoggerFactory.getLogger(E2EAnalyzerService.class);

    private final List<E2EAnalyzerEventIngestion> evIngest;
    private ModeState state;

    public E2EAnalyzerService(EventMerger eventMerger, ExporterService exporter) {
        this.eventMerger = eventMerger;
        this.exporter = exporter;
        this.state = ModeState.MERGING;
        this.evIngest = new ArrayList<>();
    }

    public void run() {
        E2EAnalyzerConfig config = E2EAnalyzerConfigBuilder.fromEnv();

        eventMerger.cleanTables();

        for (Topic t : config.getTopics()) {
            evIngest.add(new E2EAnalyzerEventIngestion(BinscaleE2EIngestionConfig.fromEnv(t.getName())));
        }

        while (ModeState.MERGING.equals(this.state) && !isAllEventProcessed()) {
            List<ConsumerRecords<String, EventCustomer>> events = evIngest.stream().map(ei -> ei.poll(Duration.ofMillis(TIME_TO_COMMIT.longValue()))).collect(Collectors.toList());
            eventMerger.eventMerger(events);
        }

        List<E2EEventTracker> eventTrackers = eventMerger.getAllEventTrackers();
        log.info("{}", eventTrackers);
        exporter.exportEvents(eventTrackers);


    }

    private boolean isAllEventProcessed() {
        for (E2EAnalyzerEventIngestion consumer : evIngest) {
            Set<TopicPartition> partitions = consumer.assignment();
            if (partitions.isEmpty()) continue;
            Map<TopicPartition, Long> endOffsets = consumer.endOffsets(partitions);
            for (TopicPartition tp : partitions) {
                Long endOffset = endOffsets.get(tp);
                if (endOffset == null) continue;
                long currentOffset = consumer.position(tp);
                if (endOffset - currentOffset > 0) return false;
            }
        }
        return true;
    }

    public void changeMode(ModeState modeState) {
        this.state = modeState;
    }
}
