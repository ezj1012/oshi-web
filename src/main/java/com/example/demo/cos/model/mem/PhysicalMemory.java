package com.example.demo.cos.model.mem;

import java.io.Serializable;

import lombok.Data;

@Data
public class PhysicalMemory implements Serializable {

    private static final long serialVersionUID = 1L;

    private String bankLabel;

    private Long capacity;

    private Long clockSpeed;

    private String manufacturer;

    private String memoryType;
}
