package com.lwc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwc.entity.UserTagRelation;
import com.lwc.mapper.UserTagRelationMapper;
import com.lwc.service.UserTagRelationService;
import org.springframework.stereotype.Service;

/**
 * @author liuweichun
 * @date 2022/8/11 9:47
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Service
public class UserTagRelationServiceImpl extends ServiceImpl<UserTagRelationMapper, UserTagRelation> implements UserTagRelationService {
}
