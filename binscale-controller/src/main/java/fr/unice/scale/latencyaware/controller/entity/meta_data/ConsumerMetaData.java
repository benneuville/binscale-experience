package fr.unice.scale.latencyaware.controller.entity.meta_data;

import fr.unice.scale.latencyaware.controller.entity.Consumer;

public class ConsumerMetaData {

    private double avgProcessingCapacity;

    private Consumer consumer;

    public ConsumerMetaData(Consumer consumer) {
        this.avgProcessingCapacity = 0;
        this.consumer = consumer;
    }

    public double getAvgProcessingCapacity() {
        return avgProcessingCapacity;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public String toString() {
        return "ConsumerMetaData{"+
                "consumer=" + consumer.getId() +
                ", avgProcessingCapacity=" + avgProcessingCapacity +
                "}";
    }
}
