package fr.unice.scale.latencyaware.controller.admin;

import fr.unice.scale.latencyaware.controller.entity.ConsumerGroup;
import fr.unice.scale.latencyaware.controller.entity.graph.Graph;
import fr.unice.scale.latencyaware.controller.entity.graph.Vertex;
import fr.unice.scale.latencyaware.controller.entity.metric.DoubleMetric;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListOffsetsResult;
import org.apache.kafka.clients.admin.OffsetSpec;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.ConsumerGroupState;
import org.apache.kafka.common.TopicPartition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static fr.unice.scale.latencyaware.common.constant.CommonVariables.DATE_FORMAT;
import static fr.unice.scale.latencyaware.controller.constant.Variables.BOOTSTRAP_SERVERS;
import static fr.unice.scale.latencyaware.controller.constant.Variables.NAMESPACE;

public class AdminComponent {

    private static Logger log = LogManager.getLogger(AdminComponent.class);

    private static KubernetesClient kubernetesClient;

    public AdminClient admin;

    public AdminComponent(KubernetesClient kubernetesClient) {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        this.admin = AdminClient.create(props);
        this.kubernetesClient = kubernetesClient;
    }

    public boolean waitConsumerGroupStable(ConsumerGroup consumerGroup) {
        try {
            return admin.describeConsumerGroups(
                            List.of(consumerGroup.getGroupName()))
                    .all().get()
                    .get(consumerGroup.getGroupName())
                    .state().equals(ConsumerGroupState.STABLE);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void waitAllConsumerGroupsStable(Graph<ConsumerGroup> graph) {
        waitAllConsumerGroupsStable(graph.topologicalSort().stream().map(Vertex::getGroup).collect(Collectors.toList()));
    }

    public void waitAllConsumerGroupsStable(List<ConsumerGroup> consumerGroups) {
        boolean allStable = false;

        while (!allStable) {
            try {
                log.info("Waiting consumers group to scale...");
                Thread.sleep(250);
                kubernetesClient.apps().deployments().inNamespace(NAMESPACE).withLabel("app", "latency").list()
                        .getItems().forEach(d ->
                                log.info(" - Deployment {} : {}/{}", d.getMetadata().getName(),
                                        d.getStatus().getReadyReplicas() != null ? d.getStatus().getReadyReplicas() : 0,
                                        d.getSpec().getReplicas())
                        );
                allStable = admin.describeConsumerGroups(
                                consumerGroups.stream().map(ConsumerGroup::getGroupName).collect(Collectors.toList()))
                        .all().get().values().stream().allMatch(cgDescription ->
                                cgDescription.state().equals(ConsumerGroupState.STABLE)
                        )
                        &&
                        kubernetesClient.apps().deployments().inNamespace(NAMESPACE).withLabel("app", "latency").list()
                                .getItems().stream().allMatch(d ->
                                        d.getStatus().getReadyReplicas() != null &&
                                                d.getStatus().getReadyReplicas().equals(d.getSpec().getReplicas())
                                );
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Map<Integer, DoubleMetric> collectLagByPartition(ConsumerGroup consumerGroup) {
        double totalLag = 0.0;
        Map<Integer, DoubleMetric> lagMetrics = new HashMap<>();
        try {

            Map<TopicPartition, OffsetAndMetadata> committedOffset = admin.listConsumerGroupOffsets(consumerGroup.getGroupName())
                    .partitionsToOffsetAndMetadata().get();
            Map<TopicPartition, OffsetSpec> requestLatestOffsets = new HashMap<>();
            for (int i = 0; i < consumerGroup.getTopicPartitions().size(); i++) {
                requestLatestOffsets.put(new TopicPartition(consumerGroup.getInputTopic(), i), OffsetSpec.latest());
            }
            Map<TopicPartition, ListOffsetsResult.ListOffsetsResultInfo> latestOffsets =
                    admin.listOffsets(requestLatestOffsets).all().get();

            for (int i = 0; i < consumerGroup.getTopicPartitions().size(); i++) {
                TopicPartition t = new TopicPartition(consumerGroup.getInputTopic(), i);
                Double latestOffset = latestOffsets.get(t) != null ? (double) latestOffsets.get(t).offset() : 0.0;
                Double committed = committedOffset.get(t) != null ? (double) committedOffset.get(t).offset() : 0.0;
                lagMetrics.put(i, new DoubleMetric(Instant.now().toEpochMilli(), String.valueOf(latestOffset - committed)));
                log.info("partition {} has lag {}", i, latestOffset - committed);
                totalLag += (latestOffset - committed);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String currentTime = DATE_FORMAT.format(new Date(System.currentTimeMillis()));
        log.info("total lag {} at {}", totalLag, currentTime);
        return lagMetrics;
    }

}
