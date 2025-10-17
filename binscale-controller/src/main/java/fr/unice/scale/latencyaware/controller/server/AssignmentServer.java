package fr.unice.scale.latencyaware.controller.server;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



import java.io.IOException;

public class AssignmentServer implements Runnable {
    private final int port;
    private final Server server;
    private static final Logger log = LogManager.getLogger(AssignmentServer.class);
    public AssignmentServer(ServerBuilder<?> serverBuilder, int port) {
        this.port = port;
        this.server = serverBuilder.addService(new AssignmentService()).build();
    }
    public void start() throws IOException {
        log.info("Server Started");
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may has been reset by its JVM shutdown hook.
                log.info("*** shutting down gRPC server since JVM is shutting down");
                AssignmentServer.this.stop();
                log.info("*** server shut down");
            }
        });
    }
    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
    @Override
    public void run() {
        try {
            start();
            blockUntilShutdown();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}