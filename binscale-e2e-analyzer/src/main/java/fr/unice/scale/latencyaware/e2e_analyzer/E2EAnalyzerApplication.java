package fr.unice.scale.latencyaware.e2e_analyzer;

import fr.unice.scale.latencyaware.e2e_analyzer.service.E2EAnalyzerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class E2EAnalyzerApplication implements CommandLineRunner {

    private final E2EAnalyzerService e2EAnalyzerService;

    private final ConfigurableApplicationContext context;

    public E2EAnalyzerApplication(E2EAnalyzerService e2EAnalyzerService, ConfigurableApplicationContext context) {
        this.e2EAnalyzerService = e2EAnalyzerService;
        this.context = context;
    }

    public static void main(String[] args) {
        SpringApplication.run(E2EAnalyzerApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        e2EAnalyzerService.run();

        context.close();
        System.exit(0);
    }
}