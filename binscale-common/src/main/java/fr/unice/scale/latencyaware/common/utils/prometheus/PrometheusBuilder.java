package fr.unice.scale.latencyaware.common.utils.prometheus;

import java.util.HashMap;
import java.util.Map;

public abstract class PrometheusBuilder {

    public Map<String, Object> params = new HashMap<>();

    public abstract String getPattern();

    public String build() {
        for (String key : params.keySet()) {
            Object builder = params.get(key);
            if (params.get(key) instanceof PrometheusBuilder) {
                params.put(key, ((PrometheusBuilder) builder).build());
            }
        }
        return PromQueryUtils.namedFormat(getPattern(), params);
    }
}
