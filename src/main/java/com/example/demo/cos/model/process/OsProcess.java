package com.example.demo.cos.model.process;

import java.io.Serializable;

import lombok.Data;

/**
 * 操作系统进程状态描述
 * 
 * @author XiongJian
 *
 */
@Data
public class OsProcess implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String name = "";
    
    private String path = "";
    
    private String commandLine = "";
    
    private String currentWorkingDirectory = "";
    
    private String user = "";
    
    private String userID = "";
    
    private String group = "";
    
    private String groupID = "";
    
    private int processID;
    
    private int parentProcessID;
    
    private int threadCount;
    
    private int priority;
    
    private State state = State.OTHER;
    
    private long virtualSize;
    
    private long residentSetSize;
    
    private long kernelTime;
    
    private long userTime;
    
    private long startTime;
    
    private long upTime;
    
    private long bytesRead;
    
    private long bytesWritten;
    
    private long openFiles;
    
    private int bitness;
    
    /**
     * 系统采集的时间点
     */
    private long collectTime;
    
    /**
     * Process Execution States
     */
    public enum State {
        /**
         * Intermediate state in process creation
         */
        NEW,
        /**
         * Actively executing process
         */
        RUNNING,
        /**
         * Interruptible sleep state
         */
        SLEEPING,
        /**
         * Blocked, uninterruptible sleep state
         */
        WAITING,
        /**
         * Intermediate state in process termination
         */
        ZOMBIE,
        /**
         * Stopped by the user, such as for debugging
         */
        STOPPED,
        /**
         * Other or unknown states not defined
         */
        OTHER
    }
    
    public void setState(String name) { this.state = State.valueOf(name); }
}
