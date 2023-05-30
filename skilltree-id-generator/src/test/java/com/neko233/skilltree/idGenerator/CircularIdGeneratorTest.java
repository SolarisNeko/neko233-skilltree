package com.neko233.skilltree.idGenerator;

import com.neko233.skilltree.commons.core.base.Range233;
import com.neko233.skilltree.commons.core.number.NumberRange;
import org.junit.Assert;
import org.junit.Test;

public class CircularIdGeneratorTest {

    @Test
    public void getNextId() {
        CircularIdGenerator generator = new CircularIdGenerator(1, 10, 2);
        long previousId = 1;

        NumberRange<Long> between = NumberRange.between(1L, 10L);

        for (int i = 0; i < 15; i++) {
            long nextId = generator.getNextId();

            boolean isContains = between.contains(nextId);
            Assert.assertTrue(isContains);
        }
    }
}