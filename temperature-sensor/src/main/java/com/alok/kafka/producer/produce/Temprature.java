package com.alok.kafka.producer.produce;

import com.alok.kafka.producer.model.TemperatureData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

@Service
public class Temprature {

    @Value(value = "${app.kafka.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, TemperatureData> kafkaTemplate;

    private static final String PRODUCER_ID = UUID.randomUUID().toString();

    public void getTemperature() {

        TemperatureData temperatureData = TemperatureData.builder()
                .id(UUID.randomUUID().toString())
                .temperature((int)(Math.random() * 1000))
                .epochTime(System.currentTimeMillis())
                .build();

        ListenableFuture<SendResult<String, TemperatureData>> future =
                // I am adding PRODUCER_ID as key (optional) - In case topic is broken to multiple partitions
                // and producer has multiple instances running
                // the same key will make sure the message is sent to the same partition always
                // in order to achive ordering of the messages.
                kafkaTemplate.send(topic, PRODUCER_ID,  temperatureData);

        future.addCallback(new ListenableFutureCallback<SendResult<String, TemperatureData>>() {

            @Override
            public void onSuccess(SendResult<String, TemperatureData> result) {
                System.out.println("Sent message=[" +  temperatureData +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        +  temperatureData + "] due to : " + ex.getMessage());
            }
        });
    }
}
