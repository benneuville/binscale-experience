package fr.unice.scale.latencyaware.consumer.entity;

public class ProducerTopicDistribution {
    private final String topicName;
    private final float distributionRatio;

    public ProducerTopicDistribution(String topicName, float distributionRatio) {
        this.topicName = topicName;
        this.distributionRatio = distributionRatio;
    }

    public String getTopicName() {
        return topicName;
    }

    public float getDistributionRatio() {
        return distributionRatio;
    }
}
