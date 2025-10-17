package fr.unice.scale.latencyaware.controller.server;

import fr.unice.scale.latencyaware.controller.*;
import fr.unice.scale.latencyaware.controller.bin_pack.BinPackLag;
import fr.unice.scale.latencyaware.controller.entity.Consumer;
import fr.unice.scale.latencyaware.controller.entity.Partition;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AssignmentService extends AssignmentServiceGrpc.AssignmentServiceImplBase {
    private static final Logger log = LogManager.getLogger(AssignmentService.class);
    @Override
    public void getAssignment(AssignmentRequest request, StreamObserver<AssignmentResponse> responseObserver) {
        if (BinPackLag.assignment.size() == 0) {
            List<ConsumerGrpc> assignmentReply = new ArrayList<>();
            for (int i = 0; i < ArrivalProducer.numberpartitions; i++) {
                List<PartitionGrpc> pgrpclist = new ArrayList<>();
                PartitionGrpc pgrpc = PartitionGrpc.newBuilder().setId(i).build();
                pgrpclist.add(pgrpc);
                ConsumerGrpc consg = ConsumerGrpc.newBuilder().setId(i)
                        .addAllAssignedPartitions(pgrpclist).build();
                assignmentReply.add(consg);
            }
            ////////////////////////////////////
            log.info("The assignment is {}", assignmentReply);
            responseObserver.onNext(AssignmentResponse.newBuilder().addAllConsumers(assignmentReply).build());
            responseObserver.onCompleted();
            log.info("Sent Assignment to client");
            return;
        }
        log.info(request.getRequest());
        //TODO Synchronize access to assignment
        List<Consumer> assignment = BinPackLag.assignment;
        log.info("The assignment is {}", assignment);
        List<ConsumerGrpc> assignmentReply = new ArrayList<>(assignment.size());
        for (Consumer cons : assignment) {
            List<PartitionGrpc> pgrpclist = new ArrayList<>();
            for (Partition p : cons.getAssignedPartitions()) {
                log.info("partition {} is assigned to consumer {}", p.getId(), cons.getId());
                PartitionGrpc pgrpc = PartitionGrpc.newBuilder().setId(p.getId()).build();
                pgrpclist.add(pgrpc);
            }
            ConsumerGrpc consg = ConsumerGrpc.newBuilder().setId(Integer.parseInt(cons.getId()))
                    .addAllAssignedPartitions(pgrpclist).build();
            assignmentReply.add(consg);
        }

         /*   for(ConsumerGrpc cons : assignmentReply){
                log.info("Consumer {} has the following partitions", cons.getId());
                for(PartitionGrpc part : cons.getAssignedPartitionsList()){
                    log.info("partition {}", part.getId());
                }
            }*/
        responseObserver.onNext(AssignmentResponse.newBuilder().addAllConsumers(assignmentReply).build());
        responseObserver.onCompleted();
        log.info("Sent Assignment to client");
    }
}