package fr.unice.scale.latencyaware.controller;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import fr.unice.scale.latencyaware.producer.ArrivalServiceGrpc;
import fr.unice.scale.latencyaware.producer.ArrivalRequest;
import fr.unice.scale.latencyaware.producer.ArrivalResponse;



public class ArrivalProducer {
    private static final Logger log = LogManager.getLogger(ArrivalProducer.class);
    static ArrayList<Partition> topicpartitions;
    static double totalArrivalrate;

    static int numberpartitions = Integer.valueOf(System.getenv("NUMBER_PARTITIONS"));

    static {
        topicpartitions = new ArrayList<>();
        for (int i = 0; i < numberpartitions; i++) {
            topicpartitions.add(new Partition(i, 0, 0));
        }
    }

    public static void callForArrivals() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("arrivalservice", 5002)
            .usePlaintext()
            .build();
        ArrivalServiceGrpc.ArrivalServiceBlockingStub arrivalServiceBlockingStub = ArrivalServiceGrpc.newBlockingStub(managedChannel);

        try {

            log.info("number of partition set : {}", numberpartitions);
            log.info("Requesting arrival rate...");
            ArrivalRequest request = ArrivalRequest.newBuilder()
                .setArrivalrequest("Give me the arrival rate plz").build();
            ArrivalResponse reply = arrivalServiceBlockingStub.arrivalRate(request);

            totalArrivalrate = reply.getArrival();
            log.info("Arrival from the producer is {}", totalArrivalrate);

            double partitionArrival = totalArrivalrate / (numberpartitions * 1.0);
            log.info("Arrival into each partition is {}", partitionArrival);

            for (int i = 0; i < numberpartitions; i++) {
                topicpartitions.get(i).setArrivalRate(partitionArrival);
            }
        } catch (Exception e) {
            log.error("Error calling for arrivals: {}", e.getMessage(), e);
        } finally {
            managedChannel.shutdown();
        }
    }
}
