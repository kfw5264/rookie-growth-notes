package com.masq.kafkademo;

import com.masq.kafkademo.interceptor.MyInterceptor;
import com.masq.kafkademo.model.Person;
import com.masq.kafkademo.serialize.MyDeserializer;
import com.masq.kafkademo.serialize.MySerializer;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.Duration;
import java.util.*;
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
        // 配置拦截器
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, MyInterceptor.class.getName());
        KafkaProducer<String, Serializable> producer = new KafkaProducer<>(props);

        for (int i = 0; i < 10; i++) {
            String key = "key-" + i;
            String value = "value-" + i;
            ProducerRecord<String, Serializable> record = new ProducerRecord<String, Serializable>("topic01", key, value);
            // 发送消息给服务器
            producer.send(record);
        }

        producer.close();
    }

    @Test
    public void testProducerPerson() {
        // 生产者连接参数
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "centos0:9092,centos1:9092,centos2:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MySerializer.class.getName());
        KafkaProducer<String, Serializable> producer = new KafkaProducer<>(props);

        for (int i = 0; i < 10; i++) {
            String key = "key-" + i;
            Person person = new Person("name" + i, i, new Date());
            ProducerRecord<String, Serializable> record = new ProducerRecord<String, Serializable>("topic02", key, person);
            // 发送消息给服务器
            producer.send(record);
        }

        producer.close();
    }

    @Test
    public void consumerPersonTest() {
        // 消费者连接参数
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "centos0:9092,centos1:9092,centos2:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, MyDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "g1");

        // 建立连接
        KafkaConsumer<String, Serializable> consumer = new KafkaConsumer<>(props);

        // 订阅相关Topic
        consumer.subscribe(Collections.singletonList("topic02"));

        while(true) {
            ConsumerRecords<String, Serializable> records = consumer.poll(Duration.ofSeconds(1));
            if (!records.isEmpty()) {
                Iterator<ConsumerRecord<String, Serializable>> recordIterator = records.iterator();
                while(recordIterator.hasNext()) {
                    ConsumerRecord<String, Serializable> record = recordIterator.next();
                    LOGGER.info(record.toString());
                }
            }
        }
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
        consumer.subscribe(Collections.singletonList("topic01"));

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

    // 手动指定消费的分区以及偏移量，不需要组管理特性，每个实例相互独立
    @Test
    public void consumerAssignTest() {
        // 消费者连接参数
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "centos0:9092,centos1:9092,centos2:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "g1");

        // 建立连接
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);


//        consumer.subscribe(Pattern.compile("^topic.*"));
        // 订阅指定Topic的指定分区
        TopicPartition partition = new TopicPartition("topic01", 0);
        List<TopicPartition> partitions = Collections.singletonList(partition);
        consumer.assign(partitions);
        // 指定偏移位置为起始位置
//        consumer.seekToBeginning(partitions);

        // 指定分区指定偏移量
        consumer.seek(partition, 5);

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
