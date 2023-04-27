package com.neko233.skilltree.commons.storage.async_dao;


import com.neko233.skilltree.commons.core.annotation.Experimental;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;

/**
 * 抽象数据同步 DAO API, 具体写操作由外部实现
 * ps: 不允许用作 Analysis 类 DAO
 *
 * @author SolarisNeko on 2023-02-05
 **/
@Experimental(comment = "异步 DAO, 读取最新数据覆盖")
public abstract class AbstractCacheDataSyncDao<T extends DataUniqueId<T>> implements DataSyncApi<T> {

    // 全局数据同步 scheduler
    private static final ScheduledExecutorService GLOBAL_DATA_SYNC_SCHEDULER = Executors.newScheduledThreadPool(1);

    private final BlockingQueue<T> mq;

    private final Map<String, T> asyncWriteCache = new ConcurrentHashMap<>();

    public AbstractCacheDataSyncDao(int mqMaxSize, DataSyncScheduleParamDto dataSyncScheduleParamDto) {
        dataSyncScheduleParamDto = dataSyncScheduleParamDto == null ? DataSyncScheduleParamDto.temp() : dataSyncScheduleParamDto;

        int initialDelay = Math.max(0, dataSyncScheduleParamDto.getInitDelay());
        int period = Math.max(10, dataSyncScheduleParamDto.getPeriod());
        TimeUnit scheduleTimeUnit = Optional.ofNullable(dataSyncScheduleParamDto.getTimeUnit()).orElse(TimeUnit.SECONDS);

        this.mq = new ArrayBlockingQueue<>(Math.max(10, mqMaxSize));

        // scheduler
        GLOBAL_DATA_SYNC_SCHEDULER.scheduleAtFixedRate(() -> {
            flushAsyncWriteData();
        }, initialDelay, period, scheduleTimeUnit);

        Thread preShutdownThread = new Thread(this::flushAsyncWriteData);
        Runtime.getRuntime().addShutdownHook(preShutdownThread);
    }

    private void flushAsyncWriteData() {
        final List<T> tempDataList = new ArrayList<>(mq.size());
        mq.drainTo(tempDataList);
        insertOrUpdate(tempDataList);

        for (T t : tempDataList) {
            asyncWriteCache.remove(t.uniqueId());
        }

        tempDataList.clear();
    }

    public List<T> getAllAsyncWriteData() {
        return new ArrayList<>(asyncWriteCache.values());
    }


    public T getAsyncWriteData(DataUniqueId<T> queryData) {
        return asyncWriteCache.get(queryData.uniqueId());
    }

    @Override
    public boolean syncData(T data) {
        insertOrUpdate(data);
        return false;
    }

    @Override
    public boolean asyncData(T data) {
        boolean isOk = mq.offer(data);
        if (isOk) {
            asyncWriteCache.merge(data.uniqueId(), data, mergeNewData());
        }
        return isOk;
    }


}
