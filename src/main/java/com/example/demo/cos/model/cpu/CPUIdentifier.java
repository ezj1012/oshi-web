package com.example.demo.cos.model.cpu;

import java.io.Serializable;

import lombok.Data;

@Data
public class CPUIdentifier implements Serializable {

    private final static long serialVersionUID = 1L;

    private String vendor;

    private String name;

    private String family;

    private String model;

    private String stepping;

    private String processorID;

    private String identifier;

    private boolean cpu64bit;

    private Long vendorFreq;
}
