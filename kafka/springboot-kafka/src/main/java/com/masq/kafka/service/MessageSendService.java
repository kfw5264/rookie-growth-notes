package com.masq.kafka.service;

public interface MessageSendService {

    void sendMessage(String topic, String key, String value);
}
