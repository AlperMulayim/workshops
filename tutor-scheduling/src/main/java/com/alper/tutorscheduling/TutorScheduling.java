package com.alper.tutorscheduling;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class TutorScheduling {

    @Scheduled(fixedDelay = 2000)
    public void  fixedDelayCompute(){
        UUID uuid = UUID.randomUUID();
        System.err.println("Fixed Delay--> " + uuid.toString());

        System.err.println("DELAY-> " +LocalDateTime.now().toLocalTime());
    }

    @Scheduled(fixedRate = 3000)
    @Async
    public void  fixedRate(){
        UUID uuid = UUID.randomUUID();
        System.err.println("Fixed Rate--> " + uuid.toString());

        System.err.println("RATE-> " + LocalDateTime.now().toLocalTime());
    }

    //https://crontab.cronhub.io/ GET THE CRON
    //https://docs.oracle.com/cd/E12058_01/doc/doc.1014/e12030/cron_expressions.htm
    @Scheduled(cron = "0 15 16 * * ?")
    public void  cronJob(){
        UUID uuid = UUID.randomUUID();
        System.err.println("CRON Rate--> " + uuid.toString());

        System.err.println("CRON-> " + LocalDateTime.now().toLocalTime());
    }
}
