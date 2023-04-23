package com.neko233.toolchain.distribute_system.raft;

/**
 * Leader 变化监听器
 */
public interface LeaderChangeListener {

    /**
     * Leader 发生变化时触发
     *
     * @param newLeader 新的 Leader 节点地址
     */
    void onLeaderChange(String newLeader);
}