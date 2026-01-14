package fr.unice.scale.latencyaware.controller.metric;

import com.fasterxml.jackson.databind.JavaType;
import fr.unice.scale.latencyaware.common.error.exception.MetricResultEmptyException;

public interface ClientMetricCollector {
    public <T> T query(String request, JavaType type) throws MetricResultEmptyException;
}
