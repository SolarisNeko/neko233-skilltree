package com.neko233.toolchain.actor.strategy;

/**
 * @author SolarisNeko on 2023-02-05
 **/
public class ShardingActorIdStrategy implements ActorIdStrategy {

    public static final ActorIdStrategy singleton = new ShardingActorIdStrategy();

    private ShardingActorIdStrategy() {
    }

    public int getActorId(long toShardingId, int workerLength) {
        return toShardingId == Integer.MIN_VALUE ? 0 : (int) (Math.abs(toShardingId) % workerLength);
    }

    @Override
    public void increaseUse(int actorId, int count) {

    }

    @Override
    public void minusUse(int actorId, int count) {

    }

}
