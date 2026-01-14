package fr.unice.scale.latencyaware.controller;

import fr.unice.scale.latencyaware.controller.assignment.AssignmentComponent;
import fr.unice.scale.latencyaware.controller.config.DistributionNodeConfigBuilder;
import fr.unice.scale.latencyaware.controller.entity.ConsumerGroup;
import fr.unice.scale.latencyaware.controller.entity.decision.ScaleDecision;
import fr.unice.scale.latencyaware.controller.entity.distribution.GraphDistributionConfig;
import fr.unice.scale.latencyaware.controller.entity.graph.Graph;
import fr.unice.scale.latencyaware.controller.entity.meta_data.CGMetaData;
import fr.unice.scale.latencyaware.controller.graph.GraphBuilder;
import fr.unice.scale.latencyaware.controller.graph.GraphBuilderImpl;
import fr.unice.scale.latencyaware.controller.metric.prometheus.PrometheusMetricCollector;
import fr.unice.scale.latencyaware.controller.processing.ClassicScalerProcessor;
import fr.unice.scale.latencyaware.controller.processing.ScalerProcessor;
import fr.unice.scale.latencyaware.controller.server.AssignmentServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static fr.unice.scale.latencyaware.controller.constant.Variables.DI;

public class ControllerService implements Runnable {
    public static Graph<ConsumerGroup> graph;
    private Logger log = LoggerFactory.getLogger(ControllerService.class);
    private PrometheusMetricCollector metricCollector;
    private ScalerProcessor scalerProcessor;
    private GraphBuilder graphBuilder;
    private AssignmentComponent assignmentComponent;

    private AssignmentServer server;


    public ControllerService() {
    }

    public void init() {

        server = new AssignmentServer(5002);
        Thread serverthread = new Thread(server);
        serverthread.start();

        this.graphBuilder = new GraphBuilderImpl();

        GraphDistributionConfig config = DistributionNodeConfigBuilder.fromEnv();

        graph = graphBuilder.buildGraph(config);

        this.metricCollector = new PrometheusMetricCollector();
        this.scalerProcessor = new ClassicScalerProcessor();
        this.assignmentComponent = new AssignmentComponent();
    }

    @Override
    public void run() {
        try {
            init();
            while (true) {
                Map<ConsumerGroup, CGMetaData> cgdatas = metricCollector.collectRawMetaData(graph);
                if (cgdatas.isEmpty()) {
                    log.warn("No ConsumerGroup MetaData collected, skipping this iteration");
                    continue;
                }
                Map<ConsumerGroup, ScaleDecision> decisions = scalerProcessor.process(graph, cgdatas);

                assignmentComponent.assignScale(graph, decisions);
                log.info("Sleeping for {} millisecond", DI);
                log.info("******************************************");
                Thread.sleep(DI.longValue());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
