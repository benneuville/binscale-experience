package fr.unice.scale.latencyaware.common.utils.prometheus;

public class SimpleQueryBuilder extends PrometheusBuilder implements QueryBuilder {

    private static final String SIMPLE_URI_PATTERN = "#{query}";
    private static final String QUERY_STRING = "query";

    public SimpleQueryBuilder() {
    }

    public static SimpleQueryBuilder builder() {
        return new SimpleQueryBuilder();
    }

    public SimpleQueryBuilder query(String query) {
        params.put(QUERY_STRING, query);
        return this;
    }

    @Override
    public String getPattern() {
        return SIMPLE_URI_PATTERN;
    }
}