package fr.unice.scale.latencyaware.controller.binpack;


import fr.unice.scale.latencyaware.controller.entity.ConsumerGroup;
import fr.unice.scale.latencyaware.controller.entity.graph.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class GraphTest {
    Graph<ConsumerGroup> graph;

    @BeforeEach
    public void setUp() {
        graph = new Graph<>();
    }

    @Test
    public void testAcyclicArbitraryGraph() {
        graph.addVertex("latency1", new MockConsumerGroup("latency-group-1", 1, 1));
        graph.addVertex("latency2", new MockConsumerGroup("latency-group-2", 1, 1));
        graph.addVertex("latency3", new MockConsumerGroup("latency-group-3", 1, 1));
        graph.addVertex("latency4", new MockConsumerGroup("latency-group-4", 1, 1));
        graph.addVertex("latency5", new MockConsumerGroup("latency-group-5", 1, 1));
        graph.addVertex("latency6", new MockConsumerGroup("latency-group-6", 1, 1));

        graph.addEdge("latency1", "latency2", 1.);
        graph.addEdge("latency1", "latency3", 1.);
        graph.addEdge("latency2", "latency6", 1.);
        graph.addEdge("latency2", "latency5", 1.);
        graph.addEdge("latency3", "latency5", 1.);
        graph.addEdge("latency3", "latency4", 1.);
        graph.addEdge("latency4", "latency5", 1.);

        assertDoesNotThrow(() -> {
            graph.topologicalSort();
        });

    }
}
