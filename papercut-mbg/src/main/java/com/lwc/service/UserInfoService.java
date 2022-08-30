package com.lwc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lwc.entity.UserInfo;
import com.lwc.entity.UserResoures;
import com.lwc.vo.UpdatePassWordParam;
import com.lwc.vo.UserVo;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * @author liuweichun
 * @date 2022/8/9 10:39
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public interface UserInfoService extends IService<UserInfo> {
    /**
     * 用户注册
     * @param userVo
     * @return
     */
    UserInfo register(UserVo userVo);

    /**
     * 跟据用户名字或者用户
     * @param userName
     * @return
     */
    UserInfo getUserByUserName(String userName);

    /**
     * 跟据用户名字或者spring用户
     * @param userName
     * @return
     */
    UserDetails loadUserByUserName(String userName);
    /**
     * 用户登入
     */
    String login(UserVo userVo);

    /**
     * 修改用户信息
     * @param id
     * @param userInfo
     */
    void updateUser(String id,UserInfo userInfo);

    /**
     * 查询页
     * @param Page
     * @param keyword
     * @return
     */
    IPage<UserInfo> getPage(Page<UserInfo> Page,String keyword);

    /**
     * 刷新token
     * @param oldToken
     * @return
     */
    String refreshToken(String oldToken);

    /**
     * 删除用户
     * @param userId
     */
    void delteUser(String userId);

    /**
     * 跟新用户密码
     * @param updatePassWordParam
     * @return
     */
    int updatePassWord(UpdatePassWordParam updatePassWordParam);

    /**
     * 获取用户所有资源
     * @param userId
     * @return
     */
    List<UserResoures> getResource(String userId);
    /**
     * 通过springsecurity获取当前用户
     */
    UserInfo getCurrentUser();
}
