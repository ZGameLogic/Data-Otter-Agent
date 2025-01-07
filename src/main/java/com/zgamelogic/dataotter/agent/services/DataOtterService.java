package com.zgamelogic.dataotter.agent.services;

import com.zgamelogic.dataotter.agent.data.Agent;
import com.zgamelogic.dataotter.agent.data.AgentReport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DataOtterService {
    @Value("${data-otter.url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public DataOtterService() {
        restTemplate = new RestTemplate();
    }

    public Agent registerAgent(Agent agent) {
        String url = baseUrl + "/agent/register";
        return restTemplate.postForObject(url, agent, Agent.class);
    }

    public void report(AgentReport report, long id){
        String url = baseUrl + "/agent/" + id + "/status";
        restTemplate.postForObject(url, report, String.class);
    }
}
