package com.neko233.skilltree.commons.core.reload.reloadConfig.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 示例使用：假设有一个User类作为配置项
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MockReloadUserData {

    private String name;
    private int age;

    // 省略构造函数、getter和setter等

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

