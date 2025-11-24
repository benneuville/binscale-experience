package fr.unice.scale.latencyaware.controller.entity;

import fr.unice.scale.latencyaware.common.config.KafkaConsumerConfig;
import fr.unice.scale.latencyaware.common.error.exception.NotFoundException;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static fr.unice.scale.latencyaware.common.constant.CommonVariables.STRING_DESERIALIZER;
import static fr.unice.scale.latencyaware.controller.constant.Variables.*;

public class ConsumerGroup implements NamedEntity {
    private static final Logger log = LogManager.getLogger(ConsumerGroup.class);
    double wsla;
    private String inputTopic;
    private String consumerName;
    private String kafkaGroupName;
    private double maxConsumptionRate;
    private List<Partition> topicPartitions;
    private Instant lastUpScaleDecision = Instant.now();
    private List<Consumer> assignment = new ArrayList<>();
    private KafkaConsumer<byte[], byte[]> metadataConsumer;

    public ConsumerGroup(String inputTopic, double maxConsumptionRate, double wsla, String consumerName, String groupName, int partitionNumber) {
        this(inputTopic, 1, maxConsumptionRate, wsla, consumerName, groupName, partitionNumber);
    }

    public ConsumerGroup(String inputTopic, Integer size, double maxConsumptionRate,
                         double wsla, String name, String groupName, int partitionNumber) {
        this.inputTopic = inputTopic;
        this.maxConsumptionRate = maxConsumptionRate;
        this.wsla = wsla;
        this.consumerName = name;
        this.kafkaGroupName = groupName;
        topicPartitions = IntStream.range(0, partitionNumber)
                .mapToObj(Partition::new).collect(Collectors.toList());

        Properties props = KafkaConsumerConfig.createProperties(new KafkaConsumerConfig(BOOTSTRAP_SERVERS, inputTopic, kafkaGroupName));
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                STRING_DESERIALIZER);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                STRING_DESERIALIZER);
        metadataConsumer = new KafkaConsumer<>(props);

        for (int i = 0; i < size; i++)
            assignment.add(new Consumer(String.valueOf(i), MU * wsla * .9, MU * .9));
        topicPartitions.forEach(assignment.get(0)::assignPartition);
    }

    @Override
    public String getName() {
        return consumerName;
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

    public double getMaxConsumptionRate() {
        return maxConsumptionRate;
    }

    public void setMaxConsumptionRate(double maxConsumptionRate) {
        this.maxConsumptionRate = maxConsumptionRate;
    }

    public Integer getSize() {
        return assignment.size();
    }

    public double getWsla() {
        return wsla;
    }

    public Instant getLastUpScaleDecision() {
        return lastUpScaleDecision;
    }

    public void setLastUpScaleDecision(Instant lastUpScaleDecision) {
        this.lastUpScaleDecision = lastUpScaleDecision;
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


}
