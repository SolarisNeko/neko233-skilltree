package com.neko233.toolchain.idGenerator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * IdGenerator 管理
 *
 * @author SolarisNeko on 2022-12-29
 **/
public class IdGeneratorManager {

    private static final Map<String, IdGenerator> cache = new ConcurrentHashMap<>();


    public static boolean register(String businessName, IdGenerator idGenerator) {
        cache.put(businessName, idGenerator);
        return true;
    }

    public static IdGenerator get(String businessName) {
        return cache.get(businessName);
    }


}
