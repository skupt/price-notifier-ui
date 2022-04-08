package com.example.pricenotifierui.service;

import com.example.pricenotifierui.service.jobcomponent.CallerBack;
import com.example.pricenotifierui.service.jobcomponent.Shutdownable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

@PropertySource("classpath:application.properties")
@Service
public class JobService {
    @Value("${app.pricenotifier-url}")
    private String uiBaseUrl;
    @Autowired
    private ThreadGroup threadGroup;
    @Autowired
    private CallerBack callerBack;


    @PostConstruct
    private void startJobThreads() {
        callerBack.start();
    }

    @PreDestroy
    private void stopJobThreads() {
        Thread[] threads = new Thread[threadGroup.activeCount()];
        for (Thread thread : threads) {
            Shutdownable s = (Shutdownable) thread;
            s.setShutdown(true);
        }
        while (threadGroup.activeCount() > 0) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
