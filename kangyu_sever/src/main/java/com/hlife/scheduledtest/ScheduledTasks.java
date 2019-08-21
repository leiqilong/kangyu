package com.hlife.scheduledtest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class ScheduledTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    /*@Scheduled(fixedRate = 5000)
    @Scheduled(fixedDelay = 5000)
    @Scheduled(initialDelay=1000, fixedRate = 5000)*/
    @Scheduled(cron = "0/5 * * ? 7 MON")
    public void reportCurrentTime() {
        log.debug("The time is now {}", dateFormat.format(new Date()));
    }
}
