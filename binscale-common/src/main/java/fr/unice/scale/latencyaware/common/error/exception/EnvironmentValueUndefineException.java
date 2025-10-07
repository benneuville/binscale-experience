package fr.unice.scale.latencyaware.common.error.exception;

public class EnvironmentValueUndefineException extends AbstractException {
    public EnvironmentValueUndefineException(String key) {
        super("Environment Variable is missing/undefined : " + key + "\n");
    }

}
