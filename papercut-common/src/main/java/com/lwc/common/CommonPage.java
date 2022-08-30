package com.lwc.common;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author liuweichun
 * @date 2022/8/23 17:54
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Data
public class CommonPage<T> {
    private Integer totalPage;
    private Integer pageSize;
    private Integer pageNum;
    private Long total;
    List<T> data;

    public static <T> CommonPage<T> restPage(Page<T> pageInfo){
        CommonPage<T> page = new CommonPage();

        page.setData(pageInfo.getContent());
        page.setPageNum(pageInfo.getNumber());
        page.setPageSize(pageInfo.getSize());
        page.setTotalPage(pageInfo.getTotalPages());
        page.setTotal(pageInfo.getTotalElements());
        return page;
    };

}
