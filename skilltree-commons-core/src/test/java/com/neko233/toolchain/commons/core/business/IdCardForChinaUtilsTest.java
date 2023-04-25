package com.neko233.toolchain.commons.core.business;

import org.junit.Assert;
import org.junit.Test;

public class IdCardForChinaUtilsTest {

    public static final String ID_CARD_MOCK = IdCardForChinaUtils.getBirthByIdCard("440102200001010000");

    @Test
    public void getBirthByIdCard() {
        String birthByIdCard = ID_CARD_MOCK;
        Assert.assertEquals("20000101", birthByIdCard);
    }
}