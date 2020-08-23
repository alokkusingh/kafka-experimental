package com.alok.kafka.producer.produce;

import com.alok.kafka.producer.model.RainData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

@Service
public class Rain {

    @Value(value = "${app.kafka.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, RainData> kafkaTemplate;

    public void startRain() {

        RainData rainData = RainData.builder()
                .id(UUID.randomUUID().toString())
                .millimeters((int)(Math.random() * 1000))
                .build();

        ListenableFuture<SendResult<String, RainData>> future =
                kafkaTemplate.send(topic, rainData);

        future.addCallback(new ListenableFutureCallback<SendResult<String, RainData>>() {

            @Override
            public void onSuccess(SendResult<String, RainData> result) {
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
