package com.neko233.skilltree.commons.core.base;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author SolarisNeko
 * Date on 2023-04-13
 */
public class ListUtils233 {

    @SafeVarargs
    public static <V> List<V> of(V... objs) {
        return CollectionUtils233.ofList(objs);
    }

    /**
     * @param list  列表
     * @param index 支持 +/- number
     * @return data
     */
    public static <T> T get(List<T> list, int index) {
        if (null == list) {
            return null;
        }

        // reverse
        if (index < 0) {
            index += list.size();
        }

        return list.get(index);
    }

    /**
     * @param list       列表
     * @param humanIndex 从 1 开始
     * @return data
     */
    public static Type getByHumanIndex(List<Type> list, int humanIndex) {
        if (humanIndex == 0) {
            return get(list, 0);
        }
        return get(list, humanIndex - 1);
    }
}
