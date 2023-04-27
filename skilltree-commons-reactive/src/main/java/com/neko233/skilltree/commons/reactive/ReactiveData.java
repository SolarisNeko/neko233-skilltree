package com.neko233.skilltree.commons.reactive;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * set 触发 callback, get 不会
 * 线程不安全.
 * 灵感来源于响应式页面
 *
 * @author SolarisNeko
 * Date on 2023-01-02
 */
public class ReactiveData<T> {

    private final List<Consumer<T>> listeners = new ArrayList<>();
    private T data;

    public ReactiveData(T data) {
        this.data = data;
    }

    public synchronized ReactiveData<T> set(T data) {
        this.data = data;
        callback();
        return this;
    }


    public T get() {
        return this.data;
    }


    private void callback() {
        for (Consumer<T> listener : listeners) {
            listener.accept(this.data);
        }
    }

    public ReactiveData<T> addListener(Consumer<T> listener) {
        this.listeners.add(listener);
        return this;
    }

    public ReactiveData<T> removeListener(Consumer<T> listener) {
        this.listeners.remove(listener);
        return this;
    }

    public ReactiveData<T> clearAllListeners() {
        this.listeners.clear();
        return this;
    }

}
