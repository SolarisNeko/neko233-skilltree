package com.neko233.skilltree.global.lock.mysql;

import com.neko233.skilltree.global.lock.GlobalLock;
import com.neko233.skilltree.global.lock.mysql.utils.JdbcHelper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * FU 锁，可重入基于行锁，不支持行锁的无效或锁表，支持阻塞和非阻塞
 */
@Slf4j
public class MysqlFuGlobalLock implements GlobalLock {

    public static final String CREATE_TABLE_SQL = " * create table if not exists neko233_global_lock_by_fu ( " +
            "  id int unsigned auto_increment primary key, " +
            "  lock_name varchar(100) not null, " +
            "  unique(lock_name) " +
            "  ) engine = innodb, charset uf8mb4  ";

    public static final String SELECT_SQL = "select * from neko233_global_lock_by_fu where `lock_name`=? for update";
    public static final String INSERT_SQL = "insert into neko233_global_lock_by_fu(lock_name) values(?)";
    private final DataSource dataSource;
    private ExecutorService threadPoolExecutor = Executors.newSingleThreadExecutor();
    private Connection connection;
    private String lockName;

    public MysqlFuGlobalLock(DataSource dataSource,
                             String lockName) {
        this.lockName = lockName;
        this.dataSource = dataSource;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE_TABLE_SQL)) {
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @Override
    public void lock() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            for (; ; ) {
                connection = dataSource.getConnection();
                connection.setAutoCommit(false);
                statement = connection.prepareStatement(SELECT_SQL);
                statement.setString(1, lockName);
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return;
                }
                JdbcHelper.gracefulClose(resultSet, statement, connection);
                log.info("锁记录不存在，正在创建. lockName = {}", lockName);
                Connection insertConnection = dataSource.getConnection();
                PreparedStatement insertStatement = null;
                try {
                    insertStatement = insertConnection.prepareStatement(INSERT_SQL);
                    insertStatement.setString(1, lockName);
                    if (insertStatement.executeUpdate() == 1) {
                        log.info("创建锁记录成功. lockName = {}", lockName);
                    }
                } catch (Exception e) {
                } finally {
                    JdbcHelper.gracefulClose(insertStatement, insertConnection);
                }
            }
        } catch (Exception e) {
            log.error("获取锁异常. lockName = {}", lockName, e);
        } finally {
            JdbcHelper.gracefulClose(resultSet, statement);
        }
    }

    @Override
    public boolean tryLock(long time,
                           TimeUnit unit) {
        final Future<?> future = threadPoolExecutor.submit(() -> {
            try {
                lock();
                return 1;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        try {
            final Object o = future.get(time, unit);
            if (o == null) {
                future.cancel(true);
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @SneakyThrows
    @Override
    public void unlock() {
        connection.commit();
        connection.close();
    }

}
