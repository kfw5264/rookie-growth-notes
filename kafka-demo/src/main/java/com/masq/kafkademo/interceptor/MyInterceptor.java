package com.masq.kafkademo.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class MyInterceptor implements ProducerInterceptor<String, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyInterceptor.class);

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        return new ProducerRecord<>(record.topic(), record.partition(), record.timestamp(), record.key() + "-bak", record.value() + "-bak");
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        LOGGER.info(metadata.topic() + "--" + metadata.partition() + "--" + metadata.timestamp() + "--"
                + metadata.offset() + "--" + metadata.serializedKeySize() + metadata.serializedValueSize());
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
