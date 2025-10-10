package fr.unice.scale.latencyaware.common.error.exception;

import fr.unice.scale.latencyaware.common.error.ErrorHandler;

public abstract class AbstractException extends RuntimeException {
    private static ErrorHandler handler = ErrorHandler.defaultHandler();

    public AbstractException(String message) {
        super(message);
        handler.handle(this);
    }
}
