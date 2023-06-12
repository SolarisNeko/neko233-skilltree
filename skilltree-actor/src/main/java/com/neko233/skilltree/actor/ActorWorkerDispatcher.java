package com.neko233.skilltree.actor;


import com.neko233.skilltree.annotation.Experimental;
import lombok.extern.slf4j.Slf4j;

/**
 * {@link ActorWorkerDispatcher} 是 Actor 功能入口
 * {@link ActorApi} 需要实现 API
 *
 * @author SolarisNeko on 2023-02-05
 **/
@Slf4j
@Experimental(comment = "Actor Worker 线程绑定 ")
public class ActorWorkerDispatcher {

    private final ActorWorkerManager actorWorkerManager = new ActorWorkerManager();

    public static final ActorWorkerDispatcher instance = new ActorWorkerDispatcher();

    private ActorWorkerDispatcher() {
        try {
            actorWorkerManager.init();
        } catch (Throwable e) {
            log.error("workerCenter init error!", e);
        }
    }

    public ActorWorkerDispatcher onNotUseActor(ActorApi actor) {
        if (actor.getActorThreadId() != null) {
            actorWorkerManager.removeActorUse(actor.getActorThreadId());
            return this;
        }
        actorWorkerManager.removeActorUse(actor.toCalculateActorId());
        return this;
    }

    /**
     * 对外 API
     *
     * @param actorApi
     * @param runnable
     * @return
     */
    public ActorWorkerDispatcher addTaskToUserWorker(ActorApi actorApi, Runnable runnable) {
        Integer actorThreadId = actorApi.getActorThreadId();
        if (actorThreadId == null) {
            int actorId = addWorkerTaskById(actorApi.toCalculateActorId(), runnable);
            if (actorId == -1) {
                return this;
            }
            actorApi.setActorThreadId(actorId);
            return this;
        }

        addWorkerTaskById(actorThreadId, runnable);
        return this;
    }

    private int addWorkerTaskById(long userId, Runnable task) {
        final WorkerTask workerTask = WorkerTask.builder()
                .type("default")
                .task(task)
                .isCanDrop(false)
                .addMs(System.currentTimeMillis())
                .build();
        return actorWorkerManager.addActorTask(userId, workerTask);
    }
}
