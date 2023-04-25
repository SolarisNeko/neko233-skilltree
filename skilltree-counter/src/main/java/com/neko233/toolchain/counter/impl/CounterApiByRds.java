package com.neko233.toolchain.counter.impl;

import com.neko233.toolchain.commons.core.base.CollectionUtils233;
import com.neko233.toolchain.counter.CounterApi;
import com.neko233.toolchain.counter.CounterData;
import com.neko233.toolchain.sql.SqlTemplate;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @since 0.1.8
 */
@Slf4j
public class CounterApiByRds implements CounterApi {

    public static final String CREATE_TABLE = "create table if not exists counter_api " +
            "( " +
            "    `id`              varchar(32) not null comment 'id | 兼容更多系统, 做成文本', " +
            "    `type`            varchar(32) not null comment '类型', " +
            "    `cnt`             bigint      not null default 0 comment '数量', " +
            "    `next_refresh_ms` bigint      not null default -1 comment '下一次刷新时间', " +
            "    primary key (`id`, `type`) " +
            ")";
    public static final String SELECT_VALUE_SQL = "select type, cnt from counter_api where id = ? and type in (?) ";
    public static final String UPSERT_SQL = "Insert into counter_api(`id`, `type`, `cnt`, `next_refresh_ms`) values( ${id}, ${type}, ${cnt}, ${next_refresh_ms} ) " +
            " on duplicate key update cnt = ${cnt} ";
    /**
     * 异步调度
     */
    private static final int ASYNC_SCHEDULE_PERIOD_SECONDS = 10;
    private static final String SELECT_SQL_LIMIT_1 = "select `id`, `type`, `cnt`, `next_refresh_ms` from counter_api where `id` = ? and `type` = ? limit 0, 1";
    private final DataSource dataSource;
    // async
    private boolean async;
    private ScheduledExecutorService scheduler;
    private LinkedBlockingQueue<CounterData> queue;

//    public static final String DROP_TABLE = "drop table if exists counter_api ";
//
//    private void dropTableForTest() {
//        try (Connection connection = dataSource.getConnection()) {
//            PreparedStatement ps = connection.prepareStatement(DROP_TABLE);
//            ps.execute();
//        } catch (Exception e) {
//            log.error("drop table error", e);
//        }
//    }

    /**
     * Constructor
     *
     * @param dataSource 数据库
     */
    public CounterApiByRds(DataSource dataSource) {
        this(dataSource, false);
    }

    /**
     * if async, you maybe lose your data..
     *
     * @param dataSource ds
     * @param async      bool
     */
    public CounterApiByRds(DataSource dataSource,
                           boolean async) {
        assert dataSource != null;
        this.dataSource = dataSource;
        this.async = async;
        if (this.async) {
            queue = new LinkedBlockingQueue<>();
            scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
                Thread thread = new Thread(r);
                thread.setName("single");
                return thread;
            });

            scheduler.scheduleAtFixedRate(() -> {
                if (CollectionUtils233.isEmpty(queue)) {
                    return;
                }
                List<CounterData> tempList = new ArrayList<>();
                queue.drainTo(tempList);
                setInternal(tempList);
                tempList.clear();
            }, 0, ASYNC_SCHEDULE_PERIOD_SECONDS, TimeUnit.SECONDS);
        }

        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(CREATE_TABLE);
            ps.execute();
        } catch (Exception e) {
            log.error("create table error", e);
        }
    }

    @Override
    public CounterData get(String id,
                           String type) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SELECT_SQL_LIMIT_1);
            ps.setObject(1, id);
            ps.setObject(2, type);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return CounterData.builder()
                        .id(rs.getString("id"))
                        .type(rs.getString("type"))
                        .count(rs.getLong("cnt"))
                        .nextRefreshMs(rs.getLong("next_refresh_ms"))
                        .build();
            }
        } catch (Exception e) {
            log.error("[{}] get error.", this.getClass().getSimpleName(), e);
            return null;
        }
        return null;
    }

    @Override
    public Map<String, Number> getValue(String id,
                                        Collection<String> types) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SELECT_VALUE_SQL);
            ps.setObject(1, id);
            ps.setObject(2, String.join(",", types));
            ResultSet rs = ps.executeQuery();
            Map<String, Number> map = new HashMap<>();
            while (rs.next()) {
                map.put(rs.getString("type"), rs.getLong("cnt"));
            }
            return map;
        } catch (Exception e) {
            log.error("[{}] get error.", this.getClass().getSimpleName(), e);
            return new HashMap<>();
        }
    }

    @Override
    public boolean set(String id,
                       Map<String, Number> typeCountMap,
                       long nextRefreshMs) {

        List<CounterData> collect = typeCountMap.entrySet().stream()
                .map(entry -> CounterData.builder()
                        .id(id)
                        .type(entry.getKey())
                        .count(entry.getValue())
                        .nextRefreshMs(nextRefreshMs)
                        .build())
                .collect(Collectors.toList());

        boolean isSuccess = true;
        if (async) {
            if (queue == null) {
                return false;
            }
            queue.addAll(collect);
            return true;
        }
        isSuccess = setInternal(collect);

        return isSuccess;
    }

    private boolean setInternal(List<CounterData> collect) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps;
            int updateCount = 0;
            for (CounterData data : collect) {
                String sql = SqlTemplate.builder(UPSERT_SQL)
                        .put("id", data.getId())
                        .put("type", data.getType())
                        .put("cnt", String.valueOf(data.getCount() == null ? 0 : data.getCount().longValue()))
                        .put("next_refresh_ms", String.valueOf(data.getNextRefreshMs()))
                        .build();
                ps = connection.prepareStatement(sql);
                updateCount += ps.executeUpdate();
            }
            return updateCount > 0;
        } catch (Exception e) {
            log.error("[{}] get error.", this.getClass().getSimpleName(), e);
            return false;
        }
    }

    @Override
    public boolean del(String id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("delete from counter_api where id = ?");
            ps.setObject(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            log.error("[{}] get error.", this.getClass().getSimpleName(), e);
            return false;
        }
    }

    @Override
    public boolean del(String id,
                       Collection<String> types) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("delete from counter_api where id = ? and type in (?)");
            ps.setObject(1, id);
            ps.setObject(2, String.join(",", types));
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            log.error("[{}] get error.", this.getClass().getSimpleName(), e);
            return false;
        }
    }

    @Override
    public boolean expireAtMs(String id,
                              String type,
                              long nextRefreshMs) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("update counter_api set nextRefreshMs = ? where id = ? and type in (?)");
            ps.setObject(1, nextRefreshMs);
            ps.setObject(2, id);
            ps.setObject(3, type);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            log.error("[{}] get error.", this.getClass().getSimpleName(), e);
            return false;
        }
    }

}
