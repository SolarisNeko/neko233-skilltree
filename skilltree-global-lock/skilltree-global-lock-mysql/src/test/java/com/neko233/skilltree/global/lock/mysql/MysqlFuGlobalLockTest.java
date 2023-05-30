package com.neko233.skilltree.global.lock.mysql;

import org.junit.Test;

import javax.sql.DataSource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author SolarisNeko
 * Date on 2023-05-23
 */
public class MysqlFuGlobalLockTest {

    private final DataSource dataSource = DataSourceMock.createDataSource();
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private final String lockName = "test_lock";

    @Test
    public void testMysqlFUDistributeLock() throws Exception {
        int[] count = {0};
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                final MysqlFuGlobalLock lock = new MysqlFuGlobalLock(dataSource, lockName);
                try {
                    lock.lock();
//                    if (lock.tryLock(schedulerLockInfo, 1000, TimeUnit.MILLISECONDS)) {
                    count[0]++;
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        lock.unlock();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println(count[0]);
    }

    @Test
    public void testMysqlIDDistributeLock() throws Exception {
        int[] count = {0};
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                final MysqlExcludeGlobalLock lock = new MysqlExcludeGlobalLock(dataSource, 3000, lockName);
                try {
                    lock.lock();
//                    if (lock.tryLock(schedulerLockInfo, 1000, TimeUnit.MILLISECONDS)) {
                    count[0]++;
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        lock.unlock();
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