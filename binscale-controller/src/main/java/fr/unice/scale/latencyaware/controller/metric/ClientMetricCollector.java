package fr.unice.scale.latencyaware.controller.metric;

import com.fasterxml.jackson.databind.JavaType;

public interface ClientMetricCollector {
    public <T> T query(String request, JavaType type);
}
