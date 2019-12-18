package com.example.demo.cos.model.mem;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 
 * @author XiongJian
 *
 */
@Data
public class Memory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 内存总大小
     */
    private Long total;
    
    /**
     * 可以内存.
     */
    private Long available;
    
    /**
     * 分页大小
     */
    private Long pageSize;

    private VirtualMemory virtualMemory;

    private List<PhysicalMemory> physicalMemories;
}
