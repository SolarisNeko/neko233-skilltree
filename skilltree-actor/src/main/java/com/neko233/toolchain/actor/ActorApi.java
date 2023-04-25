package com.neko233.toolchain.actor;

/**
 * @author SolarisNeko on 2023-02-05
 **/
public interface ActorApi {

    /**
     * @return 获取唯一标识, 用于非 actorId 场景
     */
    Long toCalculateActorId();

    /**
     * @return 获取 actor 线程 id (index)
     */
    Integer getActorThreadId();

    /**
     * 设置 actor 线程 id
     *
     * @param actorId 线程id (index)
     */
    void setActorThreadId(int actorId);

}
