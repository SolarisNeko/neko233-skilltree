package com.neko233.skilltree.commons.core.exception;

/**
 * @author SolarisNeko
 * Date on 2023-01-02
 */
@FunctionalInterface
public interface ThrowableHandler {


    void handle(Throwable t);

}
