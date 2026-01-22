package fr.unice.scale.latencyaware.controller.binpack;


import fr.unice.scale.latencyaware.controller.bin_pack.BinPack;
import fr.unice.scale.latencyaware.controller.constant.Action;
import fr.unice.scale.latencyaware.controller.entity.Consumer;
import fr.unice.scale.latencyaware.controller.entity.ConsumerGroup;
import fr.unice.scale.latencyaware.controller.entity.decision.ScaleDecision;
import fr.unice.scale.latencyaware.controller.entity.meta_data.CGMetaData;
import fr.unice.scale.latencyaware.controller.entity.meta_data.PartitionMetaData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class BinPackTest {

    private ConsumerGroup consumerGroup;

    private CGMetaData cgMetaData;

    @BeforeEach
    void setUp() {

        Consumer consumer = new Consumer("0");

        /** Max Processing Capacity : 100 events
         *  WSLA : .5 s
         *
         *
         */
        consumerGroup = new MockConsumerGroup("topic-test", 4, 1);

        consumerGroup.getTopicPartitions().forEach(consumer::assignPartition);

        consumerGroup.setAssignment(List.of(
                consumer
        ));

        consumerGroup.setNowLastUpScaleDecision();

        cgMetaData = new CGMetaData(consumerGroup, .5);

        consumerGroup.getTopicPartitions().forEach((p) -> {
                    cgMetaData.setPartitionMetaData(p, new PartitionMetaData(p, 2));
                }
        );

        cgMetaData.getPartitionsMetaData().forEach((p, pmd) -> {
            pmd.setLatency(100);
            pmd.setArrivalRate(10);
            pmd.setLag(10);
            pmd.setProcessingCount(100);
            pmd.setProcessingTime(100);
        });
    }

    @Test
    public void testUpscaleLag() {
        cgMetaData.getPartitionsMetaData().forEach((p, pmd) -> {
            pmd.setLag(10);
        });
        ScaleDecision decision = BinPack.scaleDecisionEventConsumerWithLag(consumerGroup, cgMetaData);

        assertNotNull(decision);
        assertEquals(Action.UP, decision.getAction(), "Expected UP decision");
        assertEquals(2, decision.getAssociations().size(), "Expected 2 ");
    }

    @Test
    public void testUpscaleArrivalRate() {
        cgMetaData.getPartitionsMetaData().forEach((p, pmd) -> {
            pmd.setLag(5);
            pmd.setArrivalRate(20);
        });
        ScaleDecision decision = BinPack.scaleDecisionEventConsumerWithLag(consumerGroup, cgMetaData);

        assertNotNull(decision);
        assertEquals(Action.UP, decision.getAction(), "Expected UP decision");
        assertEquals(2, decision.getAssociations().size(), "Expected 2");
    }

    @Test
    public void testDownscale() {
        Consumer consumer1 = new Consumer("0");
        Consumer consumer2 = new Consumer("1");
        consumer1.assignPartition(consumerGroup.getTopicPartitions().get(0));
        consumer1.assignPartition(consumerGroup.getTopicPartitions().get(1));
        consumer2.assignPartition(consumerGroup.getTopicPartitions().get(2));
        consumer2.assignPartition(consumerGroup.getTopicPartitions().get(3));

        consumerGroup.setAssignment(List.of(
                consumer1, consumer2
        ));
        cgMetaData.getPartitionsMetaData().forEach((p, pmd) -> {
            pmd.setLag(1);
            pmd.setArrivalRate(5);
            pmd.setProcessingCount(1);
            pmd.setProcessingTime(1);
        });
        ScaleDecision decision = BinPack.scaleDecisionEventConsumerWithLag(consumerGroup, cgMetaData);

        assertNotNull(decision);
        assertEquals(Action.DOWN, decision.getAction(), "Expected DOWN decision");
        assertEquals(1, decision.getAssociations().size(), "Expected 1");
    }

    @Test
    void testReassignment() {
        Consumer consumer1 = new Consumer("0");
        Consumer consumer2 = new Consumer("1");
        consumer1.assignPartition(consumerGroup.getTopicPartitions().get(0));
        consumer1.assignPartition(consumerGroup.getTopicPartitions().get(1));
        consumer2.assignPartition(consumerGroup.getTopicPartitions().get(2));
        consumer2.assignPartition(consumerGroup.getTopicPartitions().get(3));

        consumerGroup.setAssignment(List.of(
                consumer1, consumer2
        ));
        cgMetaData.getPartitionsMetaData().forEach((p, pmd) -> {
            pmd.setLag(5);
            pmd.setArrivalRate(10);
            pmd.setProcessingCount(1);
            pmd.setProcessingTime(1);
        });
        cgMetaData.getPartitionMetaData(0).setArrivalRate(80);
        cgMetaData.getPartitionMetaData(0).setLag(24);

        ScaleDecision decision = BinPack.scaleDecisionEventConsumerWithLag(consumerGroup, cgMetaData);

        assertNotNull(decision);
        assertEquals(Action.REASS, decision.getAction(), "Expected REASS decision");
        assertEquals(2, decision.getAssociations().size(), "Expected 2");
    }

    @Test
    void testNone() {
        Consumer consumer1 = new Consumer("0");
        Consumer consumer2 = new Consumer("1");
        consumer1.assignPartition(consumerGroup.getTopicPartitions().get(0));
        consumer1.assignPartition(consumerGroup.getTopicPartitions().get(1));
        consumer2.assignPartition(consumerGroup.getTopicPartitions().get(2));
        consumer2.assignPartition(consumerGroup.getTopicPartitions().get(3));

        consumerGroup.setAssignment(List.of(
                consumer1, consumer2
        ));
        cgMetaData.getPartitionsMetaData().forEach((p, pmd) -> {
            pmd.setLag(5);
            pmd.setArrivalRate(20);
            pmd.setProcessingCount(1);
            pmd.setProcessingTime(1);
        });
        ScaleDecision decision = BinPack.scaleDecisionEventConsumerWithLag(consumerGroup, cgMetaData);

        assertNotNull(decision);
        assertEquals(Action.NONE, decision.getAction(), "Expected NONE decision");

    }
}
