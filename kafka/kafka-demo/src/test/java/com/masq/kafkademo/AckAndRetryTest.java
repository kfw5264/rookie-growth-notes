package com.masq.kafkademo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;

import java.util.Properties;

/**
 * Kafka生产者在发送完一条消息之后，要求leader在一定时间内进行ack应答，如果没有应答，生产者会尝试n次重新发送<br/>
 * acks=1 默认。Leader会将Record写入本地日志中，但不会等待所有Follower的完全确认请款该做出响应。在这种情况下，如果Leader在确认记录之后，但在Follower复制消息之前失败，则记录将丢失<br/>
 * acks=0 生产不会等待服务器确认，记录会添加到网络缓冲区中并视为已发送，在这种情况下，不能保证服务器已收到记录。<br/>
 * acks=all 这意味者Leader全套同步副本确认记录，保证了只要有一个同步副本仍处于活动状态，数据就不会丢失。等效于acks=-1<br/>
 *
 * 如果生产者没有在规定时间内收到ack确认，Kafka可以开启reties机制<br/>
 * request.timeout.ms=30000<br/>
 * retries=2147383647<br/>
 *
 * Kafka写入成功但在规定时间没有发送acks，可能会导致数据重复
 */
public class AckAndRetryTest {

    @Test
    public void testProducerAckAndRetires() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "centos0:9092,centos1:9092,centos2:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 设置acks跟retries
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);

        // 设置一个很小的超时时间，测试超时
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 1);

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {

            ProducerRecord<String, String> record = new ProducerRecord<String, String>("topic01", "ack", "testAck");
            producer.send(record);
            producer.flush();
        }

    }
}
