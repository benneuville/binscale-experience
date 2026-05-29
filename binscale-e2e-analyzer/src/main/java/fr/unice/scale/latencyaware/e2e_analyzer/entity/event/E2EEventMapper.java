package fr.unice.scale.latencyaware.e2e_analyzer.entity.event;

import fr.unice.scale.latencyaware.common.entity.EventCustomer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;

import static fr.unice.scale.latencyaware.common.constant.CommonVariables.EXTERNAL_GROUP_NAME;
import static fr.unice.scale.latencyaware.common.constant.CommonVariables.HEADER_GROUP_ID_KEY;

public class E2EEventMapper extends HashMap<String, E2EEventTracker> {
    public void addEvent(ConsumerRecord<String, EventCustomer> event) {
        Header headergid = event.headers().lastHeader(HEADER_GROUP_ID_KEY);
        String idgroup;
        if (Objects.nonNull(headergid))
            idgroup = new String(headergid.value(), StandardCharsets.UTF_8);
        else
            idgroup = EXTERNAL_GROUP_NAME;
        E2EEvent e2eEvent = new E2EEvent(idgroup, event.timestamp());
        putIfAbsent(event.key(), new E2EEventTracker(event.key()));
        get(event.key()).addEvent(e2eEvent);
    }
}
