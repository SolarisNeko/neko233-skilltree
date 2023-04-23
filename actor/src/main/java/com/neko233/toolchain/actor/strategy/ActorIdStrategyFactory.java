package com.neko233.toolchain.actor.strategy;


/**
 * @author SolarisNeko on 2023-02-05
 **/
public interface ActorIdStrategyFactory {

    static ActorIdStrategy choose(String name) {
        // sharding
        if (ActorIdStrategyConstant.SHARDING.equalsIgnoreCase(name)) {
            return ShardingActorIdStrategy.singleton;
        }
        // load balance
        if (ActorIdStrategyConstant.LOAD_BALANCE.equalsIgnoreCase(name)) {
            return LoadBalanceActorIdStrategy.singleton;
        }

        // (default) sharding
        return ShardingActorIdStrategy.singleton;
    }
}
