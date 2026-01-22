package fr.unice.scale.latencyaware.controller.metric;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import fr.unice.scale.latencyaware.controller.binpack.MockConsumerGroup;
import fr.unice.scale.latencyaware.controller.entity.ConsumerGroup;
import fr.unice.scale.latencyaware.controller.entity.meta_data.CGMetaData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junitpioneer.jupiter.SetEnvironmentVariable;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class MetricCollectorTest {

    private Map<ConsumerGroup, CGMetaData> consumerGroupMetaDatas;
    private CGMetaData cgMetaData;
    private ConsumerGroup cg;
    private ObjectWriter objectWriter;

    @BeforeEach
    public void setUp() {
        objectWriter = new ObjectMapper().writer();
        consumerGroupMetaDatas = new HashMap<>();
        cg = new MockConsumerGroup("test-topic", 10, 1);
        cgMetaData = new CGMetaData(cg, 200);
        consumerGroupMetaDatas.put(cg, cgMetaData);
    }

    @Test
    @SetEnvironmentVariable(key = "TOPIC", value = "test-topic")
    public void jacksonMapperTest() throws JsonProcessingException {
        cg.setNowLastUpScaleDecision();
        assertDoesNotThrow(() -> objectWriter.writeValueAsString(consumerGroupMetaDatas.values()));
        System.out.println(objectWriter.writeValueAsString(consumerGroupMetaDatas.values()));
    }
}
