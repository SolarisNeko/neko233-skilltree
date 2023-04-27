package com.neko233.skilltree.commons.core.base;


import java.util.ArrayList;
import java.util.List;

/**
 * 异常工具类
 */
public class ExceptionUtils233 {

    /**
     * 获得完整消息，包括异常名，消息格式为：{SimpleClassName}: {ThrowableMessage}
     *
     * @param e 异常
     * @return 完整消息
     */
    public static String getMessage(Throwable e) {
        if (null == e) {
            return null;
        }
        return StringUtils233.format("{}: {}", e.getClass().getSimpleName(), e.getMessage());
    }

    /**
     * 获得消息，调用异常类的getMessage方法
     *
     * @param e 异常
     * @return 消息
     */
    public static String getSimpleMessage(Throwable e) {
        return (null == e) ? StringUtils233.EMPTY : e.getMessage();
    }

    /**
     * 使用运行时异常包装编译异常<br>
     * <p>
     * 如果传入参数已经是运行时异常，则直接返回，不再额外包装
     *
     * @param throwable 异常
     * @return 运行时异常
     */
    public static RuntimeException wrapRuntime(Throwable throwable) {
        if (throwable instanceof RuntimeException) {
            return (RuntimeException) throwable;
        }
        return new RuntimeException(throwable);
    }

    /**
     * 将指定的消息包装为运行时异常
     *
     * @param message 异常消息
     * @return 运行时异常
     * @since 5.5.2
     */
    public static RuntimeException wrapRuntime(String message) {
        return new RuntimeException(message);
    }


    /**
     * 获取异常链上所有异常的集合，如果{@link Throwable} 对象没有cause，返回只有一个节点的List<br>
     * 如果传入null，返回空集合
     *
     * <p>
     * 此方法来自Apache-Commons-Lang3
     * </p>
     *
     * @param throwable 异常对象，可以为null
     * @return 异常链中所有异常集合
     * @since 4.6.2
     */
    public static List<Throwable> getThrowableList(Throwable throwable) {
        final List<Throwable> list = new ArrayList<>();
        while (throwable != null && false == list.contains(throwable)) {
            list.add(throwable);
            throwable = throwable.getCause();
        }
        return list;
    }

    /**
     * 获取异常链中最尾端的异常，即异常最早发生的异常对象。<br>
     * 此方法通过调用{@link Throwable#getCause()} 直到没有cause为止，如果异常本身没有cause，返回异常本身<br>
     * 传入null返回也为null
     *
     * <p>
     * 此方法来自Apache-Commons-Lang3
     * </p>
     *
     * @param throwable 异常对象，可能为null
     * @return 最尾端异常，传入null参数返回也为null
     */
    public static Throwable getRootCause(final Throwable throwable) {
        final List<Throwable> list = getThrowableList(throwable);
        return list.size() < 1 ? null : list.get(list.size() - 1);
    }

    /**
     * 获取异常链中最尾端的异常的消息，消息格式为：{SimpleClassName}: {ThrowableMessage}
     *
     * @param th 异常
     * @return 消息
     * @since 4.6.2
     */
    public static String getRootCauseMessage(final Throwable th) {
        return getMessage(getRootCause(th));
    }
}
