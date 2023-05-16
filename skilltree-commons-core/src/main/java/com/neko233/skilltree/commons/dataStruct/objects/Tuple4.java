package com.neko233.skilltree.commons.dataStruct.objects;


import com.neko233.skilltree.commons.core.annotation.ThreadSafe;

/**
 * 对向元组 3
 * T1~T3 = 按顺序的元素
 *
 * @author SolarisNeko on 2023-01-01
 */
@ThreadSafe
public final class Tuple4<T1, T2, T3, T4> {

    private final T1 t1;
    private final T2 t2;
    private final T3 t3;
    private final T4 t4;

    public Tuple4(T1 t1, T2 t2, T3 t3, T4 t4) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
    }

    public final T1 getT1() {
        return t1;
    }

    public final T2 getT2() {
        return t2;
    }

    public final T3 getT3() {
        return t3;
    }

    public T4 getT4() {
        return t4;
    }
}