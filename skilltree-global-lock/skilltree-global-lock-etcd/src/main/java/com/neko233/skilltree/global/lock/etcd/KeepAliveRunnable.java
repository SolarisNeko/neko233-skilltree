package com.neko233.skilltree.global.lock.etcd;

import io.etcd.jetcd.Lease;

/**
 * 心跳续约线程类
 */
public class KeepAliveRunnable implements Runnable {
    private final Lease leaseClient;
    private final long leaseId;

    public KeepAliveRunnable(Lease leaseClient,
                             long leaseId) {
        this.leaseClient = leaseClient;
        this.leaseId = leaseId;
    }

    @Override
    public void run() {
        // 对该 leaseid 进行一次续约
        leaseClient.keepAliveOnce(leaseId);
    }
}