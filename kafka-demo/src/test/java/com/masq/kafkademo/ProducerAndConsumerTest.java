package com.masq.kafkademo;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Pattern;

public class ProducerAndConsumerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerAndConsumerTest.class);

    KafkaAdminClient client = null;

    @Test
    public void testProduce() {
        // 生产者连接参数
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "centos0:9092,centos1:9092,centos2:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        for (int i = 0; i < 10; i++) {
            String key = "key-" + i;
            String value = "value-" + i;
            ProducerRecord<String, String> record = new ProducerRecord<String, String>("topic01", key, value);
            // 发送消息给服务器
            producer.send(record);
        }

        producer.close();
    }
    
    @Test
    public void consumerTest() {
        // 消费者连接参数
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "centos0:9092,centos1:9092,centos2:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "g1");

        // 建立连接
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // 订阅相关Topic
        consumer.subscribe(Pattern.compile("^topic.*"));

        while(true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
            if (!records.isEmpty()) {
                Iterator<ConsumerRecord<String, String>> recordIterator = records.iterator();
                while(recordIterator.hasNext()) {
                    ConsumerRecord<String, String> record = recordIterator.next();
                    LOGGER.info(record.toString());
                }
            }
        }
    }



//    @BeforeEach
//    public void before() {
//        Properties props = new Properties();
//        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "centos0:9092,centos1:9092,centos2:9092");
//        client = (KafkaAdminClient) KafkaAdminClient.create(props);
//    }
//
//    @AfterEach
//    public void after() {
//        client.close();
//    }
}
