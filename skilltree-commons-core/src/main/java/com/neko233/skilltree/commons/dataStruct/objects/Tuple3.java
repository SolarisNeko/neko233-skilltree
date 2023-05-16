package com.neko233.skilltree.commons.dataStruct.objects;


import com.neko233.skilltree.commons.core.annotation.ThreadSafe;

/**
 * 对向元组 3
 * T1~T3 = 按顺序的元素
 *
 * @author SolarisNeko on 2023-01-01
 */
@ThreadSafe
public final class Tuple3<T1, T2, T3> {

    private final T1 t1;
    private final T2 t2;
    private final T3 t3;

    /**
     * Create t1 triplet and store three objects.
     *
     * @param t1 the first object to store
     * @param t2 the second object to store
     * @param t3 the third object to store
     */
    public Tuple3(T1 t1, T2 t2, T3 t3) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
    }

    /**
     * Returns the first stored object.
     *
     * @return first object stored
     */
    public final T1 getT1() {
        return t1;
    }

    /**
     * Returns the second stored object.
     *
     * @return second object stored
     */
    public final T2 getT2() {
        return t2;
    }

    /**
     * Returns the third stored object.
     *
     * @return third object stored
     */
    public final T3 getT3() {
        return t3;
    }

}