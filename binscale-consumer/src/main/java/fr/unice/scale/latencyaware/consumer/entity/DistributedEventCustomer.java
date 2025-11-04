package fr.unice.scale.latencyaware.consumer.entity;

import fr.unice.scale.latencyaware.common.entity.EventCustomer;

import java.util.ArrayList;
import java.util.List;

public class DistributedEventCustomer {
    private ProducerTopicDistribution targetTopic;
    private List<EventCustomer> events;

    public DistributedEventCustomer() {
    }

    public DistributedEventCustomer(ProducerTopicDistribution targetTopic) {
        this.targetTopic = targetTopic;
        this.events = new ArrayList<>();
    }

    public DistributedEventCustomer(ProducerTopicDistribution targetTopic, List<EventCustomer> events) {
        this.targetTopic = targetTopic;
        this.events = events;
    }

    public ProducerTopicDistribution getTargetTopic() {
        return targetTopic;
    }

    public void setTargetTopic(ProducerTopicDistribution targetTopic) {
        this.targetTopic = targetTopic;
    }

    public List<EventCustomer> getEvents() {
        return events;
    }

    public void setEvents(List<EventCustomer> events) {
        this.events = events;
    }

    public void addEvent(EventCustomer event) {
        this.events.add(event);
    }

    public void addAllEvents(List<EventCustomer> events) {
        this.events.addAll(events);
    }

    @Override
    public String toString() {
        return "DistributedEventCustomer{" +
                "targetTopic=" + targetTopic +
                ", events_count=" + events.size() +
                '}';
    }
}
