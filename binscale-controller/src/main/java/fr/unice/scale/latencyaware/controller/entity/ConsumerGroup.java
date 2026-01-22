package fr.unice.scale.latencyaware.controller.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.unice.scale.latencyaware.common.error.exception.NotFoundException;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static fr.unice.scale.latencyaware.common.constant.CommonVariables.DATE_FORMAT;
import static fr.unice.scale.latencyaware.controller.constant.Variables.FDOWN;
import static fr.unice.scale.latencyaware.controller.constant.Variables.FUP;

public class ConsumerGroup implements NamedEntity {
    // WSLA in seconds
    double wsla;
    private String inputTopic;
    private String consumerName;
    private String kafkaGroupName;
    // MU
    private double maxDefinedProcessingRate;
    private List<Partition> topicPartitions;
    private String lastUpScaleDecision;
    private List<Consumer> assignment = new ArrayList<>();
    @JsonIgnore
    private KafkaConsumer<byte[], byte[]> metadataConsumer;

    // test constructor only
    ConsumerGroup() {
    }

    public ConsumerGroup(String inputTopic, double maxDefinedProcessingRate, double wsla, String consumerName, String groupName, int partitionNumber, KafkaConsumer<byte[], byte[]> kafkaConsumer) {
        this(inputTopic, 1, maxDefinedProcessingRate, wsla, consumerName, groupName, partitionNumber, kafkaConsumer);
    }

    public ConsumerGroup(String inputTopic, Integer size, double maxDefinedProcessingRate,
                         double wsla, String name, String groupName, int partitionNumber, KafkaConsumer<byte[], byte[]> kafkaConsumer) {
        this.inputTopic = inputTopic;
        this.maxDefinedProcessingRate = maxDefinedProcessingRate;
        this.wsla = wsla;
        this.consumerName = name;
        this.kafkaGroupName = groupName;
        this.lastUpScaleDecision = "N/A";
        topicPartitions = IntStream.range(0, partitionNumber)
                .mapToObj(Partition::new).collect(Collectors.toList());
        metadataConsumer = kafkaConsumer;

        for (int i = 0; i < size; i++)
            assignment.add(new Consumer(String.valueOf(i)));
        topicPartitions.forEach(assignment.get(0)::assignPartition);
    }

    @Override
    public String getName() {
        return consumerName;
    }

    @Override
    public String getGroupName() {
        return kafkaGroupName;
    }

    public List<Consumer> getAssignment() {
        return assignment;
    }

    public void setAssignment(List<Consumer> assignment) {
        this.assignment = assignment;
    }

    public KafkaConsumer<byte[], byte[]> getMetadataConsumer() {
        return metadataConsumer;
    }

    public void setMetadataConsumer(KafkaConsumer<byte[], byte[]> metadataConsumer) {
        this.metadataConsumer = metadataConsumer;
    }

    public String getKafkaGroupName() {
        return kafkaGroupName;
    }

    public void setKafkaGroupName(String kafkaGroupName) {
        this.kafkaGroupName = kafkaGroupName;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public double getMaxDefinedProcessingRate() {
        return maxDefinedProcessingRate;
    }

    public void setMaxDefinedProcessingRate(double maxDefinedProcessingRate) {
        this.maxDefinedProcessingRate = maxDefinedProcessingRate;
    }

    public double getWsla() {
        return wsla;
    }

    public String getLastUpScaleDecision() {
        return lastUpScaleDecision;
    }

    public void setNowLastUpScaleDecision() {
        this.lastUpScaleDecision = DATE_FORMAT.format(System.currentTimeMillis());
    }

    public String getInputTopic() {
        return inputTopic;
    }

    public List<Partition> getTopicPartitions() {
        return topicPartitions;
    }

    public Partition getTopicPartitionById(int id) {
        return topicPartitions.stream().filter(p -> p.getId() == id).findFirst().orElseThrow(() -> new NotFoundException("Partition with id " + id + " not found"));
    }

    public double getFup() {
        return FUP;
    }

    public double getFdown() {
        return FDOWN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsumerGroup that = (ConsumerGroup) o;
        return this.kafkaGroupName.equals(that.kafkaGroupName);
    }

    @Override
    public int hashCode() {
        return kafkaGroupName.hashCode();
    }

    @Override
    public String toString() {
        return "ConsumerGroup{" + kafkaGroupName + "}";
    }


}
