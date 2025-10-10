package fr.unice.scale.latencyaware.producer.entity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Workload {
    // private static final Logger log = LogManager.getLogger(KafkaProducerConfig.class);
    private final static String CSV_SPLIT_BY = ",";

    private static ArrayList<Double> datay = new ArrayList<Double>();
    private static ArrayList<Double> datax = new ArrayList<Double>();
    public Workload() throws IOException, URISyntaxException {
        this.loadWorkload();
    }
    public ArrayList<Double> getDatax() {
        return datax;
    }
    public ArrayList<Double> getDatay() {
        return datay;
    }

    private void loadWorkload() throws IOException, URISyntaxException {
        ClassLoader CLDR = this.getClass().getClassLoader();
        InputStream inputStream = CLDR.getResourceAsStream("defaultArrivalRatesm.csv");

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



