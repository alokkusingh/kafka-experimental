package com.alok.kafka.producer.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TemperatureData {
    private String id;
    private Integer temperature;
    private Long epochTime;

}
