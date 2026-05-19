package fr.unice.scale.latencyaware.controller;

import fr.unice.scale.latencyaware.controller.entity.ConsumerGroup;
import fr.unice.scale.latencyaware.controller.entity.graph.Graph;
import fr.unice.scale.latencyaware.controller.entity.meta_data.CGMetaData;
import fr.unice.scale.latencyaware.controller.entity.meta_data.PartitionMetaData;
import fr.unice.scale.latencyaware.controller.processing.ClassicScalerProcessor;
import fr.unice.scale.latencyaware.controller.processing.SeparateArrivalRateClassicScalerProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junitpioneer.jupiter.SetEnvironmentVariable;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static fr.unice.scale.latencyaware.common.constant.CommonVariables.EXTERNAL_GROUP_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ClassicScalerProcessorTest {

    ClassicScalerProcessor scaler;
    Graph<ConsumerGroup> graph;
    Map<ConsumerGroup, CGMetaData> cgdatas;
    ConsumerGroup group1;
    ConsumerGroup group2;

    ConsumerGroup group3;

    @BeforeEach
    public void setup() {
        scaler = new SeparateArrivalRateClassicScalerProcessor();
        graph = new Graph<>();
        group1 = new MockConsumerGroup("group-1", 10, 1);
        group2 = new MockConsumerGroup("group-2", 10, 1);
        group3 = new MockConsumerGroup("group-3", 10, 1);
        graph.addVertex("group-1", group1);
        graph.addVertex("group-2", group2);
        graph.addVertex("group-3", group3);

        graph.addEdge("group-1", "group-2", .5);
        graph.addEdge("group-1", "group-3", .5);
        graph.addEdge("group-2", "group-3", 1.);

        cgdatas = new HashMap<>();
        CGMetaData data1 = new CGMetaData(group1, 0.2);
        for (PartitionMetaData pmd : data1.getPartitionsMetaData().values()) {
            pmd.setLag(1);
            pmd.setArrivalRate(Map.of(EXTERNAL_GROUP_NAME, 10.0));
            pmd.setProcessingTime(10);
            pmd.setProcessingCount(10);
        }
        CGMetaData data2 = new CGMetaData(group2, 0.2);
        for (PartitionMetaData pmd : data2.getPartitionsMetaData().values()) {
            pmd.setLag(1);
            pmd.setArrivalRate(Map.of("group-1", 10.0, EXTERNAL_GROUP_NAME, 10.0));
            pmd.setProcessingTime(10);
            pmd.setProcessingCount(10);
        }

        CGMetaData data3 = new CGMetaData(group3, 0.2);
        for (PartitionMetaData pmd : data3.getPartitionsMetaData().values()) {
            pmd.setLag(1);
            pmd.setArrivalRate(Map.of("group-1", 10.0, "group-2", 10.0));
            pmd.setProcessingTime(10);
            pmd.setProcessingCount(10);
        }
        cgdatas.put(group1, data1);
        cgdatas.put(group2, data2);
        cgdatas.put(group3, data3);

    }

    @Test
    @SetEnvironmentVariable(key = "TOPIC", value = "topic-test")
    public void propagatedParentArrivalRateTest() {
        scaler.propagateArrivalRate(graph, cgdatas);

        assertEquals(0, cgdatas.get(group1).getParentArrivalRate());
        assertEquals(50, cgdatas.get(group2).getParentArrivalRate());
        assertEquals(200, cgdatas.get(group3).getParentArrivalRate());
    }

    @Test
    @SetEnvironmentVariable(key = "TOPIC", value = "topic-test")
    public void arrivalRateCheckingTest() {
        scaler.propagateArrivalRate(graph, cgdatas);

        assertEquals(100, cgdatas.get(group1).getTotalExternalArrivalRate());
        assertEquals(0, cgdatas.get(group1).getParentArrivalRate());

        assertEquals(100, cgdatas.get(group2).getTotalExternalArrivalRate());
        assertEquals(50, cgdatas.get(group2).getParentArrivalRate());

        assertEquals(0, cgdatas.get(group3).getTotalExternalArrivalRate());
        assertEquals(200, cgdatas.get(group3).getParentArrivalRate());
    }
}
