package com.zgamelogic.dataotter.agent.data;

public record AgentReport(long memoryUsage, long cpuUsage, long diskUsage, String agentVersion) {
}
