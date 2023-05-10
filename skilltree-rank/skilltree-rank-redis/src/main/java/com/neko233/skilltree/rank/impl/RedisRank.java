package com.neko233.skilltree.rank.impl;

import com.neko233.skilltree.rank.Rank;
import com.neko233.skilltree.rank.RankData;
import com.neko233.skilltree.rank.UserData;
import com.neko233.skilltree.rank.util.RedisUtil;
import io.lettuce.core.ScoredValue;
import io.lettuce.core.api.sync.RedisCommands;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RedisRank<T extends RankData<T>>
        implements Rank<T> {

    @Override
    public void addUser(String userId, int score) {
        RedisCommands commands = RedisUtil.init();
        String finalScore = String.valueOf(score) + System.currentTimeMillis();
        commands.zadd("rank", Long.parseLong(finalScore), userId);
        RedisUtil.close();
    }

    // TODO 有问题，需要再研究
    public void addManyUser(Map<String, Double> userList) {
        RedisCommands commands = RedisUtil.init();
        commands.zadd("rank", userList);
        RedisUtil.close();
    }

    @Override
    public List<T> getUserListByTopN(int count) {
        RedisCommands commands = RedisUtil.init();
        List<ScoredValue> rank = commands.zrevrangeWithScores("rank", 0, count);
        if (rank.size() == 0) {
            return null;
        }
        List<UserData> result = new ArrayList<>();
        for (ScoredValue args : rank) {
            String[] scoreAndTime = scoreDispose(args.getScore());
            result.add(new UserData(String.valueOf(args.getValue()), Long.parseLong(scoreAndTime[0]), Long.parseLong(scoreAndTime[1])));
        }
        RedisUtil.close();
        // TODO 这里泛型很奇怪
        return (List<T>) result;
    }

    // 获取中间排名low-high的用户信息
    public List<T> getUserListByLimit(int low,int high) {
        RedisCommands commands = RedisUtil.init();
        List<ScoredValue> rank = commands.zrevrangeWithScores("rank", low-1, high-1);
        if (rank.size() == 0) {
            return null;
        }
        List<UserData> result = new ArrayList<>();
        for (ScoredValue args : rank) {
            String[] scoreAndTime = scoreDispose(args.getScore());
            result.add(new UserData(String.valueOf(args.getValue()), Long.parseLong(scoreAndTime[0]), Long.parseLong(scoreAndTime[1])));
        }
        RedisUtil.close();
        // TODO 这里泛型很奇怪
        return (List<T>) result;
    }

    @Override
    public int getRankByUserId(String userId) {
        RedisCommands commands = RedisUtil.init();
        Long rank = commands.zrevrank("rank", userId) + 1; // 排名从0开始计算
        RedisUtil.close();
        return Integer.parseInt(String.valueOf(rank));
    }

    @Override
    public int getScore(String userId) {
        RedisCommands commands = RedisUtil.init();
        Double rank = commands.zscore("rank", userId);
        RedisUtil.close();
        String[] strings = scoreDispose(rank);
        return Integer.parseInt(strings[0]);
    }

    @Override
    public void clear() {
        RedisCommands commands = RedisUtil.init();
        commands.zremrangebyrank("rank", 0, -1);
        RedisUtil.close();
    }

    // 工具方法：返回数据的得分和时间戳
    public String[] scoreDispose(Double initScore) {
        String[] result = new String[2];
        String s = BigDecimal.valueOf(initScore).toString();
        result[0] = s.substring(0, s.length() - 13);    // 分数
        result[1] = s.substring(s.length() - 13, s.length());    // 时间戳
        return result;
    }
}
