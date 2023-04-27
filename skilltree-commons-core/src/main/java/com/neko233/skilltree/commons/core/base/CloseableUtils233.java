package com.neko233.skilltree.commons.core.base;

import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;

/**
 * close must success, if error no one can help you..
 */
@Slf4j
public class CloseableUtils233 {

    public static void close(AutoCloseable... autoCloseables) {
        for (AutoCloseable autoCloseable : autoCloseables) {
            if (autoCloseable == null) {
                return;
            }
            try {
                autoCloseable.close();
            } catch (Exception e) {
                log.error("close error. class name = {}", autoCloseable.getClass(), e);
            }
        }
    }

    public static void close(Closeable... autoCloseables) {
        for (AutoCloseable autoCloseable : autoCloseables) {
            if (autoCloseable == null) {
                return;
            }
            try {
                autoCloseable.close();
            } catch (Exception e) {
                log.error("close error. name = {}", autoCloseable.getClass(), e);
            }
        }
    }

}
