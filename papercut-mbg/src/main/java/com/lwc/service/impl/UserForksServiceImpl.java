package com.lwc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwc.entity.UserForks;
import com.lwc.mapper.UserForksMapper;
import com.lwc.service.UserForksService;
import org.springframework.stereotype.Service;

/**
 * @author liuweichun
 * @date 2022/8/10 22:49
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Service
public class UserForksServiceImpl extends ServiceImpl<UserForksMapper, UserForks> implements UserForksService {
}
