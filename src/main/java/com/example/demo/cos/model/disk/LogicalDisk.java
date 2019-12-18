package com.example.demo.cos.model.disk;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class LogicalDisk implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 可以打开的最大文件数(linux上 查看方式 ulimit -n)
     */
    private long maxFileDescriptors;

    /**
     * 已经打开的文件数
     */
    private long openFileDescriptors;

    private List<LogicalDiskItem> logicalDiskItems;
}
