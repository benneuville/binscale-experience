package fr.unice.scale.latencyaware.producer.workload;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractWorkload {

    public static float ArrivalRate;

    public abstract void startWorkload() throws IOException, URISyntaxException, InterruptedException;
}
