package com.example.demo.cos.model.disk;

import java.io.Serializable;

import lombok.Data;

/**
 * 逻辑磁盘
 * 
 * @author XiongJian
 *
 */
@Data
public class LogicalDiskItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String volume;

    private String logicalVolume = "";

    /**
     * 
     */
    private String logicalVolumeId;

    private String mount;

    private String description;

    private String fsType;

    private String uuid;

    private long freeSpace;

    private long usableSpace;

    private long totalSpace;

    private long freeInodes;

    private long totalInodes;
    
    private transient PhysicalDiskItem physicalDisk;
}
