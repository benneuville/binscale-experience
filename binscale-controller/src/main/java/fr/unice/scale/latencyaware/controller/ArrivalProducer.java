package fr.unice.scale.latencyaware.controller;

import fr.unice.scale.latencyaware.controller.entity.ConsumerGroup;
import fr.unice.scale.latencyaware.controller.entity.Partition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static fr.unice.scale.latencyaware.controller.constant.Variables.NUMBER_PARTITIONS;


public class ArrivalProducer {
    private static final Logger log = LogManager.getLogger(ArrivalProducer.class);
    public static ArrayList<Partition> topicpartitions = IntStream.range(0, NUMBER_PARTITIONS)
            .collect(ArrayList::new, (list, i) -> list.add(new Partition(i)), ArrayList::addAll);
    public static double totalArrivalrate;

    public static void callForArrivals(ConsumerGroup g) {
//        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("arrivalservice",
//                        5002)
//                .usePlaintext()
//                .build();
//        ArrivalServiceGrpc.ArrivalServiceBlockingStub arrivalServiceBlockingStub =
//                ArrivalServiceGrpc.newBlockingStub(managedChannel);
//        ArrivalRequest request = ArrivalRequest.newBuilder()
//                .setArrivalrequest("Give me the arrival rate plz").build();
//        ArrivalResponse reply = arrivalServiceBlockingStub.consumptionRate(request);
//        log.info("Arrival from the producer is {}", reply);
//        totalArrivalrate = reply.getArrival();
//        double partitionArrival = reply.getArrival()/5.0;
//        log.info("Arrival into each partition is {}", partitionArrival);
////        g.setTotalArrivalRate(totalArrivalrate);
//        managedChannel.shutdown();
    }


    public static void callForConsumers() {
//        ManagedChannel managedChannel = ManagedChannelBuilder
//                .forAddress("rateservice", 5002)
//                .usePlaintext()
//                .build();
//        ArrivalServiceGrpc.ArrivalServiceBlockingStub rateServiceBlockingStub
//                = ArrivalServiceGrpc.newBlockingStub(managedChannel);
//        RateRequest request = RateRequest.newBuilder()
//                .setRaterequest("Give me the Assignment plz").build();
//        RateResponse reply = rateServiceBlockingStub
//                .consumptionRatee(request);
//        log.info("latency is {}", reply);
//        managedChannel.shutdown();
    }
}
