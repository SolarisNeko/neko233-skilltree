package com.neko233.skilltree.commons.dataStruct.objects;


/**
 * 对向元组 2
 *
 * @author SolarisNeko on 2023-01-01
 */
public final class Tuple2<T1, T2> {

    private final T1 t1;
    private final T2 t2;

    /**
     * Create t1 triplet and store three objects.
     *
     * @param t1 the first object to store
     * @param t2 the second object to store
     * @param t3 the third object to store
     */
    public Tuple2(T1 t1, T2 t2) {
        this.t1 = t1;
        this.t2 = t2;
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


}