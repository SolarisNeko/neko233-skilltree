package com.neko233.toolchain.commons.storage.async_dao;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

/**
 * 你的数据对象, 需要 implements DataUniqueId
 * 数据同步 API - sync / async
 *
 * @author SolarisNeko on 2023-02-05
 **/
public interface DataSyncApi<T extends DataUniqueId<T>> {

    T getByUniqueId(DataUniqueId<T> uniqueId);

    List<T> getAll();

    boolean syncData(T data);

    boolean asyncData(T data);

    /**
     * insert or update
     * @return isOk
     */
    default boolean insertOrUpdate(T data) {
        return insertOrUpdate(Collections.singletonList(data));
    }

    /**
     * sync / async 公用刷盘逻辑 = insert if duplicate will update
     *
     * @param dataList 数据
     */
    boolean insertOrUpdate(final List<T> dataList);

    BiFunction<T, T, T> mergeNewData();

}
