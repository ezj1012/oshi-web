package com.example.demo.cos.model.mem;

import java.io.Serializable;

import lombok.Data;

@Data
public class VirtualMemory implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long swapTotal;

    private Long swapUsed;

    private Long swapPagesIn;

    private Long swapPagesOut;
}
