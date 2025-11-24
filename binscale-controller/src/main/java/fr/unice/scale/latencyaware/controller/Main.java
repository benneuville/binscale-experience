package fr.unice.scale.latencyaware.controller;

import fr.unice.scale.latencyaware.controller.bin_pack.BinPackLag;
import fr.unice.scale.latencyaware.controller.bin_pack.BinPackState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutionException;


public class Main {
    public static void main(String[] args) {
        ControllerService controllerService = new ControllerService();
        controllerService.init();
        controllerService.run();
    }

}