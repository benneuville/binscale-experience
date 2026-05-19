package fr.unice.scale.latencyaware.controller.entity.meta_data;

import fr.unice.scale.latencyaware.controller.entity.Consumer;

public class ConsumerMetaData {

    private double dynamicProcessingCapacity;

    private Consumer consumer;

    public ConsumerMetaData(Consumer consumer, double dynamicProcessingCapacity) {
        this.dynamicProcessingCapacity = dynamicProcessingCapacity;
        this.consumer = consumer;
    }

    public double getDynamicProcessingCapacity() {
        return dynamicProcessingCapacity;
    }

    public void setDynamicProcessingCapacity(double dynamicProcessingCapacity) {
        this.dynamicProcessingCapacity = dynamicProcessingCapacity;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public String toString() {
        return "ConsumerMetaData{" +
                "consumer=" + consumer.getId() +
                ", avgProcessingCapacity=" + dynamicProcessingCapacity +
                "}";
    }
}
