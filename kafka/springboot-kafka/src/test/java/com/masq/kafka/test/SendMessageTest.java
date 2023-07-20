package com.masq.kafka.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
public class SendMessageTest {

    @Autowired
    private KafkaTemplate<String, String> template;

    @Test
    public void testSend() {
        // 非事务情况下发送
//        template.send("topic01", "test01", "This is a test message");

        template.executeInTransaction(new KafkaOperations.OperationsCallback<String, String, Object>() {
            @Override
            public Object doInOperations(KafkaOperations<String, String> kafkaOperations) {
                return kafkaOperations.send("topic01", "transaction-test", "This is a message in transaction");
            }
        });
    }
}
