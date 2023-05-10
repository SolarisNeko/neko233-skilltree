package com.neko233.skilltree.rank;

import com.neko233.skilltree.rank.impl.RedisRankData;

public class UserData extends RedisRankData<UserData> implements Comparable<UserData> {

    private String userID;
    private long score;
    private long createTime;

    public UserData() {
    }

    public UserData(String userID, long score, long createTime) {
        this.userID = userID;
        this.score = score;
        this.createTime = createTime;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    @Override
    public UserData getId() {
        return this;
    }

    @Override
    public long getScore() {
        return score;
    }

    @Override
    public long createMs() {
        return createTime;
    }

    public void setScore(long score) {
        this.score = score;
    }


    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @Override
    public int compareTo(UserData o) {
        return 0;
    }
}
