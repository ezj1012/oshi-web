package com.example.demo.cos.model.disk;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 物理磁盘上的分区信息.
 * 
 * @author XiongJian
 *
 */
@Data
public class PhysicalDiskPartition implements Serializable {

    private static final long serialVersionUID = 1L;

    private String identification;

    private String name;

    private String type;

    private String uuid;

    private long size;

    private int major;

    private int minor;

    private String mountPoint;
    
    private transient List<LogicalDiskItem> logicalDisks;
}
