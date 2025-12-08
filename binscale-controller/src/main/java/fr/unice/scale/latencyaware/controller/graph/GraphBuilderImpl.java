package fr.unice.scale.latencyaware.controller.graph;

import fr.unice.scale.latencyaware.common.config.KafkaConsumerConfig;
import fr.unice.scale.latencyaware.controller.entity.ConsumerGroup;
import fr.unice.scale.latencyaware.controller.entity.distribution.GraphDistributionConfig;
import fr.unice.scale.latencyaware.controller.entity.distribution.NodeDistribution;
import fr.unice.scale.latencyaware.controller.entity.graph.Graph;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

import static fr.unice.scale.latencyaware.common.constant.CommonVariables.STRING_DESERIALIZER;
import static fr.unice.scale.latencyaware.controller.constant.Variables.BOOTSTRAP_SERVERS;

public class GraphBuilderImpl implements GraphBuilder {
    @Override
    public Graph<ConsumerGroup> buildGraph(GraphDistributionConfig config) {
        Graph<ConsumerGroup> graph = new Graph<>();
        for (NodeDistribution node : config.getNodes()) {

            Properties props = KafkaConsumerConfig.createProperties(new KafkaConsumerConfig(BOOTSTRAP_SERVERS, node.getInputTopic(), node.getGroupId()));
            KafkaConsumerConfig.createProperties(new KafkaConsumerConfig(BOOTSTRAP_SERVERS, node.getInputTopic(), node.getGroupId()));
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                    STRING_DESERIALIZER);
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                    STRING_DESERIALIZER);

            graph.addVertex(node.getName(),
                    new ConsumerGroup(
                            node.getInputTopic(),
                            node.getMaxConsumptionRate(),
                            node.getWsla(),
                            node.getName(),
                            node.getGroupId(),
                            node.getPartitionNumber(),
                            new KafkaConsumer<>(props)
                    ));
        }
        config.getEdges().forEach((e) -> graph.addEdge(e.getFrom(), e.getTo(), e.getWeight()));

        return graph;
    }
}
