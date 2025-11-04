package fr.unice.scale.latencyaware.controller.metric;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

public class PrometheusClient {
    private static final String PROM_URL = "http://prometheus-operated:9090/api/v1/query";
    private static final HttpClient client = HttpClient.newHttpClient();

    public String queryPrometheus(String promQL) throws Exception {
        String url = PROM_URL + "?query=" + java.net.URLEncoder.encode(promQL, StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder(new URI(url)).GET().build();

        CompletableFuture<String> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body);
        return null;
    }
}
