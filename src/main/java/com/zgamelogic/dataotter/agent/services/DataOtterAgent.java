package com.zgamelogic.dataotter.agent.services;

import com.zgamelogic.dataotter.agent.data.Agent;
import com.zgamelogic.dataotter.agent.data.AgentReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DataOtterAgent {
    private final DataOtterService dataOtterService;
    private final SystemService systemService;
    private final long AGENT_ID;
    private final String AGENT_VERSION;

    public DataOtterAgent(DataOtterService dataOtterService, PersistenceService persistenceService, SystemService systemService) {
        this.dataOtterService = dataOtterService;
        this.systemService = systemService;
        AGENT_VERSION = "0.0.1";
        AGENT_ID = persistenceService.getAgentId().orElseGet(() -> {
            log.info("No agent data file found, registering new agent...");
            Agent agent = new Agent(null, systemService.getSystemName(), systemService.getOperatingSystemName());
            Agent registered = dataOtterService.registerAgent(agent);
            persistenceService.saveAgentId(registered.id());
            log.info("Agent registered with id: {}", registered.id());
            return registered.id();
        });


    }

    @Scheduled(cron = "0 * * * * *")
    private void scheduledMinuteTask() {
        System.out.println(AGENT_VERSION);
        sendAgentStatus();
    }

    private void sendAgentStatus() {
        AgentReport report = new AgentReport(200, 56, 23, "0.0.1");
        dataOtterService.report(report, AGENT_ID);
    }
}
