package com.neko233.skilltree.counter.impl;

import com.neko233.skilltree.counter.Counter;
import org.junit.Assert;
import org.junit.Test;

import javax.sql.DataSource;

/**
 * @author SolarisNeko
 * Date on 2023-04-27
 */
public class CounterByMysqlTest {

    DataSource dataSource = DataSourceMock.createDataSource();

    @Test
    public void test_plus1() throws InterruptedException {
        Counter counter = new CounterByMysql(dataSource);

        String id = "demo" + System.currentTimeMillis();
        String type = "test_plus1";
        counter.set(id, type);
        counter.plus(id, type);
        Number value = counter.getValue(id, type);


        Assert.assertEquals(1, value.intValue());
    }

    @Test
    public void test_plus10() throws InterruptedException {
        Counter counter = new CounterByMysql(dataSource);

        String id = "demo" + System.currentTimeMillis();
        String type = "test_plus10";
        counter.set(id, type);
        counter.plus(id, type, 10);
        Number value = counter.getValue(id, type);


        Assert.assertEquals(10, value.intValue());
    }

    @Test
    public void test_plus10_minus1() throws InterruptedException {
        Counter counter = new CounterByMysql(dataSource);

        String id = "demo" + System.currentTimeMillis();
        String type = "test_plus10_minus1";
        counter.set(id, type);
        counter.plus(id, type, 10);
        counter.minus(id, type, 1);
        Number value = counter.getValue(id, type);


        Assert.assertEquals(9, value.intValue());
    }


}