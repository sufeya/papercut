package com.lwc.repository;

import com.lwc.doc.UserCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author liuweichun
 * @date 2022/8/23 13:48
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public interface UserCollectionRepository extends MongoRepository<UserCollection,String> {

    /**
     * 跟据会员id和作品id查找记录
     */
    UserCollection findUserCollectionsByUserIdAndAndWorkId(String userId,String workId);

    /**
     * 跟据用户id和作品id删除记录
     */
    int deleteAllByUserIdAndWorkId(String userId,String workId);

    /**
     * 跟据会员id分页查询
     */
    Page<UserCollection> findByUserId(String userId, Pageable pageable);

    /**
     * 根据会员ID删除录
     */
    void deleteAllByUserId(String userId);
}
