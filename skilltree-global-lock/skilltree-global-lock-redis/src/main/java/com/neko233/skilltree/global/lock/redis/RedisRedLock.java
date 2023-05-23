package com.neko233.skilltree.global.lock.redis;

import com.neko233.skilltree.global.lock.AbstractLock;
import org.redisson.RedissonRedLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * Redis Red Lock by Redisson
 *
 * @author SolarisNeko
 */
public class RedisRedLock implements AbstractLock {

    private RedissonRedLock redLock;

    private RedisRedLock() {
    }

    public RedisRedLock(RedissonRedLock redLock) {
        this.redLock = redLock;
    }

    @Override
    public void lock() {
        redLock.lock();
    }

    @Override
    public void lockInterruptibly() {
        try {
            redLock.lockInterruptibly();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean tryLock() {
        return redLock.tryLock();
    }

    @Override
    public boolean tryLock(long time,
                           TimeUnit unit) {
        try {
            return redLock.tryLock(time, unit);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unlock() {
        redLock.unlock();
    }

    @Override
    public Condition newCondition() {
        return redLock.newCondition();
    }
}
