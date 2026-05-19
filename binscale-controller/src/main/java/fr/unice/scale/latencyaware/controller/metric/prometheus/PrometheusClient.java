package fr.unice.scale.latencyaware.controller.metric.prometheus;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import fr.unice.scale.latencyaware.common.error.exception.MetricResultEmptyException;
import fr.unice.scale.latencyaware.controller.metric.ClientMetricCollector;
import org.apache.hc.core5.net.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class PrometheusClient implements ClientMetricCollector {
    private static final String PROM_URL = "http://prometheus-service:9090/api/v1/query";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(PrometheusClient.class);

    public String rawQuery(String promQL) {
        try {
            URI uri = new URIBuilder(PROM_URL)
                    .addParameter("query", promQL)
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            CompletableFuture<String> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body);
            String res = response.get();
//            logger.info("Querying Prometheus result : {}", res);
            return res;
        } catch (Exception e) {
//            logger.error("Error querying Prometheus with query {}: {}", promQL, e.getMessage());
            return null;
        }
    }

    public <T> T query(String request, Class<T> tClass) throws MetricResultEmptyException {
        return query(request, TypeFactory.defaultInstance().constructType(tClass));
    }

    public <T> T query(String request, TypeReference<T> typeReference) throws MetricResultEmptyException {
        return query(request, mapper.getTypeFactory().constructType(typeReference.getType()));
    }

    public <K, V> Map<K, List<V>> mappedResultQuery(String request, String mapKey, List<String> otherTagsForValue,
                                                    Class<K> keyClass, Class<V> valueClass) throws MetricResultEmptyException {
        JSONObject response = JSONObject.parseObject(rawQuery(request));
//        logger.info("Prometheus request: {}", request);

        JSONArray results = response.getJSONObject("data")
                .getJSONArray("result");
        if (results.isEmpty()) throw new MetricResultEmptyException(request);
        Map<K, List<V>> map = new LinkedHashMap<>();

        for (int i = 0; i < results.size(); i++) {
            JSONObject entry = results.getJSONObject(i);

            String keyStr = entry.getJSONObject("metric").getString(mapKey);
            K key = mapper.convertValue(keyStr, keyClass);

            V value;

            if (entry.containsKey("value")) {
                // VECTOR: [ts, val]
                JSONArray valueArr = entry.getJSONArray("value");
                Map<String, Object> val = new HashMap<>();
                val.put("timestamp", valueArr.getDouble(0).longValue());
                val.put("metric", valueArr.getString(1));
                for (String tag : otherTagsForValue) {
                    String tagValue = entry.getJSONObject("metric").getString(tag);
                    val.put(tag, tagValue);
                }

                value = mapper.convertValue(val, valueClass);

            } else if (entry.containsKey("values")) {
                JSONArray valuesArr = entry.getJSONArray("values");

                JSONArray last = valuesArr.getJSONArray(valuesArr.size() - 1);
                Map<String, Object> val = new HashMap<>();
                val.put("timestamp", last.getDouble(0).longValue());
                val.put("metric", last.getString(1));
                for (String tag : otherTagsForValue) {
                    String tagValue = entry.getJSONObject("metric").getString(tag);
                    val.put(tag, tagValue);
                }

                value = mapper.convertValue(val, valueClass);

            } else {
                throw new IllegalStateException("Prometheus result contains neither 'value' nor 'values'");
            }

            if (!map.containsKey(key)) {
                List<V> values = new ArrayList<>();
                values.add(value);
                map.put(key, values);
            } else {
                map.get(key).add(value);
            }
        }

        return map;
    }

    public <K, V> Map<K, V> mappedResultQuery(String request, String mapKey,
                                              Class<K> keyClass, Class<V> valueClass) throws MetricResultEmptyException {
        JSONObject response = JSONObject.parseObject(rawQuery(request));

        JSONArray results = response.getJSONObject("data")
                .getJSONArray("result");
        if (results.isEmpty()) throw new MetricResultEmptyException(request);
        Map<K, V> map = new LinkedHashMap<>();

        for (int i = 0; i < results.size(); i++) {
            JSONObject entry = results.getJSONObject(i);

            String keyStr = entry.getJSONObject("metric").getString(mapKey);
            K key = mapper.convertValue(keyStr, keyClass);

            V value;

            if (entry.containsKey("value")) {
                // VECTOR: [ts, val]
                JSONArray valueArr = entry.getJSONArray("value");
                Map<String, Object> val = new HashMap<>();
                val.put("timestamp", valueArr.getDouble(0).longValue());
                val.put("metric", valueArr.getString(1));

                value = mapper.convertValue(val, valueClass);

            } else if (entry.containsKey("values")) {
                JSONArray valuesArr = entry.getJSONArray("values");

                JSONArray last = valuesArr.getJSONArray(valuesArr.size() - 1);
                Map<String, Object> val = new HashMap<>();
                val.put("timestamp", last.getDouble(0).longValue());
                val.put("metric", last.getString(1));

                value = mapper.convertValue(val, valueClass);

            } else {
                throw new IllegalStateException("Prometheus result contains neither 'value' nor 'values'");
            }

            map.put(key, value);
        }

        return map;
    }

    @Override
    public <T> T query(String request, JavaType type) throws MetricResultEmptyException {
        JSONObject response = JSONObject.parseObject(rawQuery(request));

        JSONObject data = response.getJSONObject("data");
        String resultType = data.getString("resultType");
        JSONObject first = data.getJSONArray("result").getJSONObject(0);
        if (first == null) {
            throw new MetricResultEmptyException(request);
        }

        Object toConvert;

        if ("vector".equals(resultType)) {
            // [timestamp, value]
            JSONArray value = first.getJSONArray("value");

            Map<String, Object> entry = new HashMap<>();
            entry.put("timestamp", value.getDouble(0).longValue());
            entry.put("metric", value.getString(1));

            toConvert = entry;

        } else if ("matrix".equals(resultType)) {
            // [[ts, value], [ts, value]]
            JSONArray values = first.getJSONArray("values");

            List<Map<String, Object>> list = new ArrayList<>();

            for (int i = 0; i < values.size(); i++) {
                JSONArray pair = values.getJSONArray(i);

                Map<String, Object> entry = new HashMap<>();
                entry.put("timestamp", pair.getDouble(0).longValue());
                entry.put("metric", pair.getString(1));

                list.add(entry);
            }

            toConvert = list;

        } else {
            throw new IllegalArgumentException("Unsupported resultType: " + resultType);
        }

        return mapper.convertValue(toConvert, type);
    }

}
