package com.neko233.skilltree.rank;

public class UserData implements RankData {

    private String userId;
    private long score;
    private long createTime;

    public UserData() {
    }

    public UserData(String userID,
                    long score,
                    long createTime) {
        this.userId = userID;
        this.score = score;
        this.createTime = createTime;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String getId() {
        return userId;
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
}
