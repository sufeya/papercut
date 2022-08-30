package com.lwc.service;

import com.lwc.doc.UserReadHistory;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author liuweichun
 * @date 2022/8/23 14:35
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public interface UserReadHistoryService {
    /**
     * 创建浏览历史
     * @param userReadHistory
     * @return
     */
    int create(UserReadHistory userReadHistory);

    /**
     * 批量删除浏览历史
     * @param ids
     * @return
     */
    int delete(List<String> ids);

    Page<UserReadHistory> list(Integer pageNum, Integer pageSize);

    /**
     * 清楚浏览历史
     */
    void clear();
}
