package com.alok.kafka.producer.model;

import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TemperatureData {
    private String id;
    private Integer temperature;
}
