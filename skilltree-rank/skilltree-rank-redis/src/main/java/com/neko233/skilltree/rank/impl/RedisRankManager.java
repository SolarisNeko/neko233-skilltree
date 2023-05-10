package com.neko233.skilltree.rank.impl;

import com.neko233.skilltree.commons.core.base.CollectionUtils233;
import com.neko233.skilltree.rank.RankManager;
import com.neko233.skilltree.rank.UserData;
import com.neko233.skilltree.rank.util.RedisUtil;
import io.lettuce.core.ScoredValue;
import io.lettuce.core.api.sync.RedisCommands;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RedisRankManager
        implements RankManager<UserData> {

    @Override
    public void addUserScoreAll(String businessKey,
                                Map<String, Integer> userIdToScoreMap) {
        RedisCommands<String, String> commands = RedisUtil.init();
        userIdToScoreMap.forEach((userId, score) -> {
            String finalScore = String.valueOf(score) + System.currentTimeMillis();
            commands.zadd(businessKey, Long.parseLong(finalScore), userId);
        });
        RedisUtil.close();
    }

    // TODO 有问题，需要再研究
    public void addManyUser(String businessKey,
                            Map<String, Double> userList) {
        RedisCommands<String, String> commands = RedisUtil.init();
        commands.zadd(businessKey, userList);
        RedisUtil.close();
    }

    @Override
    public List<UserData> getUserListByTopN(String businessKey,
                                            int count) {
        RedisCommands<String, String> commands = RedisUtil.init();
        List<ScoredValue<String>> rank = commands.zrevrangeWithScores(businessKey, 0, count);
        if (rank.size() == 0) {
            return null;
        }
        List<UserData> result = new ArrayList<>();
        for (ScoredValue<String> args : rank) {
            String[] scoreAndTime = scoreDispose(args.getScore());
            UserData userData = new UserData(String.valueOf(args.getValue()),
                    Long.parseLong(scoreAndTime[0]),
                    Long.parseLong(scoreAndTime[1]));
            result.add(userData);
        }
        RedisUtil.close();
        // TODO 这里泛型很奇怪
        return result;
    }

    // 获取中间排名low-high的用户信息
    public List<UserData> getUserListByLimit(String businessKey,
                                             int low,
                                             int high) {
        RedisCommands<String, String> commands = RedisUtil.init();
        List<ScoredValue<String>> rankList = commands.zrevrangeWithScores(businessKey, low - 1, high - 1);
        // FIXME 为什么不用工具？ 你确定不会 NPE ?
//        if (CollectionUtils233.isEmpty(rankList)) {
        if (rankList.size() == 0) {
            return null;
        }
        List<UserData> result = new ArrayList<>();
        for (ScoredValue<String> args : rankList) {
            String[] scoreAndTime = scoreDispose(args.getScore());

            // FIXME 此处需要优化, 过于乐观, 特别是 String -> Number
            UserData e = new UserData(String.valueOf(args.getValue()),
                    Long.parseLong(scoreAndTime[0]),
                    Long.parseLong(scoreAndTime[1]));
            result.add(e);
        }
        RedisUtil.close();

        // TODO 这里泛型很奇怪
        return result;
    }

    @Override
    public int getRankByUserId(String businessKey,
                               String userId) {
        RedisCommands<String, String> commands = RedisUtil.init();
        Long rank = commands.zrevrank(businessKey, userId) + 1; // 排名从0开始计算
        RedisUtil.close();
        return Integer.parseInt(String.valueOf(rank));
    }

    @Override
    public int getScoreByUserId(String businessKey,
                                String userId) {
        RedisCommands<String, String> commands = RedisUtil.init();
        Double rank = commands.zscore(businessKey, userId);
        RedisUtil.close();
        String[] strings = scoreDispose(rank);
        return Integer.parseInt(strings[0]);
    }

    @Override
    public void clear(String businessKey) {
        RedisCommands<String, String> commands = RedisUtil.init();
        commands.zremrangebyrank(businessKey, 0, -1);
        RedisUtil.close();
    }

    // 工具方法：返回数据的得分和时间戳
    public String[] scoreDispose(Double initScore) {
        String[] result = new String[2];
        String s = BigDecimal.valueOf(initScore).toString();
        // TODO 命名过于随便, 而且返回数组不好，封装成一个通用对象.
        result[0] = s.substring(0, s.length() - 13);    // 分数
        result[1] = s.substring(s.length() - 13);    // 时间戳
        return result;
    }
}
