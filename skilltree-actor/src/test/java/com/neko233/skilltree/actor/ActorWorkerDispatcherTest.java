package com.neko233.skilltree.actor;

import java.util.stream.LongStream;

public class ActorWorkerDispatcherTest {

    public void onUserLogout() throws InterruptedException {

        LongStream.range(1, 100).parallel()
                .forEach(i -> {
                    ActorUser user = new ActorUser(i);
                    ActorWorkerDispatcher.instance
                            .addTaskToUserWorker(user, () -> {
                                System.out.println(Thread.currentThread().getName() + " halo");
                            });

                });


        Thread.sleep(100);
    }
}