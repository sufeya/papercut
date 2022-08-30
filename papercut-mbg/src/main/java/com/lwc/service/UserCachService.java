package com.lwc.service;

import com.lwc.entity.UserInfo;
import org.springframework.stereotype.Service;

/**
 * 用户信息缓存
 * @author liuweichun
 * @date 2022/8/14 22:04
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public interface UserCachService {
    /**
     * 删除用户缓存
     */
    void deleteUser(String userId);

    /**
     * 设置用户缓存
     * @param userInfo
     */
    void setUserInfo(UserInfo userInfo);

    /**
     * 跟据用户名获取用户缓存
     * @param UserName
     * @return
     */
    UserInfo getUserInfo(String UserName);
}
