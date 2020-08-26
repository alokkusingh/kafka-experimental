package com.alok.kafka.consumer.config;

import com.alok.kafka.avro.AvroDeserializer;
import com.alok.kafka.avro.RainData;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.LoggingErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${app.kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${app.kafka.topic.rain}")
    private String tempTopic;

    @Value(value = "${app.kafka.consumer.group}")
    private String consumerGroup;

    @Bean
    public ConsumerFactory<String, RainData> consumerFactory() {

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);

        //props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        //props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        //props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, AvroDeserializer.class);

        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.TYPE_MAPPINGS, "rainData:com.alok.kafka.avro.RainData");

        return new DefaultKafkaConsumerFactory<String, RainData>(
                props,
                new StringDeserializer(),
                new AvroDeserializer(RainData.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, RainData>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, RainData> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @KafkaListener(topics = "rain", groupId = "app-two")
    public void listen(RainData message) {
        System.out.println("Received Message in group app-two: " + message);
    }

    @Bean
    public LoggingErrorHandler errorHandler() {
        return new LoggingErrorHandler();
    }
}

