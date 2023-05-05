package com.neko233.skilltree.rank.impl;

import com.neko233.skilltree.rank.RankData;

public class RedisRankData<UID extends Comparable<UID>>
        implements RankData<UID> {

    @Override
    public UID getId() {
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
