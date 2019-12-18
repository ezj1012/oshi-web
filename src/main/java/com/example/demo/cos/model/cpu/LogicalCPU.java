package com.example.demo.cos.model.cpu;

import java.io.Serializable;

import lombok.Data;

@Data
public class LogicalCPU implements Serializable {

    private final static long serialVersionUID = 1L;

    private Integer processorNumber;

    private Integer physicalProcessorNumber;

    private Integer physicalPackageNumber;

    private Integer numaNode;

    private Integer processorGroup;
}
