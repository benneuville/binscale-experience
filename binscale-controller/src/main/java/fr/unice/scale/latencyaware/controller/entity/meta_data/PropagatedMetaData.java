package fr.unice.scale.latencyaware.controller.entity.meta_data;

import fr.unice.scale.latencyaware.controller.entity.ConsumerGroup;

public class PropagatedMetaData {

    public static final PropagatedMetaData DEFAULT_PROPAGATED_MD = new PropagatedMetaData(null);
    private ConsumerGroup parentConsumerGroup;
    private double arrivalRate;
    private double lag;

    public PropagatedMetaData(ConsumerGroup parentConsumerGroup) {
        this.parentConsumerGroup = parentConsumerGroup;
        this.arrivalRate = 0.0;
        this.lag = 0.0;
    }

    public PropagatedMetaData(ConsumerGroup parentConsumerGroup, double arrivalRate, double lag) {
        this.parentConsumerGroup = parentConsumerGroup;
        this.arrivalRate = arrivalRate;
        this.lag = lag;
    }

    public ConsumerGroup getParentConsumerGroup() {
        return parentConsumerGroup;
    }


    public void setParentConsumerGroup(ConsumerGroup parentConsumerGroup) {
        this.parentConsumerGroup = parentConsumerGroup;
    }

    public double getArrivalRate() {
        return arrivalRate;
    }

    public void setArrivalRate(double arrivalRate) {
        this.arrivalRate = arrivalRate;
    }

    public double getLag() {
        return lag;
    }

    public void setLag(double lag) {
        this.lag = lag;
    }

    @Override
    public String toString() {
        return "PropagatedMetaData{" +
                "parentConsumerGroup=" + (parentConsumerGroup != null ? parentConsumerGroup.getConsumerName() : "null") +
                ", arrivalRate=" + arrivalRate +
                ", lag=" + lag +
                '}';
    }
}
