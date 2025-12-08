package fr.unice.scale.latencyaware.common.utils.prometheus.metrics;

import java.util.ArrayList;
import java.util.List;

public abstract class ByBuilder extends MetricBuilder {
    protected final static String BY_PATTERN = "#{metric} by (#{by_tags})";
    private final String METRIC = "metric";
    private final String BY_TAGS = "by_tags";

    private List<String> tagKeys = new ArrayList<>();

    ByBuilder() {
        super();
    }

    public ByBuilder addByTag(String tagKey) {
        if (!tagKeys.contains(tagKey)) {
            tagKeys.add(tagKey);
        }
        return this;
    }

    public abstract String getFormulaPattern();


    @Override
    public String getPattern() {
        if (tagKeys.isEmpty()) {
            return getFormulaPattern();
        }
        return BY_PATTERN.replace("#{" + METRIC + "}", getFormulaPattern());
    }

    @Override
    public String build() {
        if (tagKeys.isEmpty()) {
            return super.build();
        }
        this.params.put(BY_TAGS, String.join(", ", tagKeys));
        return super.build();
    }

}
