package com.lwc.repository;

import com.lwc.doc.UserReadHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author liuweichun
 * @date 2022/8/23 13:49
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public interface UserReadHistoryRepository extends MongoRepository<UserReadHistory,String> {
    /**
     * 查询浏览记录
     * @param userId
     * @param pageable
     * @return
     */
    Page<UserReadHistory> findByUserIdOrderByCreateDate(String userId, Pageable pageable);

    /**
     * 跟据会员id删除记录
     * @param userId
     */
    void deleteAllByUserId(String userId);
}
