package com.neko233.skilltree.commons.storage.async_dao;


import com.neko233.skilltree.commons.core.base.StringUtils233;

import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author SolarisNeko on 2023-02-05
 **/
public interface DataUniqueId<T> {

    String UNIQUE_ID_SPLIT = ",";

    // -------------- API --------------

    /**
     * @return 该数据唯一标识, 常用 StringUtils233.join() 生成
     */
    String uniqueId();

    default String generateUniqueId(Object... objs) {
        if (objs == null || objs.length == 0) {
            return "";
        }
        return StringUtils233.join(UNIQUE_ID_SPLIT, objs);
    }


    /**
     * diy name : Object
     *
     * @return dao 操作参数.
     */
    Map<String, Object> daoOperateParams();

    /**
     * 合并数据函数, 用于合并同一个 uniqueId 的数据, 若没有, 则默认采用 overwrite 方式
     *
     * @return t1, t2 -> t-result
     */
    BiFunction<T, T, T> mergeDataFunction();
}
