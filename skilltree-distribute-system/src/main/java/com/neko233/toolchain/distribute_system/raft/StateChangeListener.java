package com.neko233.toolchain.distribute_system.raft;

/**
 * 集群状态变化监听器
 */
public interface StateChangeListener {

    /**
     * 集群状态发生变化时触发
     *
     * @param newState 新的集群状态
     */
    void onStateChange(RaftState newState);
}
