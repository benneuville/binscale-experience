package fr.unice.scale.latencyaware.common.utils;

import fr.unice.scale.latencyaware.common.error.ErrorHandler;
import fr.unice.scale.latencyaware.common.error.exception.EnvironmentValueUndefineException;

import java.util.Optional;
import java.util.function.Function;

public class EnvUtils {

    // Gestionnaire d'erreurs générique (si tu veux logguer différemment plus tard)
    private static ErrorHandler handler = ErrorHandler.defaultHandler();

    // ==========================
    // ENV OR DEFAULT VALUE
    // ==========================
    public static int envOrDefault(String key, int defaultIntegerValue) {
        return envOrDefault(key, defaultIntegerValue, Integer::parseInt);
    }

    public static long envOrDefault(String key, long defaultLongValue) {
        return envOrDefault(key, defaultLongValue, Long::parseLong);
    }

    public static String envOrDefault(String key, String defaultStringValue) {
        return envOrDefault(key, defaultStringValue, String::trim);
    }

    public static boolean envOrDefault(String key, boolean defaultBooleanValue) {
        return envOrDefault(key, defaultBooleanValue, Boolean::parseBoolean);
    }

    public static float envOrDefault(String key, float defaultFloatValue) {
        return envOrDefault(key, defaultFloatValue, Float::parseFloat);
    }

    /**
     * Méthode générique sécurisée : jamais d'exception levée.
     * Gère le cas où System.getenv(key) est null, ou le mapper lève une exception.
     */
    public static <T> T envOrDefault(String key, T defaultValue, Function<String, T> mapper) {
        return Optional.ofNullable(System.getenv(key))
                .map(mapper).orElse(defaultValue);
    }

    // ==========================
    // ENV OR ERROR
    // ==========================
    public static String envString(String key) {
        return env(key, s -> s);
    }


    public static int envInt(String key) {
        return env(key, Integer::parseInt);
    }

    public static float envFloat(String key) {
        return env(key, Float::parseFloat);
    }

    public static boolean envBool(String key) {
        return env(key, Boolean::parseBoolean);
    }

    public static Double envDouble(String key) {
        return env(key, Double::parseDouble);
    }

    public static <T> T env(String key, Function<String, T> map) {
        return Optional.ofNullable(System.getenv(key))
                .map(map)
                .orElseThrow(() -> new EnvironmentValueUndefineException(key));
    }
}
