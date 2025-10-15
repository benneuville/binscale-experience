package fr.unice.scale.latencyaware.producer;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static fr.unice.scale.latencyaware.producer.constant.Variables.SERVER_PORT;

public class ServerThread implements Runnable {
    private static final Logger log = LogManager.getLogger(ServerThread.class);

    @Override
    public void run() {
        Server server = ServerBuilder.forPort(SERVER_PORT)
                .addService(ProtoReflectionService.newInstance())
                .addService(new ArrivalServiceImpl())
                .build();
        try {
            server.start();
        } catch (IOException e) {
            log.error(e.getMessage());
            server.shutdown();
            return;
        }

        log.info("grpc server started at port {}", SERVER_PORT);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutting Down");
            server.shutdown();
        }));

        try {
            server.awaitTermination();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            server.shutdown();
        }

    }
}
