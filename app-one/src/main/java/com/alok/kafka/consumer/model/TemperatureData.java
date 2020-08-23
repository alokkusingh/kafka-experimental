package com.alok.kafka.consumer.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TemperatureData {
    private String id;
    private Integer temperature;
}
