package com.masq.kafkademo;

import com.masq.kafkademo.interceptor.MyInterceptor;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.Duration;
import java.util.*;

public class AutoOffsetResetConfigTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoOffsetResetConfigTest.class);

    // 手动提交偏移量
    @Test
    public void commitOffsetBySelf() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "centos0:9092,centos1:9092,centos2:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "g4");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("topic01"));

        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
        if (!records.isEmpty()) {
            Iterator<ConsumerRecord<String, String>> recordIterator = records.iterator();
            while(recordIterator.hasNext()) {
                ConsumerRecord<String, String> record = recordIterator.next();
                LOGGER.info(record.toString());

                // 手动提交偏移量
                Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
                offsets.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1));
                consumer.commitAsync(offsets, (offset, exception) -> {
                    System.out.println(offset);
                });
            }
        }



    }

    // 消费者(g1组)
    @Test
    public void consumerTest() {
        // 消费者连接参数
        Properties props = new Properties();
        // kafka集群配置
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "centos0:9092,centos1:9092,centos2:9092");
        // key的反序列化方式
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // value的反序列化方式
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // 消费者组
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "g3");
        // 配置自动偏移量位置, kafka中没有初始化偏移量以及当前偏移量不存在的时候自动设置
        // earliest 自动设置为最开始的偏移量
        // latest   自动设置为结束的偏移量
        // none     抛出异常
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");


        // 建立连接
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // 订阅相关Topic
        consumer.subscribe(Collections.singletonList("mytopic"));

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

    // 消费者(g3组)
    @Test
    public void consumerTest2() {
        // 消费者连接参数
        Properties props = new Properties();
        // kafka集群配置
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "centos0:9092,centos1:9092,centos2:9092");
        // key的反序列化方式
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // value的反序列化方式
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // 消费者组
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "g3");
        // 配置自动偏移量位置, kafka中没有初始化偏移量以及当前偏移量不存在的时候自动设置
        // earliest 自动设置为最开始的偏移量
        // latest   自动设置为结束的偏移量
        // none     抛出异常
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // 下面两个配置表示消费者会在10s后自动提交当前偏移量，如果10s内结束程序，那么偏移量没有提交，下次消费还是会从开始的位置重新消费
        // 消费者自动提交偏移量的时间间隔
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "10000");
        // 设置允许自动提交
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");

        // 建立连接
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // 订阅相关Topic
        consumer.subscribe(Collections.singletonList("mytopic"));

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

    // 生产者
    @Test
    public void testProduce() {
        // 生产者连接参数
        Properties props = new Properties();
        // kafka集群
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "centos0:9092,centos1:9092,centos2:9092");
        // key的序列化方式
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // value的学历阿华方式
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 配置拦截器
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, MyInterceptor.class.getName());
        KafkaProducer<String, Serializable> producer = new KafkaProducer<>(props);

        for (int i = 0; i < 10; i++) {
            String key = "key-" + i;
            String value = "value-" + i;
            ProducerRecord<String, Serializable> record = new ProducerRecord<String, Serializable>("mytopic", key, value);
            // 发送消息给服务器
            producer.send(record);
        }

        producer.close();
    }
}
