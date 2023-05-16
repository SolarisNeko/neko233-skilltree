package com.neko233.skilltree.commons.algorithm.compare.dto;

import com.neko233.skilltree.commons.algorithm.compare.api.CompareDataApi;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author LuoHaoJun on 2023-05-15
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompareDataResult<T extends CompareDataApi> {

    private List<T> toAddList;
    private List<T> toUpdateList;
    private List<T> toDeleteList;
    private List<T> noChangedList;

}
