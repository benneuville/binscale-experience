package fr.unice.scale.latencyaware.controller;

public class Main {
    public static void main(String[] args) {
        ControllerService controllerService = new ControllerService();
        controllerService.init();
        controllerService.run();
    }

}