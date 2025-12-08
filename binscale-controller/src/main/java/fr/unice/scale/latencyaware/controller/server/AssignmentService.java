package fr.unice.scale.latencyaware.controller.server;

import fr.unice.scale.latencyaware.controller.*;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AssignmentService extends AssignmentServiceGrpc.AssignmentServiceImplBase {
    private static final Logger log = LogManager.getLogger(AssignmentService.class);

    @Override
    public void getAssignment(AssignmentRequest request, StreamObserver<AssignmentResponse> responseObserver) {
        log.info("Received Assignment Request from client {}", request.getId());
        List<ConsumerGrpc> assignment = ControllerService.graph.getVertices().stream().map(
                vertex -> {
                    List<PartitionGrpc> pgrpclist = new ArrayList<>();
                    ConsumerGrpc.Builder consgbuilder = ConsumerGrpc.newBuilder();
                    vertex.getGroup().getAssignment().forEach(
                            cons -> {
                                cons.getAssignedPartitions().forEach(
                                        p -> {
                                            log.info("partition {} is assigned to consumer {}", p.getId(), cons.getId());
                                            PartitionGrpc pgrpc = PartitionGrpc.newBuilder().setId(p.getId()).build();
                                            pgrpclist.add(pgrpc);
                                        }
                                );
                                consgbuilder.setId(Integer.parseInt(cons.getId()));
                            }
                    );
                    return consgbuilder.addAllAssignedPartitions(pgrpclist).build();
                }
        ).collect(Collectors.toList());
        responseObserver.onNext(AssignmentResponse.newBuilder().addAllConsumers(assignment).build());
        responseObserver.onCompleted();
        log.info("Sent Assignment to client");


//        log.info(request.getRequest());
//        //TODO Synchronize access to assignment
//        List<Consumer> assignment = ControllerService.graph.getVertex(0).getConsumerGroup().getCurrentAssignment();
//        log.info("The assignment is {}", assignment);
//        List<ConsumerGrpc> assignmentReply = new ArrayList<>(assignment.size());
//        for (Consumer cons : assignment) {
//            List<PartitionGrpc> pgrpclist = new ArrayList<>();
//            for (Partition p : cons.getAssignedPartitions()) {
//                log.info("partition {} is assigned to consumer {}", p.getId(), cons.getId());
//                PartitionGrpc pgrpc = PartitionGrpc.newBuilder().setId(p.getId()).build();
//                pgrpclist.add(pgrpc);
//            }
//            ConsumerGrpc consg = ConsumerGrpc.newBuilder().setId(Integer.parseInt(cons.getId()))
//                    .addAllAssignedPartitions(pgrpclist).build();
//            assignmentReply.add(consg);
//        }
//        responseObserver.onNext(AssignmentResponse.newBuilder().addAllConsumers(assignmentReply).build());
//        responseObserver.onCompleted();
//        log.info("Sent Assignment to client");
    }
}