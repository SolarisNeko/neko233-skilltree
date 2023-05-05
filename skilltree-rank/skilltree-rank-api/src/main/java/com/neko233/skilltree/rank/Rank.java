package com.neko233.skilltree.rank;

import java.util.List;

/**
 * 排行榜
 *
 * @param <T>
 */
public interface Rank<T extends RankData<T>> {


    /**
     * 添加一个用户到排行榜中
     *
     * @param userId 用户 ID
     * @param score  用户得分
     */
    void addUser(String userId, int score);

    default void addUser(Number userId, int score) {
        addUser(String.valueOf(userId), score);
    }

    /**
     * 获取排名前 N 的用户
     *
     * @param count 用户数量
     * @return 排名前 N 的用户
     */
    List<T> getUserListByTopN(int count);

    /**
     * 获取指定用户的排名
     *
     * @param userId 用户 ID
     * @return 用户的排名，若用户不存在，则返回 -1
     */
    int getRankByUserId(String userId);

    default int getRankByUserId(Number userId) {
        return getRankByUserId(String.valueOf(userId));
    }

    /**
     * 获取指定用户的得分
     *
     * @param userId 用户 ID
     * @return 用户的得分，若用户不存在，则返回 -1
     */
    int getScore(String userId);

    default int getScore(Number userId) {
        return getScore(String.valueOf(userId));
    }

    /**
     * 清空排行榜
     */
    void clear();


}
