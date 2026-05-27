package fr.unice.scale.latencyaware.e2e_analyzer;

import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import fr.unice.scale.latencyaware.e2e_analyzer.config.BinscaleE2EIngestionConfig;
import fr.unice.scale.latencyaware.e2e_analyzer.config.E2EAnalyzerConfigBuilder;
import fr.unice.scale.latencyaware.e2e_analyzer.entity.E2EAnalyzerConfig;
import fr.unice.scale.latencyaware.e2e_analyzer.entity.Topic;
import fr.unice.scale.latencyaware.e2e_analyzer.ingestion.E2EAnalyzerEventIngestion;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static fr.unice.scale.latencyaware.e2e_analyzer.constant.Variables.TIME_TO_COMMIT;

public class E2EAnalyzerService implements Runnable {
    @Override
    public void run() {
        E2EAnalyzerConfig config = E2EAnalyzerConfigBuilder.fromEnv();
        List<E2EAnalyzerEventIngestion> evIngest = new ArrayList<>();

        for (Topic t : config.getTopics()) {
            evIngest.add(new E2EAnalyzerEventIngestion(BinscaleE2EIngestionConfig.fromEnv(t.getName())));
        }

        while (true) {
            List<ConsumerRecords<String, EventCustomer>> events = evIngest.stream().map(ei -> ei.poll(Duration.ofMillis(TIME_TO_COMMIT.longValue()))).collect(Collectors.toList());
        }
    }
}
