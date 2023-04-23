package com.neko233.toolchain.commons.core.cron;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

public class CronExpressionV1Test {


    @Test
    public void testCronBase() throws ParseException {
        CronExpressionV1 cronExpressionV1 = new CronExpressionV1("* * * */1 * ? ");
        Date nextValidTime = cronExpressionV1.getNextValidTimeAfter();
        System.out.println(nextValidTime);
    }

}