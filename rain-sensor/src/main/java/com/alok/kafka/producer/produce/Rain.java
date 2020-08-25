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

    private static final String PRODUCER_ID = UUID.randomUUID().toString();

    public void readRainData() {

        RainData rainData = RainData.builder()
                .id(UUID.randomUUID().toString())
                .millimeters((int)(Math.random() * 1000))
                .epochTime(System.currentTimeMillis())
                .build();

        ListenableFuture<SendResult<String, RainData>> future =
                // I am adding PRODUCER_ID as key (optional) - In case topic is broken to multiple partitions
                // and producer has multiple instances running
                // the same key will make sure the message is sent to the same partition always
                // in order to achive ordering of the messages.
                kafkaTemplate.send(topic, PRODUCER_ID, rainData);

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
