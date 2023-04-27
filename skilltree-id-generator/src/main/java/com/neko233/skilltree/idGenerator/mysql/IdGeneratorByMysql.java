package com.neko233.skilltree.idGenerator.mysql;

import com.neko233.skilltree.commons.core.base.CloseableUtils233;
import com.neko233.skilltree.idGenerator.IdGenerator;
import com.neko233.skilltree.idGenerator.IdGeneratorException;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author SolarisNeko on 2022-12-29
 **/
@Slf4j
public class IdGeneratorByMysql implements IdGenerator {

    private static final String CREATE_TABLE = "create table id_generator " +
            "( " +
            "    name        varchar(100)    not null primary key comment '唯一名字', " +
            "    description varchar(100)    not null comment '描述', " +
            "    start_id    bigint unsigned not null comment '开始ID', " +
            "    end_id      bigint unsigned not null comment '结束ID', " +
            "    step        bigint unsigned not null comment '步长', " +
            "    current_id  bigint unsigned not null comment '当前ID' " +
            ") engine = innodb, " +
            "  charset utf8mb4 " +
            "    comment 'id生成器'";
    private static final String CHECK_TABLE = "SHOW TABLES LIKE 'id_generator' ";

    private static final String INSERT_FIRST = "INSERT INTO `id_generator`(name, description, start_id, end_id, step, current_id) VALUES(?, '', ?, ?, ?, ?) ";
    private static final String SELECT_FOR_UPDATE = "SELECT name, description, start_id, end_id, step, current_id FROM id_generator WHERE name = ? Limit 0, 1 FOR UPDATE";
    private static final String UPDATE = "UPDATE id_generator set current_id = current_id + ? WHERE name = ? and current_id + ? <= end_id and current_id = ? ";
    private final String name;
    private final DataSource dataSource;
    private final IdGeneratorEntity template;
    private final BlockingQueue<Long> idQueue = new LinkedBlockingQueue<>();
    public volatile int generateBatchSize = 1;
    private volatile boolean isInit = false;
    private volatile IdGeneratorEntity cacheState;


    public IdGeneratorByMysql(String businessName,
                              DataSource dataSource) {
        this(businessName, dataSource, 1, true, null);
    }

    public IdGeneratorByMysql(String businessName,
                              DataSource dataSource,
                              int generateBatchSize,
                              boolean isAutoCreateTable) {
        this(businessName, dataSource, generateBatchSize, isAutoCreateTable, null);
    }

    public IdGeneratorByMysql(String businessName,
                              DataSource dataSource,
                              int generateBatchSize,
                              boolean isAutoCreateTable,
                              IdGeneratorEntity initTemplate) {
        assert dataSource != null;
        this.dataSource = dataSource;
        this.generateBatchSize = Math.max(generateBatchSize, 1);
        this.name = businessName;
        this.template = IdGeneratorEntity.getOrDefault(businessName, initTemplate);

        if (isAutoCreateTable) {
            checkAndCreateTable();
        }
    }

    private static void rollback(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            connection.rollback();
        } catch (SQLException ex) {
            log.error("[id_generator] rollback exception.", ex);
        }
    }

    private boolean checkAndCreateTable() {
        boolean isNeedGenerate = true;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(CHECK_TABLE);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                isNeedGenerate = false;
            }

            if (isNeedGenerate) {
                log.info("Need create table [id_generator]. SQL = {}", CREATE_TABLE);
                ps = connection.prepareStatement(CREATE_TABLE);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            log.error("Create table error. sql = {}", CREATE_TABLE, e);
            cacheState = null;

            return false;
        }
        return true;
    }

    public long getCurrentId() throws IdGeneratorException {
        if (cacheState == null) {
            cacheId(0);
        }
        return cacheState.getCurrentId();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Long nextId() throws IdGeneratorException {
        Long id = idQueue.poll();
        // lazy
        if (id == null) {
            boolean isSuccess = cacheId(generateBatchSize);
            if (isSuccess) {
                id = idQueue.poll();
            }
        }
        return id;
    }

    @Override
    public boolean cacheId(int count) throws IdGeneratorException {
        if (count < 0) {
            return false;
        }

        if (count == 0) {
            try (Connection connection = dataSource.getConnection()) {
                queryStateInSync(connection);
            } catch (SQLException e) {
                log.error("query id_generator error. ", e);
                return false;
            }
            return true;
        }

        // update
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            queryStateInSync(connection);

            if (cacheState == null) {
                return false;
            }

            ps = connection.prepareStatement(UPDATE);
            long move = count * cacheState.getStep();
            ps.setObject(1, move);
            ps.setObject(2, name);
            ps.setObject(3, move);
            ps.setObject(4, cacheState.getCurrentId());
            int updateCount = ps.executeUpdate();
            if (updateCount > 0) {
                long currentId = cacheState.getCurrentId();
                for (long i = 0; i < count; i++) {
                    long moveNumber = (i + 1) * cacheState.getStep();
                    idQueue.offer(currentId + moveNumber);
                }
                cacheState.setCurrentId(currentId + cacheState.getStep());
            } else {
                throw new IdGeneratorException(String.format("[id-generator-by-rds] can not get more id! you request id number = %s, schema = %s", count, connection.getSchema()));
            }
            connection.commit();
        } catch (SQLException e) {
            log.error("generate id error. name = {}", name, e);
            cacheState = null;

            rollback(connection);

            return false;
        } finally {
            CloseableUtils233.close(connection, ps);
        }
        return true;
    }

    private synchronized void queryStateInSync(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(SELECT_FOR_UPDATE);
        ps.setObject(1, name);
        ResultSet rs = ps.executeQuery();
        // 更新状态
        if (rs.next()) {
            cacheState = IdGeneratorEntity.builder()
                    .name(name)
                    .description(rs.getString("description"))
                    .startId(rs.getLong("start_id"))
                    .endId(rs.getLong("end_id"))
                    .step(rs.getLong("step"))
                    .currentId(rs.getLong("current_id"))
                    .build();
            return;
        }

        // 没查到数据
        if (!isInit) {
            isInit = true;
            insertFirst(connection);
            queryStateInSync(connection);
        }
    }

    private void insertFirst(Connection connection) {
        long step = template.getStep();
        long currentId = template.getCurrentId();
        long startId = template.getStartId();
        long endId = template.getEndId();

        try {
            PreparedStatement ps = connection.prepareStatement(INSERT_FIRST);
            ps.setObject(1, name);
            ps.setObject(2, startId);
            ps.setObject(3, endId);
            ps.setObject(4, step);
            ps.setObject(5, currentId);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("SQL Exception error", e);
        }

        idQueue.offer(currentId);
    }

    @Override
    public List<Long> nextIds(int count) throws IdGeneratorException {
        List<Long> list = new ArrayList<>();

        int batchCount = Math.abs(Math.min(idQueue.size() - count, count));
        cacheId(batchCount);

        for (int i = 0; i < count; i++) {
            Long e = nextId();
            if (e == null) {
                continue;
            }
            list.add(e);
        }
        return list;
    }
}
