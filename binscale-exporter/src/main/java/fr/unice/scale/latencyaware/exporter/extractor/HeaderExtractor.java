package fr.unice.scale.latencyaware.exporter.extractor;

import fr.unice.scale.latencyaware.exporter.entity.KafkaHeader;
import fr.unice.scale.latencyaware.exporter.entity.KafkaHeaderConfig;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.streams.processor.api.Record;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HeaderExtractor {
    public static Map<String, String> extractHeaders(Record<String, byte[]> record, KafkaHeaderConfig config) {
        Map<String, String> headerValueMap = new HashMap<>();
        for (KafkaHeader kh : config.getKafkaHeader()) {
            Header header = record.headers().lastHeader(kh.getName());
            headerValueMap.put(kh.getName(), (!Objects.isNull(header)) ? new String(header.value(), StandardCharsets.UTF_8) : kh.getDefaultValue());
        }
        return headerValueMap;
    }
}
