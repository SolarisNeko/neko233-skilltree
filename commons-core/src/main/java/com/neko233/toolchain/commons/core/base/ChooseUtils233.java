package com.neko233.toolchain.commons.core.base;

/**
 * @author SolarisNeko on 2023-01-01
 */
public class ChooseUtils233 {

    public static <T> T choose(boolean condition, T ifTrue, T ifFalse) {
        if (condition) {
            return ifTrue;
        }
        return ifFalse;
    }
}
