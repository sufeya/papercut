package com.lwc.service;

import com.lwc.doc.UserCollection;
import org.springframework.data.domain.Page;

/**
 * @author liuweichun
 * @date 2022/8/23 15:20
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public interface UserCollectionService {
    /**
     * 添加收藏
     */
    int add(UserCollection userCollection);

    /**
     * 删除收藏
     */
    int delete(String workId);

    /**
     * 分页查询
     */
    Page<UserCollection> list(Integer pageNum,Integer pageSize);

    /**
     * 查看收藏详情
     */
    UserCollection detail(String workId);

    /**
     * 清空收藏
     */
    void clear();
}
