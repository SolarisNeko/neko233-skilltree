package com.neko233.skilltree.commons.core.base;

import java.util.Objects;
import java.util.Optional;

public class BooleanUtils233 {

    private static final String TRUE_NUMBER = "1";

    public static Boolean valueOf(String str) {
        return "1".equals(str) ? Boolean.TRUE : Boolean.FALSE;
    }

    public static boolean isTrue(Boolean bool) {
        return Boolean.TRUE.equals(bool);
    }

    public static boolean isAnyTrue(String str) {
        String content = ObjectUtils233.getOrDefault(str, "").trim();
        if (content.equalsIgnoreCase("1")) {
            return true;
        }
        if (content.equalsIgnoreCase("success")) {
            return true;
        }
        if (content.equalsIgnoreCase("ok")) {
            return true;
        }
        if (content.equalsIgnoreCase("true")) {
            return true;
        }
        if (content.equalsIgnoreCase("yes")) {
            return true;
        }
        return false;
    }

    public static boolean isTrueStringOr1(String str) {
        String trim = Optional.ofNullable(str).orElse("0").trim().toLowerCase();
        if (Objects.equals("1", trim)) {
            return true;
        }
        return Objects.equals("true", trim);
    }

    public static boolean isFalseStringOr0(String str) {
        String trim = Optional.ofNullable(str).orElse("1").trim().toLowerCase();
        if (Objects.equals("0", trim)) {
            return true;
        }
        return Objects.equals("false", trim);
    }

    public static boolean isNotTrue(Boolean bool) {
        return !isTrue(bool);
    }

    public static boolean isFalse(Boolean bool) {
        return Boolean.FALSE.equals(bool);
    }

    public static boolean isNotFalse(Boolean bool) {
        return !isFalse(bool);
    }
}
