package com.neko233.skilltree.global.lock.mysql;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author SolarisNeko
 * Date on 2023-05-23
 */
public class MysqlFuGlobalLockTest {

    private DataSource dataSource;
    private final String key = "test_lock";
    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Before
    public void before() throws Exception {
        initDataSource();
    }

    private void initDataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/test?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setPassword("root");
        druidDataSource.setUsername("root");
        druidDataSource.setMaxActive(10);
        druidDataSource.setInitialSize(10);
        druidDataSource.setMaxWait(50000);
        druidDataSource.init();
        dataSource = druidDataSource;
    }

    @Test
    public void testMysqlFUDistributeLock() throws Exception {
        int[] count = {0};
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                final MysqlFuGlobalLock lock = new MysqlFuGlobalLock(dataSource, key);
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

    @Test
    public void testMysqlIDDistributeLock() throws Exception {
        int[] count = {0};
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                final MysqlExcludeGlobalLock lock = new MysqlExcludeGlobalLock(dataSource, 3000, key);
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