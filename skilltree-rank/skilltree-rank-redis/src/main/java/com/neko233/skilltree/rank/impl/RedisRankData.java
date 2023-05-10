package com.neko233.skilltree.rank.impl;

import com.neko233.skilltree.rank.RankData;

import java.rmi.server.UID;

public class RedisRankData
        implements RankData {

    @Override
    public String getId() {
        return null;
    }

    @Override
    public long getScore() {
        return 0;
    }

    @Override
    public long createMs() {
        return 0;
    }

}
