package com.alok.kafka.consumer.config;

import com.alok.kafka.avro.AvroDeserializer;
import com.alok.kafka.avro.RainData;
import com.alok.kafka.avro.TemperatureData;
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
import java.util.Properties;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${app.kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${app.kafka.topic.temp}")
    private String tempTopic;

    @Value(value = "${app.kafka.consumer.group}")
    private String consumerGroup;

    @Bean
    public Map<String, Object> consumerProperties() {
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
        //props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        props.put(JsonDeserializer.TYPE_MAPPINGS,
                "temperatureData:com.alok.kafka.avro.TemperatureData, rainData:com.alok.kafka.avro.RainData");


        /* QoS
            1. at-most-once
              - Set ‘enable.auto.commit’ to true.
              - Set ‘auto.commit.interval.ms’ to a lower timeframe
              - And do not make call to consumer.commitSync()

            2. at-least-once
              - Set ‘enable.auto.commit’ to false  or
              - Set ‘enable.auto.commit’ to true with ‘auto.commit.interval.ms’ to a higher number
              - Consumer should now then take control of the message offset commits to Kafka
                by making the following call consumer.commitSync()

                For this type of consumer, try to implement ‘idempotent’ behavior within consumer
                to avoid reprocessing of the duplicate messages
        */

        return props;
    }

    @Bean
    public ConsumerFactory<String, RainData> rainDataConsumerFactory() {

        return new DefaultKafkaConsumerFactory<String, RainData>(
                consumerProperties(),
                new StringDeserializer(),
                new AvroDeserializer(RainData.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, RainData>
    kafkaRainDataListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, RainData> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(rainDataConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, TemperatureData> temperatureDataConsumerFactory() {

        return new DefaultKafkaConsumerFactory<String, TemperatureData>(
                consumerProperties(),
                new StringDeserializer(),
                new AvroDeserializer(TemperatureData.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TemperatureData>
    kafkaTemperatureDataListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, TemperatureData> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(temperatureDataConsumerFactory());
        return factory;
    }

    @KafkaListener(topics = "temperature", groupId = "app-one", containerFactory = "kafkaTemperatureDataListenerContainerFactory")
    public void listenTemperature(TemperatureData message) {
        System.out.println("Received Message in group app-one: " + message);
    }

    @KafkaListener(topics = "rain", groupId = "app-one", containerFactory = "kafkaRainDataListenerContainerFactory")
    public void listenRain(RainData message) {
        System.out.println("Received Message in group app-one: " + message);
    }

    @Bean
    public LoggingErrorHandler errorHandler() {
        return new LoggingErrorHandler();
    }

}
