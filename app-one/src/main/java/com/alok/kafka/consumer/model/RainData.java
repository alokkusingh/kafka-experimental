package com.alok.kafka.consumer.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RainData {
    private String id;
    private Integer millimeters;
    private Long epochTime;

}
