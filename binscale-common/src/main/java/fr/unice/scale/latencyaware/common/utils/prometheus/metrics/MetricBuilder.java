package fr.unice.scale.latencyaware.common.utils.prometheus.metrics;

import fr.unice.scale.latencyaware.common.utils.prometheus.PrometheusBuilder;

import java.util.HashMap;
import java.util.Map;

public class MetricBuilder extends PrometheusBuilder {
    protected final static String PATTERN = "#{name}#{tags}#{time_window}";
    private final String NAME = "name";
    private final String TIME_WINDOW = "time_window";
    private final String TAGS = "tags";

    public Map<String, Object> tagsMap = new HashMap<>();

    public static MetricBuilder builder() {
        return new MetricBuilder();
    }

    public MetricBuilder name(String target) {
        this.params.put(NAME, target);
        return this;
    }

    public MetricBuilder timeWindow(String timeWindow) {
        this.params.put(TIME_WINDOW, timeWindow);
        return this;
    }

    public MetricBuilder addTag(String key, String value) {
        this.tagsMap.put(key, value);
        return this;
    }

    public MetricBuilder addTag(String key, Object value) {
        this.tagsMap.put(key, value.toString());
        return this;
    }

    private void buildTags() {
        if (tagsMap.isEmpty()) return;
        StringBuilder sb = new StringBuilder().append("{");
        int count = 0;
        for (Map.Entry<String, Object> entry : tagsMap.entrySet()) {
            sb.append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
            count++;
            if (count < tagsMap.size()) {
                sb.append(",");
            } else {
                sb.append("}");
            }
        }
        this.params.put(TAGS, sb.toString());
    }

    private void buildTimeWindow() {
        if (!this.params.containsKey(TIME_WINDOW)) {
            return;
        }
        this.params.put(TIME_WINDOW, "[" + this.params.get(TIME_WINDOW).toString() + "]");
    }

    @Override
    public String build() {
        this.buildTags();
        this.buildTimeWindow();
        return super.build();
    }

    @Override
    public String getPattern() {
        return PATTERN;
    }

}