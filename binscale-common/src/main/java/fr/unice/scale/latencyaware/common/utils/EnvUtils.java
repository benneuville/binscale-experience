package fr.unice.scale.latencyaware.common.utils;

import fr.unice.scale.latencyaware.common.error.ErrorHandler;
import fr.unice.scale.latencyaware.common.error.exception.EnvironmentValueUndefineException;


import java.util.Optional;

public class EnvUtils {

    private static ErrorHandler handler = ErrorHandler.defaultHandler();
    // ENV OR DEFAULT VALUE

    public static int envOrDefault(String key, int defaultIntegerValue) {
        return envOrDefault(key, defaultIntegerValue, Integer::parseInt);
    }

    public static Long envOrDefault(String key, Long defautLongValue) {
        return envOrDefault(key, defautLongValue, Long::parseLong);
    }

    public static String envOrDefault(String key, String defaultStringValue) {
        return envOrDefault(key, defaultStringValue, s -> s);
    }

    public static Boolean envOrDefault(String key, boolean defaultBooleanValue) {
        return envOrDefault(key, defaultBooleanValue, Boolean::parseBoolean);
    }

    public static Float envOrDefault(String key, Float defaultFloatValue) {
        return envOrDefault(key, defaultFloatValue, Float::parseFloat);
    }

    public static <T> T envOrDefault(String key, T defaultValue, java.util.function.Function<String, T> mapper) {
        return Optional.of(System.getenv(key)).map(mapper).orElse(defaultValue);
    }

    //ENV OR ERROR
    public static String envString(String key) {
        return Optional.of(System.getenv(key)).orElseThrow(() -> new EnvironmentValueUndefineException(key));
    }

    public static int envInt(String key) {
        return Optional.of(System.getenv(key)).map(Integer::parseInt).orElseThrow(() -> new EnvironmentValueUndefineException(key));
    }

    public static Float envFloat(String key) {
        return Optional.of(System.getenv(key)).map(Float::parseFloat).orElseThrow(() -> new EnvironmentValueUndefineException(key));
    }

    public static Boolean envBool(String key) {
        return Optional.of(System.getenv(key)).map(Boolean::parseBoolean).orElseThrow(() -> new EnvironmentValueUndefineException(key));
    }
}