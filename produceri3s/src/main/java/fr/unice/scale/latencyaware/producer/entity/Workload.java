package fr.unice.scale.latencyaware.producer.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static fr.unice.scale.latencyaware.producer.constant.Variables.INPUT_WORKLOAD;

public class Workload {
    private final static String CSV_SPLIT_BY = ",";

    private static ArrayList<Double> datay = new ArrayList<Double>();
    private static ArrayList<Double> datax = new ArrayList<Double>();

    public Workload() throws IOException {
        this.loadWorkload();
    }

    public ArrayList<Double> getDatax() {
        return datax;
    }

    public ArrayList<Double> getDatay() {
        return datay;
    }

    private void loadWorkload() throws IOException {
        ClassLoader CLDR = this.getClass().getClassLoader();
        InputStream inputStream = CLDR.getResourceAsStream(INPUT_WORKLOAD);

        List<String> out = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                out.add(line);
            }
        }
        for (String line : out) {
            String[] workFields = line.split(CSV_SPLIT_BY);
            //inputXPointValue = Double.parseDouble(workFields[0]);
            double targetXPointValue = Double.parseDouble(workFields[1]);
            datax.add(0d);
            datay.add(targetXPointValue);
        }
    }
}



