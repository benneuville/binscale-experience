package fr.unice.scale.latencyaware.common.constant;

import fr.unice.scale.latencyaware.common.doc.EnvVar;
import fr.unice.scale.latencyaware.common.utils.EnvUtils;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class CommonVariables {
    @EnvVar(description = "Address of the Kafka bootstrap servers")
    public static final String KAFKA_BOOTSTRAP_SERVERS = EnvUtils.envOrDefault("KAFKA_BOOTSTRAP_SERVERS", "my-cluster-kafka-bootsrap:9092");
    @EnvVar(description = "Topic name. Example : 'testtopic1'")
    public static final String TOPIC = EnvUtils.envString("TOPIC");
    @EnvVar(description = "Group ID for the consumer. Example : 'testgroup1'")
    public static final String GROUP_ID = EnvUtils.envOrDefault("GROUP_ID", "");

    // Constants

    public final static String STRING_DESERIALIZER =
            "org.apache.kafka.common.serialization.StringDeserializer";

    public final static String STRING_SERIALIZER =
            "org.apache.kafka.common.serialization.StringSerializer";
    public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy'T'HH:mm:ss.SSS");

    static {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
}
