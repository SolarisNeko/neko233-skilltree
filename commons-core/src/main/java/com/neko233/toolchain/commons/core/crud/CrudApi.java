package com.neko233.toolchain.commons.core.crud;

import java.util.Collection;
import java.util.Collections;

/**
 * 增删改查 <br>
 * all API is OOP
 *
 * @author SolarisNeko on 2023-01-05
 **/
public interface CrudApi<T> {

    /**
     * add
     */
    default boolean insert(T item) {
        return insert(Collections.singleton(item));
    }

    boolean insert(Collection<T> item);


    /**
     * delete
     */
    default boolean delete(T item) {
        return delete(Collections.singleton(item));
    }


    boolean delete(Collection<T> item);


    /**
     * update
     */
    default boolean update(T item) {
        return update(Collections.singleton(item));
    }

    boolean update(Collection<T> item);


    /**
     * get
     */
    default boolean select(T item) {
        return select(Collections.singleton(item));
    }

    boolean select(Collection<T> item);


}
