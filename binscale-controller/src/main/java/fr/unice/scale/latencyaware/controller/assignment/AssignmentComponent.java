package fr.unice.scale.latencyaware.controller.assignment;

import fr.unice.scale.latencyaware.controller.entity.Consumer;
import fr.unice.scale.latencyaware.controller.entity.ConsumerGroup;
import fr.unice.scale.latencyaware.controller.entity.decision.ScaleDecision;
import fr.unice.scale.latencyaware.controller.entity.graph.Graph;
import fr.unice.scale.latencyaware.controller.utils.ConsumerConverter;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssignmentComponent {
    private final Logger log = LoggerFactory.getLogger(AssignmentComponent.class);

    public AssignmentComponent() {
    }

    public void assignScale(Graph<ConsumerGroup> graph, Map<ConsumerGroup, ScaleDecision> decisions) {
        if (decisions.isEmpty()) {
            log.info("No scaling decisions to apply");
            return;
        }
        Map<ConsumerGroup, List<Consumer>> assignments = new HashMap<>();
        for (Map.Entry<ConsumerGroup, ScaleDecision> entry : decisions.entrySet()) {
            log.info("Consumer group {} : got before assignment {}", entry.getKey().getKafkaGroupName(), entry.getKey().getAssignment());
            assignments.put(entry.getKey(), assign(graph, entry));
        }
        // Apply assignments
        graph.setAssignments(assignments);
    }

    private List<Consumer> assign(Graph<ConsumerGroup> graph, Map.Entry<ConsumerGroup, ScaleDecision> decisionEntry) {
        ScaleDecision decision = decisionEntry.getValue();
        ConsumerGroup group = graph.getVertex(decisionEntry.getKey()).getGroup();
        switch (decision.getAction()) {
            case UP:
            case DOWN:
                new Thread(() -> {
                    (new KubernetesClientBuilder().build()).apps().deployments().inNamespace("default").withName(group.getConsumerName()).scale(decision.getAssociations().size());
                    log.info("group {} scaled to {}", group.getKafkaGroupName(), decision.getAssociations());
                }).start();
                return ConsumerConverter.convertConsumers(decision.getAssociations());
            case REASS:
                group.getMetadataConsumer().enforceRebalance();
                log.info("group {} reassigned to {}", group.getKafkaGroupName(), decision.getAssociations());
                return ConsumerConverter.convertConsumers(decision.getAssociations());
            case NONE:
            default:
                log.info("No scaling action for group {}", group.getKafkaGroupName());
                return group.getAssignment();
        }
    }
}
