package fr.unice.scale.latencyaware.common.error.exception;

public class MetricResultEmptyException extends Exception {
    public MetricResultEmptyException(String request) {
        super(request);
    }
}
