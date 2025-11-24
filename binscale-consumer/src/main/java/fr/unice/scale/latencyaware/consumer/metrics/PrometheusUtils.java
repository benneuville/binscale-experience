package fr.unice.scale.latencyaware.consumer.metrics;

import com.sun.net.httpserver.HttpServer;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class PrometheusUtils {
    public final static PrometheusMeterRegistry prometheusRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);

    protected final static Logger logger = LoggerFactory.getLogger(PrometheusUtils.class);

    public static void initPrometheus() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/prometheus", httpExchange -> {
                String response = prometheusRegistry.scrape();
                httpExchange.sendResponseHeaders(200, response.getBytes().length);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            });
            logger.info("Starting Prometheus HTTP server on port 8080");
            new Thread(server::start).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}