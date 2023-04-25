package com.neko233.toolchain.actor;

/**
 * Actor 绑定异常
 *
 * @author SolarisNeko on 2023-02-05
 **/
public class ActorBindException extends Exception {

    public ActorBindException(String msg) {
        super(msg);
    }

    public ActorBindException(String msg, Exception e) {
        super(msg, e);
    }

}
