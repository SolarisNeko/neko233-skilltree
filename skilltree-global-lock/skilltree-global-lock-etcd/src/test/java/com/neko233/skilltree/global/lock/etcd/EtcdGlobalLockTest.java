//package com.neko233.skilltree.global.lock.etcd;
//
//import io.etcd.jetcd.Client;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author SolarisNeko
// * Date on 2023-05-23
// */
//public class EtcdGlobalLockTest {
//
//    private Client client;
//    private String key = "/etcd/lock";
//    private static final String server = "http://xxxx:xxxx";
//
//    private ExecutorService executorService = Executors.newFixedThreadPool(10);
//
////    @Before
////    public void before() throws Exception {
////        initEtcdClient();
////    }
////
////
////    @After
////    public void after() {
////        client.close();
////    }
//
//    private void initEtcdClient() {
//        client = Client.builder().endpoints(server).build();
//    }
//
////    @Test
//    public void testEtcdDistributeLock() throws InterruptedException {
//        int[] count = {0};
//        for (int i = 0; i < 100; i++) {
//            executorService.submit(() -> {
//                final EtcdGlobalLock lock = new EtcdGlobalLock(client, key, 20, TimeUnit.SECONDS);
//                try {
//                    lock.lock();
//                    count[0]++;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    try {
//                        lock.unlock();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }
//        executorService.shutdown();
//        executorService.awaitTermination(1, TimeUnit.HOURS);
//        System.err.println("执行结果: " + count[0]);
//    }
//
//
//    /**
//     * 不支持
//     *
//     * @throws InterruptedException
//     */
//    @Deprecated
////    @Test
//    public void testEtcdTryLock() throws InterruptedException {
//        int[] count = {0};
//        for (int i = 0; i < 20; i++) {
//            executorService.submit(() -> {
//                final EtcdGlobalLock lock = new EtcdGlobalLock(client, key, 20, TimeUnit.SECONDS);
//                try {
//                    if (lock.tryLock(5, TimeUnit.SECONDS)) {
//                        System.err.println("获取锁成功");
//                        count[0]++;
//                        Thread.sleep(2000);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    System.err.println("获取锁失败");
//                    lock.unlock();
//                }
//            });
//        }
//        executorService.shutdown();
//        executorService.awaitTermination(1, TimeUnit.HOURS);
//        System.err.println("执行结果: " + count[0]);
//    }
//
//
//}