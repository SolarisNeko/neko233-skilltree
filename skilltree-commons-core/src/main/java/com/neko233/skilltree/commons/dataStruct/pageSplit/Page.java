package com.neko233.skilltree.commons.dataStruct.pageSplit;

import lombok.Getter;
import lombok.ToString;

/**
 * @author LuoHaoJun on 2023-05-29
 **/
@Getter
@ToString
public class Page {

    private final int pageNum;
    private final int pageSize;
    private final int startIndex;
    private final int endIndex;

    public Page(int pageNum,
                int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.startIndex = (pageNum - 1) * pageSize;
        this.endIndex = startIndex + pageSize - 1;
    }


}
