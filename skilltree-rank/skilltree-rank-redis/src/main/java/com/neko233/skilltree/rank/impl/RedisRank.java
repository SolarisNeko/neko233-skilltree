package com.neko233.skilltree.rank.impl;

import com.neko233.skilltree.rank.Rank;
import com.neko233.skilltree.rank.RankData;

import java.util.List;

public class RedisRank<T extends RankData<T>>
        implements Rank<T> {

    @Override
    public void addUser(String userId, int score) {

    }

    @Override
    public List<T> getUserListByTopN(int count) {
        return null;
    }

    @Override
    public int getRankByUserId(String userId) {
        return 0;
    }

    @Override
    public int getScore(String userId) {
        return 0;
    }

    @Override
    public void clear() {

    }
}
