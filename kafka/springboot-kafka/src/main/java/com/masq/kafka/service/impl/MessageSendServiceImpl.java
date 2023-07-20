package com.masq.kafka.service.impl;

import com.masq.kafka.service.MessageSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MessageSendServiceImpl implements MessageSendService {

    @Autowired
    private KafkaTemplate<String, String> template;

    @Override
    public void sendMessage(String topic, String key, String value) {
        template.send(topic, key, value);
    }
}
