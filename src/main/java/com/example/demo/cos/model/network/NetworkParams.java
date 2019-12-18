package com.example.demo.cos.model.network;

import java.io.Serializable;

import lombok.Data;

@Data
public class NetworkParams implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String hostName;
    
    private String domainName;
    
    private String[] dnsServers;
    
    private String ipv4DefaultGateway;
    
    private String ipv6DefaultGateway;
}
