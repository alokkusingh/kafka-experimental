package com.alok.kafka.producer.model;

import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RainData {
    private String id;
    private Integer millimeters;
}
