package fr.unice.scale.latencyaware.common.error;

@FunctionalInterface
public interface ErrorHandler {
    void handle(Exception e);

    static ErrorHandler defaultHandler() {
        return e -> { throw new RuntimeException(e); };
    }
}