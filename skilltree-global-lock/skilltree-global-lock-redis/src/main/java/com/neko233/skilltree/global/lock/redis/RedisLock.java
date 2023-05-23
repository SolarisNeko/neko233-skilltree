package com.neko233.skilltree.global.lock.redis;

import com.neko233.skilltree.global.lock.AbstractLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * Redis Lock
 *
 * @author SolarisNeko on 2023-05-01
 */
public class RedisLock implements AbstractLock {

    private final RedissonClient redissonClient;
    private final String key;

    public RedisLock(RedissonClient redissonClient,
                     String key) {
        this.redissonClient = redissonClient;
        this.key = key;
    }

    @Override
    public void lock() {
        redissonClient.getLock(key)
                .lock();
    }

    @Override
    public void lockInterruptibly() {
        RLock lock = redissonClient.getLock(key);
        try {
            lock.lockInterruptibly();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean tryLock() {
        return redissonClient.getLock(key)
                .tryLock();
    }

    @Override
    public boolean tryLock(long time,
                           TimeUnit unit) {
        RLock lock = redissonClient.getLock(key);
        try {
            return lock.tryLock(time, unit);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unlock() {
        redissonClient.getLock(key)
                .unlock();
    }

    @Override
    public Condition newCondition() {
        return redissonClient.getLock(key)
                .newCondition();
    }
}
