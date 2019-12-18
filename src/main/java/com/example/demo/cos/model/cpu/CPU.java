package com.example.demo.cos.model.cpu;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class CPU implements Serializable {

    private static final long serialVersionUID = 1L;

    private CPUIdentifier cpuIdentifier;

    private Long maxFreq;

    private long[] currentFreq;

    private List<LogicalCPU> logicalCpus;
    
    private long[] systemCpuLoadTicks;
    
    private String[] cpuTickTypes;
    
    private long[][] processorCpuLoadTicks;
    
    private Integer logicalProcessorCount;
    
    private Integer physicalProcessorCount;
    
    private Integer physicalPackageCount;

    private Long contextSwitches;
    
    private Long interrupts;
}
