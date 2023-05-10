package com.neko233.skilltree.rank.util;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;

public class RedisUtil {

    private static RedisClient redisClient;

    private static ClientResources clientResources;

    private static StatefulRedisConnection<String, String> connection;

    public static RedisCommands init() {
        clientResources = DefaultClientResources.create();
        redisClient = RedisClient.create(clientResources, "redis://127.0.0.1:6379");
        connection = redisClient.connect();
        return connection.sync();
    }

    /**
     * 关闭Redis连接
     */
    public static void close() {
        if (connection != null) {
            connection.close();
        }
        if (redisClient != null) {
            redisClient.shutdown();
        }

        if (clientResources != null) {
            clientResources.shutdown();
        }
    }

}