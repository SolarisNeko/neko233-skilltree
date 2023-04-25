package com.neko233.toolchain.actor;


import com.neko233.toolchain.actor.strategy.ActorIdStrategy;
import com.neko233.toolchain.actor.strategy.ActorIdStrategyConstant;
import com.neko233.toolchain.actor.strategy.ActorIdStrategyFactory;
import com.neko233.toolchain.commons.api.lifecycle.LifeCycle;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;


/**
 * Actor Worker 管理器
 * {@link ActorApi 使用对象, 必须实现该 API}
 */
@Slf4j
public class ActorWorkerManager implements LifeCycle {


    public static final int Default_MQ_SIZE_1W = 10000;
    public static final int DEFAULT_WORKER_NUMBER = Runtime.getRuntime().availableProcessors() * 2;
    private final String name;
    private Worker[] workers;
    private final int workerNum;
    private final int workerMqSize;
    private final long shutdownTimeoutMs; // 等待关闭的超时时间，单位毫秒

    // actorId 生成策略
    private ActorIdStrategy strategy = ActorIdStrategyFactory.choose(ActorIdStrategyConstant.LOAD_BALANCE);

    public ActorWorkerManager() {
        this("Default-WorkerCenter", DEFAULT_WORKER_NUMBER, 1000);
    }

    public ActorWorkerManager(String workerCenterName, Integer workerNumber, Integer workerMqSize) {
        this.name = workerCenterName;
        this.workerNum = Optional.ofNullable(workerNumber).orElse(DEFAULT_WORKER_NUMBER);
        this.workerMqSize = Optional.ofNullable(workerMqSize).orElse(Default_MQ_SIZE_1W);
        this.shutdownTimeoutMs = 20_000L;
    }

    public synchronized void changeActorIdStrategy(ActorIdStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * 随机一个 worker 执行都可以
     */
    public void addRandomTask(WorkerTask workerTask) throws ActorBindException {
        addActorTask(System.currentTimeMillis(), workerTask);
    }

    /**
     * 移除某个东西对 Thread 的使用, 用来进行统计
     */
    public void removeActorUse(long toShardingId) {
        if (strategy == null) {
            log.error("you forget to config your ActorIdStrategy!");
            return;
        }
        int actorId = strategy.getActorId(toShardingId, workers.length);
        if (actorId < 0) {
            log.error("your task not work because actorId < 0. actorId = {}", actorId);
            return;
        }

        strategy.minusUse(actorId);
    }

    /**
     * 固定 User 对应的 Thread
     *
     * @param toShardingId 分片id. 一般是 userId
     * @param workerTask   任务
     * @return -1 error / actorId >= 0
     */
    public int addActorTask(long toShardingId, WorkerTask workerTask) {

        if (strategy == null) {
            log.error("you forget to config your ActorIdStrategy!");
            return -1;
        }
        int actorId = strategy.getActorId(toShardingId, workers.length);
        if (actorId < 0) {
            log.error("your task not work because actorId < 0. actorId = {}", actorId);
            return -1;
        }
        strategy.increaseUse(actorId);

        Worker worker = null;
        try {
            worker = workers[actorId];
        } catch (Exception e) {
            log.error("get worker error by actorId = {}", actorId);
            return -1;
        }

        worker.addTask(workerTask);
        return actorId;
    }


    public void addActorTaskByHashCode(Object shardingObject, WorkerTask workerTask) throws ActorBindException {
        if (shardingObject == null) {
            return;
        }
        int hashCode = shardingObject.hashCode();
        addActorTask((long) hashCode, workerTask);
    }

    @Override
    public void init() throws Throwable {
        this.workers = new Worker[workerNum];
        for (int index = 0; index < workers.length; index++) {
            this.workers[index] = new Worker(name, index, workerMqSize);
        }

        log.info("[WorkerCenter] start workers. workerCenter Name = {}, worker total size = {}", name, workers.length);
        for (Worker worker : workers) {
            worker.start();
        }
    }

    @Override
    public void create() throws Throwable {

    }

    @Override
    public void destroy() {
        log.info("[WorkerCenter] stop workers. workerCenter Name = {}, worker total size = {}", name, workers.length);
        // 先全部停止运行的状态，再关闭
        for (Worker worker : workers) {
            worker.sendStopSignal();
        }
        long globalStopNs = System.currentTimeMillis();
        for (Worker worker : workers) {
            worker.waitShutdown(globalStopNs, shutdownTimeoutMs);
        }
    }

    @Override
    public void shutdown() {

    }
}
