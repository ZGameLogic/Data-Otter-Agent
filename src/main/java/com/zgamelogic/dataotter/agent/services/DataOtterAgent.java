package com.zgamelogic.dataotter.agent.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DataOtterAgent {

    @Value("${data-otter.url}")
    private String baseUrl;

    @Scheduled(cron = "0 * * * * *")
    private void scheduledMinuteTask() {
        System.out.println(baseUrl);
    }
}
