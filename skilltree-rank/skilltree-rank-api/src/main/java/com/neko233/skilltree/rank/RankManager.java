package com.neko233.skilltree.rank;

import com.neko233.skilltree.commons.core.base.MapUtils233;

import java.util.List;
import java.util.Map;

/**
 * 排行榜
 *
 * @param <T>
 */
public interface RankManager<T extends RankData> {


    /**
     * 添加一个用户到排行榜中
     *
     * @param userId 用户 ID
     * @param score  用户得分
     */
    default void addUserScore(String businessKey,
                              String userId,
                              int score) {
        Map<String, Integer> of = MapUtils233.of(userId, score);
        addUserScoreAll(businessKey, of);
    }

    default void addUserScore(String businessKey,
                              Number userId,
                              int score) {
        addUserScore(businessKey, String.valueOf(userId), score);
    }

    /**
     * 添加一个用户到排行榜中
     *
     * @param userIdToScoreMap 用户 ID : score
     */
    void addUserScoreAll(String businessKey,
                         Map<String, Integer> userIdToScoreMap);


    /**
     * 获取排名前 N 的用户
     *
     * @param count 用户数量
     * @return 排名前 N 的用户
     */
    List<T> getUserListByTopN(String businessKey,
                              int count);

    /**
     * 获取指定用户的排名
     *
     * @param userId 用户 ID
     * @return 用户的排名，若用户不存在，则返回 -1
     */
    int getRankByUserId(String businessKey,
                        String userId);

    default int getRankByUserId(String businessKey,
                                Number userId) {
        return getRankByUserId(businessKey, String.valueOf(userId));
    }

    /**
     * 获取指定用户的得分
     *
     * @param userId 用户 ID
     * @return 用户的得分，若用户不存在，则返回 -1
     */
    int getScoreByUserId(String businessKey,
                         String userId);

    default int getScoreByUserId(String businessKey,
                                 Number userId) {
        return getScoreByUserId(businessKey, String.valueOf(userId));
    }

    /**
     * 清空排行榜
     */
    void clear(String businessKey);


}
