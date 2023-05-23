package com.neko233.skilltree.global.lock.redis;

import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author SolarisNeko
 * Date on 2023-05-20
 */
public class RedisLockTest {

    private RedissonClient getClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://localhost:6379")
                .setPassword("");
        //.setConnectionMinimumIdleSize(10).setConnectionPoolSize(10);
        // .setConnectionPoolSize();

        return Redisson.create(config);
    }

    private ExecutorService executorService = Executors.newCachedThreadPool();

    RedissonClient client = getClient();

//    @Test
    public void test() throws Exception {
        int[] count = {0};
        for (int i = 0; i < 10; i++) {
            final RedisLock redisLock = new RedisLock(client, "lock_key");
            executorService.submit(() -> {
                try {
                    redisLock.lock();

                    count[0]++;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        redisLock.unlock();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);

        System.out.println(count[0]);
    }

}