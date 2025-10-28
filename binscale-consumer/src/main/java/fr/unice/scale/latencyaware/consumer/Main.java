package fr.unice.scale.latencyaware.consumer;

public class Main {
    public static void main(String[] args) {
        BinscaleService service = new BinscaleService();
        service.init();
        service.run();
    }
}
