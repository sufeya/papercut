package com.lwc.service.impl;

import com.lwc.entity.UserInfo;
import com.lwc.service.RedisService;
import com.lwc.service.UserCachService;
import com.lwc.service.UserInfoService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liuweichun
 * @date 2022/8/14 22:20
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Service
public class UserCachServiceImpl implements UserCachService {
    //redis数据库名
    @Value("${redis.database}")
    private  String REDIS_DATABASE;
    //过期时间
    @Value("${redis.expire.common}")
    private Long EXPIRE_TIME;
    //用户的键
    @Value("${redis.key.admin}")
    private String REDIS_ADMIN_KEY;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserInfoService userInfoService;
    @Override
    public void deleteUser(String userId) {
        UserInfo user = userInfoService.getById(userId);
        if(user != null){
            String key = REDIS_DATABASE+":"+REDIS_ADMIN_KEY+":"+user.getUsername();
            redisService.del(key);
        }
    }

    @Override
    public void setUserInfo(UserInfo userInfo) {
        String key = REDIS_DATABASE+":"+REDIS_ADMIN_KEY+":"+userInfo.getUsername();
        redisService.set(key,userInfo,EXPIRE_TIME);
    }

    @Override
    public UserInfo getUserInfo(String userName) {
        String key = REDIS_DATABASE+":"+REDIS_ADMIN_KEY+":"+userName;

        return (UserInfo) redisService.get(key);
    }
}
