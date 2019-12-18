package com.example.demo.cos.model.process;

public enum ProcessType {
    /**
     * 不关注的
     */
    UNKNOWN, 
    /**
     * JAVA进程
     */
    JAVA, 
    /**
     * MYSQL进程
     */
    MYSQL,
    /**
     * influxDB进程
     */
    INFLUXDB,
    /**
     * NGINX进程
     */
    NGINX
    
    ;
}
