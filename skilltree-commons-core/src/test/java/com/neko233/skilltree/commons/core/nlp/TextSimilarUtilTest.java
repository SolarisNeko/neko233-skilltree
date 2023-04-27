package com.neko233.skilltree.commons.core.nlp;

import org.junit.Assert;
import org.junit.Test;

public class TextSimilarUtilTest {

    @Test
    public void similarPercent() {
        float tips = TextSimilarUtil.levenshteinDistance("1234567890", "1234567891");
        Assert.assertEquals("0.9", String.valueOf(tips));
    }

    @Test
    public void isSimilar() {
        // just 1 char update
        boolean textSimilar = TextSimilarUtil.isTextSimilar("1234567890", "1234567891", 0.7f);
        Assert.assertTrue(textSimilar);
    }

}