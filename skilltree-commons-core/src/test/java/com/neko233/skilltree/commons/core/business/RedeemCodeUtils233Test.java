package com.neko233.skilltree.commons.core.business;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author SolarisNeko
 * Date on 2023-01-28
 */
public class RedeemCodeUtils233Test {

    @Test
    public void generateRedeemCodeTest() {
        List<String> strings = RedeemCodeUtils233.GenerateCodeByBatch(1);
        Assert.assertEquals(1, strings.size());
    }

}