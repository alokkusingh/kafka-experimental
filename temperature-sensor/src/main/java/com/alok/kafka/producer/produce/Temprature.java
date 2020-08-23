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

    public void startRain() {

        TemperatureData rainData = TemperatureData.builder()
                .id(UUID.randomUUID().toString())
                .temperature((int)(Math.random() * 1000))
                .build();

        ListenableFuture<SendResult<String, TemperatureData>> future =
                kafkaTemplate.send(topic, rainData);

        future.addCallback(new ListenableFutureCallback<SendResult<String, TemperatureData>>() {

            @Override
            public void onSuccess(SendResult<String, TemperatureData> result) {
                System.out.println("Sent message=[" + rainData +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        + rainData + "] due to : " + ex.getMessage());
            }
        });
    }
}
