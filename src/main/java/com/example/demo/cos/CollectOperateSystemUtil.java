package com.example.demo.cos;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.example.demo.cos.bean.Computer;
import com.example.demo.cos.model.cpu.CPU;
import com.example.demo.cos.model.cpu.CPUIdentifier;
import com.example.demo.cos.model.cpu.LogicalCPU;
import com.example.demo.cos.model.disk.Disk;
import com.example.demo.cos.model.disk.LogicalDisk;
import com.example.demo.cos.model.disk.LogicalDiskItem;
import com.example.demo.cos.model.disk.PhysicalDiskItem;
import com.example.demo.cos.model.disk.PhysicalDiskPartition;
import com.example.demo.cos.model.mem.Memory;
import com.example.demo.cos.model.mem.PhysicalMemory;
import com.example.demo.cos.model.mem.VirtualMemory;
import com.example.demo.cos.model.network.Network;
import com.example.demo.cos.model.network.NetworkIF;
import com.example.demo.cos.model.network.NetworkParams;
import com.example.demo.utils.CommUtil;

import lombok.extern.slf4j.Slf4j;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.software.os.FileSystem;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem.ProcessSort;

@Slf4j
public class CollectOperateSystemUtil {
    
    private static CollectOperateSystemUtil osUtil = null;
    
    private static String[] cpuTickTypes;
    
    public static CollectOperateSystemUtil getInstance() {
        if (osUtil == null) {
            synchronized (CollectOperateSystemUtil.class) {
                if (osUtil == null) {
                    osUtil = new CollectOperateSystemUtil();
                    
                    // 初始化CPU tick type
                    TickType[] values = CentralProcessor.TickType.values();
                    cpuTickTypes = new String[values.length];
                    for (int i = 0; i < values.length; i++) {
                        cpuTickTypes[i] = values[i].name();
                    }
                }
            }
        }
        return osUtil;
    }
    
    private final SystemInfo si = new SystemInfo();
    
    private long[] oldCPUTicks = null;
    
    public Computer getTarsierComputerInfo() {
        CentralProcessor processor = si.getHardware().getProcessor();
        HWDiskStore[] diskStores = si.getHardware().getDiskStores();
        long diskSize = 0L;
        for (HWDiskStore hwDiskStore : diskStores) {
            diskSize += hwDiskStore.getSize();
        }
        Computer computer = new Computer();
        // CPU核数
        computer.setCpuCores(processor.getLogicalProcessorCount());
        // CPU个数
        computer.setCpuCount(processor.getPhysicalProcessorCount());
        computer.setDiskSize(diskSize);
        computer.setMemSize(si.getHardware().getMemory().getTotal());
        computer.setJavaVersion(System.getProperty("java.version"));
        // System.getProperty("os.arch")
        // System.getProperty("sun.arch.data.model")
        // computer.setLogLocalspace(logLocalspace);
        return computer;
    }
    
    public Integer getCPURate() {
        CentralProcessor processor = si.getHardware().getProcessor();
        Integer cpuRate = null;
        if (oldCPUTicks == null) {
            oldCPUTicks = processor.getSystemCpuLoadTicks();
            CommUtil.sleep(300L);// 休息300毫秒产生间隔用于计算CPU使用率;
        }
        cpuRate = (int) (processor.getSystemCpuLoadBetweenTicks(oldCPUTicks) * 100);
        oldCPUTicks = processor.getSystemCpuLoadTicks();
        return cpuRate;
    }
    
    public Disk getDisk() {
        Disk disk = new Disk();
        disk.setLogicalDisk(getLogicalDisk());
        disk.setPhysicalDisks(getPhysicalDisk());
        return disk;
    }
    
    /**
     * 获取文件系统信息
     */
    public LogicalDisk getLogicalDisk() {
        Long logSTime = null;
        if (log.isDebugEnabled()) {
            logSTime = System.currentTimeMillis();
        }
        try {
            FileSystem fileSystem = si.getOperatingSystem().getFileSystem();
            
            // C盘,D盘,E盘之类的内容
            List<LogicalDiskItem> logicalDiskItems = CommUtil.copyProperties(fileSystem.getFileStores(),
                    LogicalDiskItem.class);
            
            // 组织返回值
            LogicalDisk result = new LogicalDisk();
            result.setMaxFileDescriptors(fileSystem.getMaxFileDescriptors());
            result.setOpenFileDescriptors(fileSystem.getOpenFileDescriptors());
            result.setLogicalDiskItems(logicalDiskItems);
            return result;
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("采集`逻辑磁盘`信息耗时[" + (System.currentTimeMillis() - logSTime) + "]毫秒!");
            }
        }
    }
    
    public List<PhysicalDiskItem> getPhysicalDisk() {
        Long logSTime = null;
        if (log.isDebugEnabled()) {
            logSTime = System.currentTimeMillis();
        }
        try {
            // 获取磁盘信息
            HWDiskStore[] diskStores = si.getHardware().getDiskStores();
            // 复制磁盘信息
            List<PhysicalDiskItem> result = CommUtil.copyProperties(diskStores, PhysicalDiskItem.class);
            for (int i = 0; i < result.size(); i++) {
                // 复制磁盘分区信息.
                List<PhysicalDiskPartition> ps = CommUtil.copyProperties(diskStores[i].getPartitions(),
                        PhysicalDiskPartition.class);
                result.get(i).setPartitions(ps);
            }
            return result;
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("采集`物理磁盘`信息耗时[" + (System.currentTimeMillis() - logSTime) + "]毫秒!");
            }
        }
    }
    
    public Memory getMemory() {
        Long logSTime = null;
        if (log.isDebugEnabled()) {
            logSTime = System.currentTimeMillis();
        }
        try {
            GlobalMemory memory = si.getHardware().getMemory();
            //
            List<PhysicalMemory> pms = CommUtil.copyProperties(memory.getPhysicalMemory(), PhysicalMemory.class);
            VirtualMemory vm = CommUtil.copyProperties(memory.getVirtualMemory(), VirtualMemory.class);
            
            // 组织返回新
            Memory result = CommUtil.copyProperties(memory, Memory.class);
            result.setPhysicalMemories(pms);
            result.setVirtualMemory(vm);
            return result;
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("采集`内存`信息耗时[" + (System.currentTimeMillis() - logSTime) + "]毫秒!");
            }
        }
    }
    
    private String[] getCpuTickTypes() {
        String[] r = new String[cpuTickTypes.length];
        for (int i = 0; i < cpuTickTypes.length; i++) {
            r[i] = cpuTickTypes[i];
        }
        return r;
    }
    
    public CPU getCPU() {
        Long logSTime = null;
        if (log.isDebugEnabled()) {
            logSTime = System.currentTimeMillis();
        }
        try {
            CentralProcessor processor = si.getHardware().getProcessor();
            
            CPU cpu = CommUtil.copyProperties(processor, CPU.class);
            cpu.setCpuIdentifier(CommUtil.copyProperties(processor.getProcessorIdentifier(), CPUIdentifier.class));
            cpu.setLogicalCpus(CommUtil.copyProperties(processor.getLogicalProcessors(), LogicalCPU.class));
            cpu.setCpuTickTypes(getCpuTickTypes());
            return cpu;
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("采集`CPU`信息耗时[" + (System.currentTimeMillis() - logSTime) + "]毫秒!");
            }
        }
    }
    
    public Network getNetwork() {
        Long logSTime = null;
        Long logRTime = null;
        if (log.isDebugEnabled()) {
            logSTime = System.currentTimeMillis();
        }
        try {
            // 网络参数
            NetworkParams networkParams = CommUtil.copyProperties(si.getOperatingSystem().getNetworkParams(),
                    NetworkParams.class);
            if (log.isDebugEnabled()) {
                log.debug("采集`网络-参数`信息耗时[" + (logRTime = (System.currentTimeMillis() - logSTime)) + "]毫秒!");
            }
            //
            List<NetworkIF> networkIFs = CommUtil.copyProperties(si.getHardware().getNetworkIFs(), NetworkIF.class);
            if (log.isDebugEnabled()) {
                log.debug("采集`网络-网卡`信息耗时[" + (System.currentTimeMillis() - logSTime - logRTime) + "]毫秒!");
            }
            //
            Network network = new Network();
            network.setNetworkParams(networkParams);
            network.setNetworkIFs(networkIFs);
            return network;
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("采集`网络`信息耗时[" + (System.currentTimeMillis() - logSTime) + "]毫秒!");
            }
        }
    }
    
    public void getProcesses() {
        OSProcess[] processes = si.getOperatingSystem().getProcesses();
        for (OSProcess osProcess : processes) {
            System.out.println(JSON.toJSONString(osProcess));
        }
    }
    
    
    public static void main(String[] args) {
        CollectOperateSystemUtil os = getInstance();
        // os.getDiskRate();
        // os.getLogicalDisk();
        // List<PhysicalDiskItem> physicalDisk = os.getPhysicalDisk();
        // Memory memory = os.getMemory();
        // os.getCPU();
        // Network network = os.getNetwork();
        os.getProcesses();
    }
}
