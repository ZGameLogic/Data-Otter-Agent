package com.zgamelogic.dataotter.agent.services;

import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;

import java.util.List;

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

    public long getMemoryUsage(){
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        long totalMemory = memory.getTotal() / (1024 * 1024);
        long availableMemory = memory.getAvailable() / (1024 * 1024);
        return totalMemory - availableMemory;
    }

    public long getCpuUsage(){
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        double cpuUsage = processor.getSystemCpuLoadBetweenTicks(prevTicks);
        return (long) (cpuUsage * 100);
    }

    public long getDiskUsedPercentage(){
        SystemInfo systemInfo = new SystemInfo();
        FileSystem fileSystem = systemInfo.getOperatingSystem().getFileSystem();

        List<OSFileStore> fileStores = fileSystem.getFileStores();

        long totalSpace = 0;
        long usedSpace = 0;

        for (OSFileStore fileStore : fileStores) {
            totalSpace += fileStore.getTotalSpace();
            usedSpace += (fileStore.getTotalSpace() - fileStore.getUsableSpace());
        }

        if (totalSpace == 0) return 0;
        return (usedSpace * 100) / totalSpace;
    }
}
