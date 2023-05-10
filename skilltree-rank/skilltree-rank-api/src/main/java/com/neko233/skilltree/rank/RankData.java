package com.neko233.skilltree.rank;

/**
 * 排行榜用户信息，提供基础信息：
 * <code>
 * 1. 用户 id : userId / userName
 * 2. 分数。
 * 3. 创建时间
 * 排名数据, 不应该考虑排名, 排名是从【宏观角度】观察的一个维度
 * </code>
 */
public interface RankData {


    /**
     * @return userId / username
     */
    String getId();


    /**
     * @return 得分
     */
    long getScore();


    /**
     * @return 创建时间戳, 毫秒
     */
    long createMs();


}