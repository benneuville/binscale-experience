package fr.unice.scale.latencyaware.exporter;

public class Main {

    public static void main(String[] args) {
        ExporterService exporterService = new ExporterService();
        exporterService.start();
    }
}
