package com.example.demo.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.cos.CollectOperateSystemUtil;
import com.example.demo.cos.DiskHelper;
import com.example.demo.cos.model.disk.Disk;

@RequestMapping("/monitor")
@RestController
public class MonitorMvc {

    @RequestMapping("/getComputerInfo")
    public Disk getComputerInfo(HttpServletRequest request, HttpServletResponse response) {
          Disk disk = CollectOperateSystemUtil.getInstance().getDisk();
          DiskHelper.composite(disk);
          return disk;
    }
}
