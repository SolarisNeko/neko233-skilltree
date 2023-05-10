package com.neko233;


import com.neko233.skilltree.rank.UserData;
import com.neko233.skilltree.rank.impl.RedisRank;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class RedisRankTest extends TestCase {

    // 添加单个User测试
    public void testAddUser() {
        RedisRank redisRank = new RedisRank<UserData>();
        redisRank.addUser("2-", 2);

    }

    // 清空排行榜测试
    public void testClear() {
        RedisRank redisRank = new RedisRank<UserData>();
        redisRank.clear();
    }

    // 获取排行榜中前N个用户测试
    public void testGetUserListByTopN() {
        RedisRank redisRank = new RedisRank<UserData>();
        redisRank.getUserListByTopN(10);
    }
    // 获取排行榜中low-high用户测试
    public void testGetUserListByLimit() {
        RedisRank redisRank = new RedisRank<UserData>();
        redisRank.getUserListByLimit(1,2);
    }

    // 获取指定用户分数测试
    public void testGetScore() {
        RedisRank redisRank = new RedisRank<UserData>();
        int score = redisRank.getScore(2);
        System.out.println(score);
    }

    // 获取指定用户的排名测试
    public void testGetRankByUserId() {
        RedisRank redisRank = new RedisRank<UserData>();
        int score = redisRank.getRankByUserId(1);
        System.out.println(score);
    }

    // 添加多个User测试（有问题）
    public void testAddManyUser() {
        RedisRank redisRank = new RedisRank<UserData>();
        Map<String, Double> map = new HashMap<>();
        map.put("huangqian", 10.0);
        map.put("ypb", 90.0);
        redisRank.addManyUser(map);
    }
}