package com.neko233.skilltree.commons.actionChain;

@FunctionalInterface
public interface ExceptionAction {

    void handleException(Throwable e);

}