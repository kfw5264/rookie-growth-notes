package com.masq.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.messaging.handler.annotation.SendTo;

import java.io.IOException;

@SpringBootApplication
public class SpringbootKafkaApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SpringbootKafkaApplication.class, args);
        System.in.read();
    }

    @KafkaListeners(
            @KafkaListener(topics = {"topic01"})
    )
    public void receiveMessage(ConsumerRecord<String, String> record) {
        System.out.println("topic01---" + record);
    }

    @KafkaListeners(
            @KafkaListener(topics = {"topic03"})
    )
    public void receiveMessage2(ConsumerRecord<String, String> record) {
        System.out.println("topic03---" + record);
    }

    @KafkaListeners(
            @KafkaListener(topics = {"topic01"})
    )
    @SendTo("topic03")
    public String receiveMessageAndSend(ConsumerRecord<String, String> record) {
        return record.value() + " masq.com";
    }
}
