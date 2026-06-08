package fr.unice.scale.latencyaware.e2e_analyzer;

import fr.unice.scale.latencyaware.e2e_analyzer.entity.ModeState;
import fr.unice.scale.latencyaware.e2e_analyzer.service.E2EAnalyzerService;
import fr.unice.scale.latencyaware.e2e_analyzer.service.ExporterService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static fr.unice.scale.latencyaware.e2e_analyzer.constant.Variables.MODE;

@SpringBootApplication
public class E2EAnalyzerApplication implements CommandLineRunner {

    private final E2EAnalyzerService e2EAnalyzerService;
    private final ExporterService exporterService;

    private final ConfigurableApplicationContext context;

    public E2EAnalyzerApplication(E2EAnalyzerService e2EAnalyzerService, ConfigurableApplicationContext context, ExporterService exporterService) {
        this.e2EAnalyzerService = e2EAnalyzerService;
        this.context = context;
        this.exporterService = exporterService;
    }

    public static void main(String[] args) {
        SpringApplication.run(E2EAnalyzerApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        if (ModeState.CONSUME.equals(MODE))
            e2EAnalyzerService.run();
        else if (ModeState.EXPORT.equals(MODE)) {
            exporterService.exportEvents(e2EAnalyzerService.getAllEventTrackers());
        }

        context.close();
        System.exit(0);
    }
}