package com.example.demo.cos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.cos.model.disk.Disk;
import com.example.demo.cos.model.disk.LogicalDisk;
import com.example.demo.cos.model.disk.LogicalDiskItem;
import com.example.demo.cos.model.disk.PhysicalDiskItem;
import com.example.demo.cos.model.disk.PhysicalDiskPartition;
import com.example.demo.utils.CommUtil;

public final class DiskHelper {

    public static void parseId(LogicalDiskItem diskItem) {
        if (diskItem == null) { return; }
        String volume = diskItem.getVolume();
        if (CommUtil.isEmpty(volume)) { return; }
        int sid = volume.indexOf("{");
        int eid = volume.indexOf("}", sid + 1);
        if (sid > 1 && eid > 1) {
            diskItem.setLogicalVolumeId(volume.substring(sid+1, eid));
        }
    }

    public static void parseIds(List<LogicalDiskItem> diskItems) {
        if (CommUtil.isEmpty(diskItems)) { return; }
        for (LogicalDiskItem diskItem : diskItems) {
            parseId(diskItem);
        }
    }

    public static void composite(Disk disk) {
        if (disk == null) { return; }
        LogicalDisk logicalDisk = disk.getLogicalDisk();
        List<LogicalDiskItem> logicalDiskItems = logicalDisk.getLogicalDiskItems();
        List<PhysicalDiskItem> physicalDisks = disk.getPhysicalDisks();
        //
        if (logicalDisk == null || CommUtil.isEmpty(logicalDiskItems)) { return; }
        parseIds(logicalDisk.getLogicalDiskItems());
        //
        if (CommUtil.isEmpty(physicalDisks)) { return; }
        // 物理磁盘的唯一ID
        Map<String, PhysicalDiskPartition> pdps = new HashMap<String, PhysicalDiskPartition>();
        Map<String, PhysicalDiskItem> pdis = new HashMap<String, PhysicalDiskItem>();
        for (PhysicalDiskItem physicalDiskItem : physicalDisks) {
            List<PhysicalDiskPartition> partitions = physicalDiskItem.getPartitions();
            for (PhysicalDiskPartition pdp : partitions) {
                pdps.put(pdp.getUuid(), pdp);
                pdis.put(pdp.getUuid(), physicalDiskItem);
            }
        }
        // 建立逻辑磁盘和物理磁盘关联关系.
        for (LogicalDiskItem logicalDiskItem : logicalDiskItems) {
            String uuid = logicalDiskItem.getLogicalVolumeId();
            PhysicalDiskPartition pdp = pdps.get(uuid);
            if (pdp != null) {
                logicalDiskItem.setPhysicalDisk(pdis.get(uuid));
                List<LogicalDiskItem> logicalDisks = pdp.getLogicalDisks();
                if (logicalDisks == null) {
                    logicalDisks = new ArrayList<LogicalDiskItem>();
                    pdp.setLogicalDisks(logicalDisks);
                }
                logicalDisks.add(logicalDiskItem);
            }
        }
    }
}
