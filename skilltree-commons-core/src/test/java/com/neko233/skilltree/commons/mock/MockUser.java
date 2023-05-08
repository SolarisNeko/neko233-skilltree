package com.neko233.skilltree.commons.mock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MockUser {

    private Integer id;

    private String name;

    private Integer age;

    private String email;
}
