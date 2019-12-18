package com.example.demo.cos.model.disk;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 硬件信息
 * 
 * @author XiongJian
 *
 */
@Data
public class PhysicalDiskItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private String model = "";

    private String name = "";

    private String serial = "";

    private long size = 0L;

    private long reads = 0L;

    private long readBytes = 0L;

    private long writes = 0L;

    private long writeBytes = 0L;

    private long currentQueueLength = 0L;

    private long transferTime = 0L;

    private List<PhysicalDiskPartition> partitions;

    private long timeStamp = 0L;
}
