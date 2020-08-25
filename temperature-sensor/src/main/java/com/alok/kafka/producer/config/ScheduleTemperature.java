package com.alok.kafka.producer.config;

import com.alok.kafka.producer.produce.Temprature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class ScheduleTemperature {

    @Autowired
    private Temprature temprature;

    @Scheduled(cron = "30 * * * * ?")
    public void scheduleTemperatureReading() throws Exception {
        temprature.getTemperature();
    }
}
