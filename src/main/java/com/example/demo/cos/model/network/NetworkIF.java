package com.example.demo.cos.model.network;

import java.io.Serializable;

import lombok.Data;

@Data
public class NetworkIF implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String name;
    
    private String displayName;
    
    private String[] iPv4addr;
    
    private String[] iPv6addr;
    
    private Integer mTU;
    
    private String macaddr;
    
    private Long bytesRecv;
    
    private Long bytesSent;
    
    private Long packetsRecv;
    
    private Long packetsSent;
    
    private Long inErrors;
    
    private Long outErrors;
    
    private Long speed;
    
    private Long timeStamp;
}
