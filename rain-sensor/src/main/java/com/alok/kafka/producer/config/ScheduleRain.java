package com.alok.kafka.producer.config;

import com.alok.kafka.producer.produce.Rain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class ScheduleRain {

    @Autowired
    private Rain rain;

    @Scheduled(cron = "0 * * * * ?")
    public void scheduleRainReading() throws Exception {
        rain.readRainData();
    }
}
