package fr.unice.scale.latencyaware.controller.processing;

import fr.unice.scale.latencyaware.controller.entity.ConsumerGroup;
import fr.unice.scale.latencyaware.controller.entity.decision.ScaleDecision;
import fr.unice.scale.latencyaware.controller.entity.graph.Graph;
import fr.unice.scale.latencyaware.controller.entity.meta_data.CGMetaData;

import java.util.Map;

public interface ScalerProcessor {
    public Map<ConsumerGroup, ScaleDecision> process(Graph<ConsumerGroup> graph, Map<ConsumerGroup, CGMetaData> cgdatas);
}
