package com.neko233.skilltree.global.lock.etcd;

import com.google.common.collect.Maps;
import com.neko233.skilltree.global.lock.GlobalLock;
import com.neko233.skilltree.global.lock.etcd.dto.EtcdLockData;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.Lease;
import io.etcd.jetcd.Lock;
import io.etcd.jetcd.lock.LockResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 基于 ETCD 实现分布式锁
 *
 * @author SolarisNeko
 */
@Slf4j
@Data
public class EtcdGlobalLock implements GlobalLock {

    private Client client;
    private Lock lockClient;
    private Lease leaseClient;
    private String lockKey;
    private String lockPath;

    /**
     * 锁的次数
     */
    private AtomicInteger lockCount;
    /**
     * 租约有效期,防止客户端崩溃，可在租约到期后自动释放锁；另一方面，正常执行过程中，会自动进行续租,单位 ns
     */
    private Long leaseTtl;
    /**
     * 续约锁租期的定时任务，初次启动延迟，单位默认为 s,默认为1s，可根据业务定制设置
     */
    private Long initialDelay = 0L;
    /**
     * 定时任务线程池类
     */
    ScheduledExecutorService scheduler = null;
    /**
     * 保存线程与锁对象的映射，锁对象包含重入次数，重入次数的最大限制为Int的最大值
     */
    private final ConcurrentMap<Thread, EtcdLockData> threadData = Maps.newConcurrentMap();

    private EtcdGlobalLock() {
    }

    public EtcdGlobalLock(Client client,
                          String lockKey,
                          long leaseTtl,
                          TimeUnit unit) {
        this.client = client;
        lockClient = client.getLockClient();
        leaseClient = client.getLeaseClient();
        this.lockKey = lockKey;
        // 转纳秒
        this.leaseTtl = unit.toNanos(leaseTtl);
        scheduler = Executors.newSingleThreadScheduledExecutor();
    }


    @Override
    public void lock() {
        // 检查重入性
        Thread currentThread = Thread.currentThread();
        EtcdLockData oldEtcdLockData = threadData.get(currentThread);
        if (oldEtcdLockData != null && oldEtcdLockData.isLockSuccess()) {
            // re-entering
            int lockCount = oldEtcdLockData.lockCount.incrementAndGet();
            if (lockCount < 0) {
                throw new Error("超出可重入次数限制");
            }
            return;
        }

        // 记录租约 ID
        long leaseId = 0L;
        try {
            leaseId = leaseClient.grant(TimeUnit.NANOSECONDS.toSeconds(leaseTtl)).get().getID();
            // 续租心跳周期
            long period = leaseTtl - leaseTtl / 5;

            // 启动定时任务续约
            EtcdKeepAliveRunnable runnable = new EtcdKeepAliveRunnable(leaseClient, leaseId);
            scheduler.scheduleAtFixedRate(runnable, initialDelay, period, TimeUnit.NANOSECONDS);

            LockResponse lockResponse = lockClient.lock(ByteSequence.from(lockKey.getBytes()), leaseId).get();
            if (lockResponse != null) {
                lockPath = lockResponse.getKey().toString(StandardCharsets.UTF_8);

                log.info("<Global-Lock-ETCD> 获取锁成功,锁路径:{},线程:{}", lockPath, currentThread.getName());
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("获取锁失败", e);
            return;
        }
        // 获取锁成功，锁对象设置
        EtcdLockData newEtcdLockData = new EtcdLockData(currentThread, lockKey);
        newEtcdLockData.setLeaseId(leaseId);
        newEtcdLockData.setService(scheduler);
        threadData.put(currentThread, newEtcdLockData);
        newEtcdLockData.setLockSuccess(true);
    }

    @Override
    public void unlock() {
        Thread currentThread = Thread.currentThread();
        EtcdLockData etcdLockData = threadData.get(currentThread);
        if (etcdLockData == null) {
            throw new IllegalMonitorStateException("You do not own the lock: " + lockKey);
        }
        int newLockCount = etcdLockData.lockCount.decrementAndGet();
        if (newLockCount > 0) {
            return;
        }
        if (newLockCount < 0) {
            throw new IllegalMonitorStateException("Lock count has gone negative for lock: " + lockKey);
        }
        try {
            // 释放锁
            if (lockPath != null) {
                lockClient.unlock(ByteSequence.from(lockPath.getBytes())).get();
            }
            // 关闭定时任务
            etcdLockData.getService().shutdown();
            // 删除租约
            if (etcdLockData.getLeaseId() != 0L) {
                leaseClient.revoke(etcdLockData.getLeaseId());
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("<Global-Lock-ETCD> 解锁失败. thread = {}", currentThread.getName(), e);
        } finally {
            // 移除当前线程资源
            threadData.remove(currentThread);
        }
    }


}
