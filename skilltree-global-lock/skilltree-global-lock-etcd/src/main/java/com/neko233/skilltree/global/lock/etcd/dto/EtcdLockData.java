package com.neko233.skilltree.global.lock.etcd.dto;

import lombok.Data;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 锁对象封装
 *
 * @author SolarisNeko
 */
@Data
public class EtcdLockData {

    private String lockKey;
    private boolean lockSuccess;
    private long leaseId;
    private ScheduledExecutorService service;
    private Thread owningThread;
    private String lockPath;
    public final AtomicInteger lockCount = new AtomicInteger(1);

    private EtcdLockData() {
    }

    public EtcdLockData(Thread owningThread,
                        String lockPath) {
        this.owningThread = owningThread;
        this.lockPath = lockPath;
    }
}
