package com.neko233.skilltree.commons.core.base;

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

}
