package com.neko233.skilltree.commons.json;

import com.neko233.skilltree.commons.core.base.ListUtils233;
import com.neko233.skilltree.commons.json.dto.TestJsonUser;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class JsonUtils233Test {

    @Test
    public void toJsonString() {
        TestJsonUser build = TestJsonUser.builder()
                .userId(1)
                .name("neko233")
                .build();
        String jsonString = JsonUtils233.toJsonString(build);

        Assert.assertEquals("{\"userId\":1,\"name\":\"neko233\"}", jsonString);
    }

    @Test
    public void parseToObject() throws Exception {

        TestJsonUser build = TestJsonUser.builder()
                .userId(1)
                .name("neko233")
                .build();

        String jsonString = JsonUtils233.toJsonString(build);

        TestJsonUser testJsonUser = JsonUtils233.parseToObject(jsonString, TestJsonUser.class);
        Assert.assertEquals(1, testJsonUser.getUserId());
        Assert.assertEquals("neko233", testJsonUser.getName());
    }

    @Test
    public void toJsonString_array() {
        List<TestJsonUser> list = ListUtils233.of(
                TestJsonUser.builder()
                        .userId(1)
                        .name("neko233")
                        .build()
                ,
                TestJsonUser.builder()
                        .userId(2)
                        .name("user2")
                        .build()

        );
        String jsonString = JsonUtils233.toJsonString(list);

        Assert.assertEquals("[{\"userId\":1,\"name\":\"neko233\"},{\"userId\":2,\"name\":\"user2\"}]", jsonString);
    }

    @Test
    public void parseToObject_array() throws Exception {

        List<TestJsonUser> list = ListUtils233.of(
                TestJsonUser.builder()
                        .userId(1)
                        .name("neko233")
                        .build()
                ,
                TestJsonUser.builder()
                        .userId(2)
                        .name("user2")
                        .build()

        );

        String jsonString = JsonUtils233.toJsonString(list);

        List<TestJsonUser> testJsonUser = JsonUtils233.parseToObjectList(jsonString, TestJsonUser.class);
        System.out.println(testJsonUser);
    }
}