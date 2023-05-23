package com.neko233.skilltree.global.lock.mysql.utils;

import java.util.Arrays;

public class JdbcHelper {
    public static void gracefulClose(AutoCloseable... closeables) {
        Arrays.asList(closeables).forEach(closeable -> {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception e) {
                }
            }
        });
    }
}
