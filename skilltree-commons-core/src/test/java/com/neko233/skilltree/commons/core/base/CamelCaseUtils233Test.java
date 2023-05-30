package com.neko233.skilltree.commons.core.base;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CamelCaseUtils233Test {

    @Test
    public void bigCamelCaseToLowerCamelCase() {
        String out = CamelCaseUtils233.bigCamelCaseToLowerCamelCase("QUERY_HISTORY_REWARD_USER");
        Assert.assertEquals("queryHistoryRewardUser", out);
    }
}