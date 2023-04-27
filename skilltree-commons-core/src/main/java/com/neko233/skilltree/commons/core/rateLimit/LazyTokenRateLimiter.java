package com.neko233.skilltree.commons.core.rateLimit;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author SolarisNeko on 2022-12-26
 **/
@Slf4j
public class LazyTokenRateLimiter {

    private final Object mutex = new Object();
    // state
    private final int reqPerSecond;
    private final int maxReqPerSecond;
    private long previousUpdateMs = System.currentTimeMillis();
    private int counter = 0;

    public LazyTokenRateLimiter(int reqPerSecond) {
        this(reqPerSecond, reqPerSecond);
    }

    public LazyTokenRateLimiter(int reqPerSecond, int maxReqPerSecond) {
        this.reqPerSecond = reqPerSecond;
        this.maxReqPerSecond = maxReqPerSecond;
    }

    public boolean tryAcquire() {
        return tryAcquire(1, 0, TimeUnit.SECONDS);
    }

    public boolean tryAcquire(int timeout, TimeUnit unit) {
        return tryAcquire(1, timeout, unit);
    }

    public boolean tryAcquire(int permit, int timeout, TimeUnit unit) {
        long currentMs = System.currentTimeMillis();
        boolean isSuccess = acquireWithRefresh(permit);

        // 没有成功, 则重试
        isSuccess = retryIfFailure(permit, timeout, unit, currentMs, isSuccess);
        return isSuccess;
    }

    private boolean retryIfFailure(int permit, int timeout, TimeUnit unit, long currentMs, boolean isSuccess) {
        long tempMs;
        if (!isSuccess && timeout > 0) {
            tempMs = System.currentTimeMillis();
            while (tempMs <= (currentMs + unit.toMillis(timeout))) {
                try {
                    // 100 ms 重试一次
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    log.error("[{}] tryAcquire happen interrupted", this.getClass().getSimpleName(), e);
                    break;
                }
                isSuccess = acquireWithRefresh(permit);
                if (isSuccess) {
                    break;
                }
            }
        }
        return isSuccess;
    }

    private boolean acquireWithRefresh(int permit) {
        checkUpdate();

        boolean isSuccess = false;
        synchronized (mutex) {
            int result = counter + permit;
            if (result <= maxReqPerSecond) {
                counter = result;
                isSuccess = true;
            }
        }
        return isSuccess;
    }

    private void checkUpdate() {
        long currentMs = System.currentTimeMillis();
        long minusSecond = currentMs - previousUpdateMs;
        if (minusSecond > TimeUnit.SECONDS.toMillis(1)) {
            synchronized (mutex) {
                counter = Math.max(0, counter - reqPerSecond * (int) minusSecond);
            }
            previousUpdateMs = currentMs;
        }
    }


}
