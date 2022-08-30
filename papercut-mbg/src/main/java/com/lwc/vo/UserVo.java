package com.lwc.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户映射类
 * @author liuweichun
 * @date 2022/8/14 21:46
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Data
public class UserVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 电话
     */
    private String phone;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户名
     */
    private String username;
    /**
     * 头像链接
     */
    private String icon;
    /**
     * 性别
     */
    private String gender;
    /*
        城市
     */
    private String city;
}
