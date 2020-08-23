package com.alok.kafka.consumer.model;

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
