package com.lwc.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwc.entity.Tag;
import com.lwc.entity.UserInfo;
import com.lwc.entity.UserTagRelation;
import com.lwc.exception.ApiException;
import com.lwc.mapper.TagMapper;
import com.lwc.service.TagService;
import com.lwc.service.UserInfoService;
import com.lwc.service.UserTagRelationService;
import com.lwc.service.WorkInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liuweichun
 * @date 2022/8/11 9:51
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserTagRelationService userTagRelationService;

    @Autowired
    private WorkInfoService workInfoService;

    @Override
    public boolean addUserTag(Tag tag) {
        return save(tag);
    }

    @Override
    public boolean deleteUserTag(String tagId) {

        return false;
    }

    @Override
    public boolean userGetTag(String tagId) {
        UserInfo userInfo = userInfoService.getCurrentUser();
        UserTagRelation userTagRelation = userTagRelationService.getOne(Wrappers.lambdaQuery(UserTagRelation.class).eq(UserTagRelation::getTagId,tagId).eq(UserTagRelation::getUserId,userInfo.getId()));
        if(userTagRelation != null){
            throw new ApiException("您已经领过该标签");
        }
        Tag tag = getById(tagId);
        int count = workInfoService.getUserWorkCount();
        if (tag.getFinishWorkCount()>count){
            return false;
        }
        userTagRelation = new UserTagRelation();
        userTagRelation.setTagId(tagId);
        userTagRelation.setUserId(userInfo.getId());
        userTagRelation.setCreateTime(LocalDateTime.now());
        userTagRelationService.save(userTagRelation);
        return true;
    }

    @Override
    public IPage<Tag> getAllPage(Page<Tag> page) {
        return page(page,Wrappers.lambdaQuery(Tag.class).eq(Tag::getStatus,1));
    }

    @Override
    public IPage<Tag> getUserTagPage(Page<Tag> page) {
        UserInfo userInfo = userInfoService.getCurrentUser();
        List<UserTagRelation> userTagRelations = userTagRelationService.list(Wrappers.lambdaQuery(UserTagRelation.class).eq(UserTagRelation::getUserId,userInfo.getId()));
        if(userTagRelations == null ||userTagRelations.size() == 0 ){
            return null;
        }
        List<String> list = userTagRelations.stream().map(userTagRelation -> userTagRelation.getTagId()).collect(Collectors.toList());
        return page(page,Wrappers.lambdaQuery(Tag.class).in(Tag::getId,list));
    }
}
