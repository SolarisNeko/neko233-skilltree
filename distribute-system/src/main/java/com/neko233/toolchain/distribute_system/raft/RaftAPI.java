package com.neko233.toolchain.distribute_system.raft;

public interface RaftAPI {

    /**
     * 添加一个节点到 Raft 集群
     *
     * @param node 节点的地址
     */
    void addNode(String node);

    /**
     * 从 Raft 集群中删除一个节点
     *
     * @param node 节点的地址
     */
    void removeNode(String node);

    /**
     * 获取当前 Raft 集群中 Leader 的地址
     *
     * @return Leader 节点的地址
     */
    String getLeader();

    /**
     * 向 Raft 集群中提交一条日志
     *
     * @param log 日志内容
     */
    void appendLog(String log);

    /**
     * 获取当前 Raft 集群的状态
     *
     * @return Raft 集群的状态
     */
    RaftState getState();

    /**
     * 注册一个监听器，当 Leader 发生变化时会触发
     *
     * @param listener Leader 变化监听器
     */
    void addLeaderChangeListener(LeaderChangeListener listener);

    /**
     * 注册一个监听器，当集群状态发生变化时会触发
     *
     * @param listener 集群状态变化监听器
     */
    void addStateChangeListener(StateChangeListener listener);
}
