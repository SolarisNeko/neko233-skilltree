package com.neko233.skilltree.commons.core.text;

import com.neko233.skilltree.commons.core.base.ListUtils233;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class TextTokenizerUtils233Test {

    @Test
    public void matchFullyToTokenMap() {
        String input = "202302_01";
        String format = "yyyyMM_dd";
        List<String> tokens = ListUtils233.of("yyyy", "MM", "dd");


        Map<String, String> tokenMap = TextTokenizerUtils233.matchFullyToTokenMap(input, format, tokens);

        Assert.assertEquals("2023", tokenMap.get("yyyy"));
        Assert.assertEquals("02", tokenMap.get("MM"));
        Assert.assertEquals("01", tokenMap.get("dd"));

    }
    @Test
    public void matchFullyToTokenMap_array() {
        String input = "202302_01";
        String format = "yyyyMM_dd";


        Map<String, String> tokenMap = TextTokenizerUtils233.matchFullyToTokenMap(input, format, "yyyy", "MM", "dd");

        Assert.assertEquals("2023", tokenMap.get("yyyy"));
        Assert.assertEquals("02", tokenMap.get("MM"));
        Assert.assertEquals("01", tokenMap.get("dd"));
    }
}