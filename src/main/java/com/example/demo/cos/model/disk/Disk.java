package com.example.demo.cos.model.disk;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Disk implements Serializable {

    private static final long serialVersionUID = 1L;

    private LogicalDisk logicalDisk;

    private List<PhysicalDiskItem> physicalDisks;

}
