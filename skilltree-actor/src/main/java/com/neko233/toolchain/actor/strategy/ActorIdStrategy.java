package com.neko233.toolchain.actor.strategy;

/**
 * @author SolarisNeko on 2023-02-05
 **/
public interface ActorIdStrategy {

    /**
     * LoadBalance
     *
     * @param toShardingId 不需要
     * @param workerLength 可用的工作线程长度
     * @return actorId. -1 error / >= 0 ok
     */
    int getActorId(long toShardingId, int workerLength);

    // ------------------------------  Metrics API ---------------------------------

    default void increaseUse(int actorId) {
        increaseUse(actorId, 1);
    }

    void increaseUse(int actorId, int count);

    default void minusUse(int actorId) {
        minusUse(actorId, 1);
    }

    void minusUse(int actorId, int count);

}
