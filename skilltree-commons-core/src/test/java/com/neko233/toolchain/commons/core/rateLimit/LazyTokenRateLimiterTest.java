package com.neko233.toolchain.commons.core.rateLimit;

import org.junit.Assert;
import org.junit.Test;

public class LazyTokenRateLimiterTest {

    @Test
    public void test1() {
        int successCount = 0;

        LazyTokenRateLimiter limiter = new LazyTokenRateLimiter(1);

        for (int i = 0; i < 10; i++) {
            boolean isSuccess = limiter.tryAcquire();
            if (!isSuccess) {
                continue;
            }

            successCount += 1;
        }

        Assert.assertEquals(1, successCount);
    }

    @Test
    public void test2_maxReqPerSecond() throws InterruptedException {
        int successCount = 0;

        LazyTokenRateLimiter limiter = new LazyTokenRateLimiter(1, 2);

        for (int i = 0; i < 10; i++) {
            boolean isSuccess = limiter.tryAcquire();
            if (!isSuccess) {
                continue;
            }

            successCount += 1;
        }

        Assert.assertEquals(2, successCount);
    }

    @Test
    public void test3_maxReqPerSecond() throws InterruptedException {
        int successCount = 0;

        LazyTokenRateLimiter limiter = new LazyTokenRateLimiter(1, 2);

        boolean toDo = true;
        for (int i = 0; i < 10; i++) {
            boolean isSuccess = limiter.tryAcquire();
            if (!isSuccess) {
                continue;
            }

            successCount += 1;
            if (toDo) {
                Thread.sleep(1000);
                toDo = false;
            }
        }

        Assert.assertEquals(3, successCount);
    }

}