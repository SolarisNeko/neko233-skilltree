package com.neko233.skilltree.commons.core.base;

import com.neko233.skilltree.commons.mock.MockUser;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Map;

public class FieldUtils233Test {

    @Test
    public void testGetFieldNameToValue() {

        MockUser build = MockUser.builder()
                .id(1)
                .build();

        Map<Field, Object> fieldNameToValue = FieldUtils233.getFieldNameToValue(build);

        for (Map.Entry<Field, Object> e : fieldNameToValue.entrySet()) {
            Field f = e.getKey();

            if (!f.getName().contains(".id")) {
                continue;
            }

            if ((int) e.getValue() != 1) {
                Assert.fail();
            }
        }
    }
}