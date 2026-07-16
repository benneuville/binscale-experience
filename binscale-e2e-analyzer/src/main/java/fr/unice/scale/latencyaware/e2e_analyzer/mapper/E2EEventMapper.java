package fr.unice.scale.latencyaware.e2e_analyzer.mapper;

import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import fr.unice.scale.latencyaware.e2e_analyzer.entity.model.E2EEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Objects;

import static fr.unice.scale.latencyaware.common.constant.CommonVariables.EXTERNAL_GROUP_NAME;
import static fr.unice.scale.latencyaware.common.constant.CommonVariables.HEADER_GROUP_ID_KEY;

public class E2EEventMapper {
    public static E2EEvent toE2EEvent(ConsumerRecord<String, EventCustomer> event) {
        String nodeOrigin;
        Header nodeOriginHeader = event.headers().lastHeader(HEADER_GROUP_ID_KEY);
        if (Objects.isNull(nodeOriginHeader))
            nodeOrigin = EXTERNAL_GROUP_NAME;
        else
            nodeOrigin = new String(nodeOriginHeader.value(), StandardCharsets.UTF_8);
        return new E2EEvent(nodeOrigin, Instant.ofEpochMilli(event.timestamp()), event.key());
    }
}
