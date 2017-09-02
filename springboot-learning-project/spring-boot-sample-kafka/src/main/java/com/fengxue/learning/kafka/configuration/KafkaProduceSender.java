package com.fengxue.learning.kafka.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProduceSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String msg){
        kafkaTemplate.send(KafkaConstant.TOPIC, msg);
    }
}
