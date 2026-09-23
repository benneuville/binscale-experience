package fr.unice.scale.latencyaware.exporter.admin;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static fr.unice.scale.latencyaware.common.constant.CommonVariables.KAFKA_BOOTSTRAP_SERVERS;

public class AdminComponent {

    private static Logger log = LogManager.getLogger(AdminComponent.class);
    private static ListTopicsOptions listTopicOptions = new ListTopicsOptions().listInternal(false);

    private AdminClient admin;

    public AdminComponent() {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVERS);
        this.admin = AdminClient.create(props);
    }

    public Set<String> discoverTopics() throws ExecutionException, InterruptedException {
        ListTopicsResult topicsResult = admin.listTopics(listTopicOptions);
        return topicsResult.names().get();
    }

    public Set<String> discoverNewTopics(Set<String> alreadyKnownTopics) throws ExecutionException, InterruptedException {
        Set<String> res = discoverTopics();
        res.removeAll(alreadyKnownTopics);
        return res;
    }

}
