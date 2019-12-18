package com.example.demo.cos.bean;

import lombok.Data;

@Data
public class Computer {

    /**
     * mapping-field: CPU个数[CPU_COUNT]
     */
    private Integer cpuCount;

    /**
     * mapping-field: CPU核数[CPU_CORES]
     */
    private Integer cpuCores;

    /**
     * mapping-field: 内存大小[MEM_SIZE] 单位：Byte
     */
    private Long memSize;

    /**
     * mapping-field: 硬盘大小[DISK_SIZE] 单位：Byte
     */
    private Long diskSize;

    /**
     * mapping-field: javaVersion[DISK_SIZE]
     */
    private String javaVersion;
}
