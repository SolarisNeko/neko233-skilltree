package com.neko233.toolchain.distribute_system.raft;

/**
 * Raft 集群状态
 */
public enum RaftState {

    LEADER,

    FOLLOWER,

    CANDIDATE,

}
