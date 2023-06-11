package com.neko233.skilltree.commons.json.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestJsonUser {

    private int userId;
    private String name;

}
