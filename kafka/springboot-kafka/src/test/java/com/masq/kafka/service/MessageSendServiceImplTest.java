package com.masq.kafka.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MessageSendServiceImplTest {

    @Autowired
    private MessageSendService messageSendService;

    @Test
    public void testSendMessage() {
        messageSendService.sendMessage("topic01", "transaction-service", "This is a test for messageSendService");
    }
}
