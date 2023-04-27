package com.neko233.skilltree.actor;

public class ActorUser implements ActorApi {

    private Long userId;
    private Integer actorId;

    public ActorUser(Long userId) {
        this.userId = userId;
    }

    @Override
    public Long toCalculateActorId() {
        return userId;
    }

    @Override
    public Integer getActorThreadId() {
        return actorId;
    }

    @Override
    public void setActorThreadId(int actorId) {
        this.actorId = actorId;
    }
}
