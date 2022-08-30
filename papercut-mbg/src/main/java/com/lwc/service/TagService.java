package com.lwc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lwc.entity.Tag;
import org.springframework.stereotype.Service;

/**
 * @author liuweichun
 * @date 2022/8/11 9:51
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Service
public interface TagService extends IService<Tag> {
    /**
     * 增加用户标签
     */
    boolean addUserTag(Tag tag);

    /**
     * 删除用户标签
     */
    boolean deleteUserTag(String tagId);

    /**
     * 用户领取标签
     */
    boolean userGetTag(String tagId);

    /**
     * 获取所有的标签
     */
    IPage<Tag> getAllPage(Page<Tag> page);

    /**
     * 获取用户获取的所有标签
     */
    IPage<Tag> getUserTagPage(Page<Tag> page);
}
