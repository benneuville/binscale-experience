package fr.unice.scale.latencyaware.controller;

import fr.unice.scale.latencyaware.controller.bin_pack.BinPackLag;
import fr.unice.scale.latencyaware.controller.bin_pack.BinPackState;
import fr.unice.scale.latencyaware.controller.bin_pack.Lag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutionException;

import static fr.unice.scale.latencyaware.controller.constant.Variables.DI;


public class Main {

    private static final Logger log = LogManager.getLogger(Main.class);
    static BinPackState bps;
    static BinPackLag bpl;


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        initialize();
    }//


    private static void initialize() throws InterruptedException, ExecutionException {
        bps = new BinPackState();
        bpl = new BinPackLag();
        Lag.readEnvAndCrateAdminClient();
        log.info("Warming 15 seconds.");
        Thread.sleep(15 * 1000);
        while (true) {
            log.info("Querying Prometheus");
            ArrivalProducer.callForArrivals();
            Lag.getCommittedLatestOffsetsAndLag();
            log.info("--------------------");
            log.info("--------------------");


            //scaleLogic();
            scaleLogicTail();
            // Convert 'di' from milliseconds to seconds for logging purposes
            double diInSeconds = DI / 1000;
            log.info("Sleeping for {} seconds", diInSeconds);
            log.info("******************************************" + Thread.currentThread().getId());
            log.info("******************************************");
            Thread.sleep((DI).longValue());
        }
    }

    private static void scaleLogicTail() throws InterruptedException, ExecutionException {
        if (Lag.queryConsumerGroup() != BinPackState.size) {
            log.info("no action, previous action is not seen yet");
            return;
        }
        BinPackState.scaleAsPerBinPack();
        if (BinPackState.action.isAction()) {
            BinPackLag.scaleAsPerBinPack();
        }
    }


}