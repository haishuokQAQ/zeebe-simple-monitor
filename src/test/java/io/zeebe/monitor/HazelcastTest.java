package io.zeebe.monitor;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;
import io.zeebe.exporter.proto.Schema;
import io.zeebe.protocol.record.ValueType;

public class HazelcastTest {
    public static void main(String[] args) {
        ClientConfig clientConfig = new ClientConfig();
        //clientConfig.getGroupConfig().setName("test!");
        clientConfig.getNetworkConfig().addAddress("10.180.8.171:5701");
        HazelcastInstance hazelcast = HazelcastClient.newHazelcastClient(clientConfig);
        ITopic<Schema.DeploymentRecord> deploymentTopic = hazelcast.getTopic("zeebe-" + ValueType.DEPLOYMENT);
        //System.out.println(deploymentTopic.getLocalTopicStats().toJson());
        deploymentTopic.addMessageListener(record -> {
            System.out.println(record.toString());
        });
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
