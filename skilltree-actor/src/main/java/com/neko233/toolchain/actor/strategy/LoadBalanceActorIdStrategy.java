package com.neko233.toolchain.actor.strategy;

import com.neko233.toolchain.commons.core.base.MapUtils233;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SolarisNeko on 2023-02-05
 **/
public class LoadBalanceActorIdStrategy implements ActorIdStrategy {

    public static final ActorIdStrategy singleton = new LoadBalanceActorIdStrategy();

    private final Map<Integer, Integer> lbMap = new ConcurrentHashMap<>();

    private LoadBalanceActorIdStrategy() {
    }


    public int getActorId(long toShardingId, int workerLength) {
        checkInit(workerLength);

        // 返回负载最小的
        Integer actorId = findKeyByMinValue(lbMap);
        if (actorId == null) {
            return -1;
        }
        increaseUse(actorId);
        // lb 计数
        return actorId;
    }


    @Override
    public void increaseUse(int actorId, int count) {
        lbMap.merge(actorId, count, Integer::sum);
    }

    @Override
    public void minusUse(int actorId, int count) {
        lbMap.merge(actorId, -1, (v1, v2) -> {
            int sum = v1 + v2;
            return Math.max(sum, 0);
        });
    }

    private void checkInit(int workerLength) {
        if (MapUtils233.isEmpty(lbMap)) {
            synchronized (this) {
                if (MapUtils.isEmpty(lbMap)) {
                    for (int i = 0; i < workerLength; i++) {
                        lbMap.put(i, 0);
                    }
                }
            }
        }
    }

    private Integer findKeyByMinValue(Map<Integer, Integer> lbMap) {
        Integer key = null;
        Integer value = null;
        for (Map.Entry<Integer, Integer> entry : lbMap.entrySet()) {
            // zero load
            if (entry.getValue() <= 1) {
                return entry.getKey();
            }
            if (key == null) {
                key = entry.getKey();
            }
            if (value == null) {
                value = entry.getValue();
            }

            if (value > entry.getValue()) {
                key = entry.getKey();
                value = entry.getValue();
            }
        }
        return key;
    }


    private void initData(Map<Integer, Integer> lbMap, int workerLength) {
        for (int i = 0; i < workerLength; i++) {
            lbMap.put(i, 0);
        }
    }

}
