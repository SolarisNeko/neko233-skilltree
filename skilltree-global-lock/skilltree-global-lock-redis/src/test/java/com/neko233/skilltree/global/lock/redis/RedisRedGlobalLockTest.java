//package com.neko233.skilltree.global.lock.redis;
//
//import org.junit.Before;
//import org.redisson.Redisson;
//import org.redisson.RedissonRedLock;
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author SolarisNeko
// * Date on 2023-05-20
// */
//public class RedisRedGlobalLockTest {
//
//    public static RLock create(String url,
//                               String key) {
//        Config config = new Config();
//        config.useSingleServer().setAddress(url);
//        RedissonClient redissonClient = Redisson.create(config);
//        return redissonClient.getLock(key);
//    }
//
//    private static RedissonRedLock redissonRedLock;
//    private static RedisRedGlobalLock redLock;
//
////    @Before
//    public static void before() {
//        redissonRedLock = new RedissonRedLock(
//                create("redis://localhost:6379", "lock_key1"),
//                create("redis://localhost:6379", "lock_key2"),
//                create("redis://localhost:6379", "lock_key3"));
//
//        redLock = new RedisRedGlobalLock(redissonRedLock);
//
//    }
//
//    private ExecutorService executorService = Executors.newCachedThreadPool();
//
//    //    @Test
//    public void test() throws Exception {
//        int[] count = {0};
//        for (int i = 0; i < 2; i++) {
//            executorService.submit(() -> {
//                try {
//                    redLock.lock();
//                    count[0]++;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    try {
//                        redLock.unlock();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }
//        executorService.shutdown();
//        executorService.awaitTermination(1, TimeUnit.HOURS);
//
//        System.out.println(count[0]);
//    }
//
//
//}