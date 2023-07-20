package com.masq.kafkademo;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.Uuid;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class TopicTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopicTest.class);

    KafkaAdminClient client = null;

    @BeforeEach
    public void before() {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "centos0:9092,centos1:9092,centos2:9092");
        client = (KafkaAdminClient) KafkaAdminClient.create(props);
    }

    // 查看Topic列表
    @Test
    public void listTopic() {
        ListTopicsResult result = client.listTopics();
        try {
            Set<String> names = result.names().get();
            names.forEach(LOGGER::info);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    // 查看Topic详情
    @Test
    public void getDescribe() {
        DescribeTopicsResult result = client.describeTopics(Arrays.asList("topic01", "topic02"));
        try {
            Map<String, TopicDescription> map = result.allTopicNames().get();
            map.values().forEach(item -> LOGGER.info(item.toString()));
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    // 创建Topic
    @Test
    public void testCreateTopic() {
        NewTopic topic = new NewTopic("topic03", 3, (short) 3 );
        // 异步创建
        CreateTopicsResult result = client.createTopics(Collections.singletonList(topic));

    }


    // 删除Topic
    @Test
    public void testDeleteTopic() {
        client.deleteTopics(Collections.singletonList("topic03"));
    }

    @AfterEach
    public void after()  {
        client.close();
    }
}
