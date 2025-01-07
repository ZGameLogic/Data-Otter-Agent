package com.zgamelogic.dataotter.agent.services;

import org.springframework.stereotype.Service;
import oshi.SystemInfo;

@Service
public class SystemService {
    private final SystemInfo systemInfo;

    public SystemService() {
        systemInfo = new SystemInfo();;
    }

    public String getSystemName(){
        return systemInfo.getOperatingSystem().getNetworkParams().getHostName();
    }

    public String getOperatingSystemName(){
        return systemInfo.getOperatingSystem().toString();
    }
}
