package com.neko233.skilltree.idGenerator.snowflake;

import com.neko233.skilltree.idGenerator.IdGeneratorException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author SolarisNeko
 * Date on 2023-04-27
 */
public class IdGeneratorBySnowflakeTest {


    @Test
    public void test() {
        IdGeneratorBySnowflake worker = new IdGeneratorBySnowflake("demo", 1);
        int count = 0;
        for (int i = 0; i < 30; i++) {
            Long x = null;
            try {
                x = worker.nextId();
                if (x != null) {
                    count++;
                }
            } catch (IdGeneratorException e) {
                Assert.fail();
            }
        }

        Assert.assertEquals(30, count);
    }


}