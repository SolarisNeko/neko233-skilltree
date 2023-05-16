package com.neko233.skilltree.commons.algorithm.compare.impl;


import com.neko233.skilltree.commons.algorithm.compare.api.CompareDataApi;
import com.neko233.skilltree.commons.core.base.ListUtils233;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author SolarisNeko on 2023-05-01
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DemoCompareData implements CompareDataApi {

    private int id;
    private String name;
    private String email;


    @Override
    public List<Object> primaryKeyList() {
        return ListUtils233.of(id);
    }
}
