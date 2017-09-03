package com.fengxue.learning.kafka.configuration;

import com.fengxue.learning.kafka.cache.MessageSwapCacheManager;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Optional;

public class KafkaConsumerListener {

    @Autowired
    private MessageSwapCacheManager messageSwapCacheManager;

    @KafkaListener(topics = {KafkaConstant.TOPIC})
    public void listen(ConsumerRecord<?, ?> record) {

//        Integer partiton  = record.partition();
//        String value = (String) record.value();
//        messageSwapCacheManager.put("1", value);
//        System.out.println(value);

        System.out.println(Thread.currentThread() + "  " + record);
//        try{
//            Thread.sleep(10000);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

//        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
//        if (kafkaMessage.isPresent()) {
//            System.out.println(Thread.currentThread() + "  " + record);
//        }
    }


}
