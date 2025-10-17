package fr.unice.scale.latencyaware.controller.bin_pack;

import fr.unice.scale.latencyaware.controller.ArrivalProducer;
import fr.unice.scale.latencyaware.controller.entity.Partition;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.TopicPartition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static fr.unice.scale.latencyaware.controller.constant.Variables.*;

public class Lag {
    private static final Logger log = LogManager.getLogger(Lag.class);
    public static AdminClient admin = null;
    static Map<TopicPartition, OffsetAndMetadata> committedOffsets;
    static long totalLag;
    static ArrayList<Partition> partitions = new ArrayList<>();


    static Map<String, ConsumerGroupDescription> consumerGroupDescriptionMap;


    public static void readEnvAndCrateAdminClient() {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        admin = AdminClient.create(props);
        for (int i = 0; i < NUMBER_PARTITIONS; i++) {
            //ArrivalProducer.topicpartitions.get(i).setLag(0L);
            Partition p = new Partition(i, 0L, 0.0);
            partitions.add(p);
        }
    }


    public static void getCommittedLatestOffsetsAndLag() throws ExecutionException, InterruptedException {
        committedOffsets = admin.listConsumerGroupOffsets(GROUP_ID)
                .partitionsToOffsetAndMetadata().get();
        Map<TopicPartition, OffsetSpec> requestLatestOffsets = new HashMap<>();
        for (int i = 0; i < NUMBER_PARTITIONS; i++) {
            requestLatestOffsets.put(new TopicPartition(TOPIC, i), OffsetSpec.latest());
        }
        Map<TopicPartition, ListOffsetsResult.ListOffsetsResultInfo> latestOffsets =
                admin.listOffsets(requestLatestOffsets).all().get();
        totalLag = 0L;

        for (int i = 0; i < NUMBER_PARTITIONS; i++) {
            TopicPartition t = new TopicPartition(TOPIC, i);
            Long latestOffset = latestOffsets.get(t) != null ? latestOffsets.get(t).offset() : 0L;
            Long committedOffset = committedOffsets.get(t) != null ? committedOffsets.get(t).offset() : 0L;
            partitions.get(i).setLag(latestOffset - committedOffset);
            ArrivalProducer.topicpartitions.get(i).setLag(latestOffset - committedOffset);
            totalLag += partitions.get(i).getLag();
            log.info("partition {} has lag {}", i, partitions.get(i).getLag());
        }
        // Get the current date and time in the specified format
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy'T'HH:mm:ss.SSSSSS");
        String currentTime = sdf.format(new Date(System.currentTimeMillis()));

        log.info("total lag {} at {}", totalLag, currentTime);
    }

    public static int queryConsumerGroup() throws ExecutionException, InterruptedException {
        DescribeConsumerGroupsResult describeConsumerGroupsResult =
                admin.describeConsumerGroups(Collections.singletonList(GROUP_ID));
        KafkaFuture<Map<String, ConsumerGroupDescription>> futureOfDescribeConsumerGroupsResult =
                describeConsumerGroupsResult.all();

        consumerGroupDescriptionMap = futureOfDescribeConsumerGroupsResult.get();


        int members = consumerGroupDescriptionMap.get(GROUP_ID).members().size();

        log.info("consumers nb as per kafka {}", members);

        return members;


    }

}
