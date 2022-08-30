package com.lwc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author liuweichun
 * @date 2022/8/9 10:09
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Data
@TableName("ppc_user_info")
@ApiModel("用户信息")
@EqualsAndHashCode(callSuper=false)
public class UserInfo extends Model<UserInfo> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户主键
     */
    @TableId(value ="id",type = IdType.ASSIGN_ID)
    private String id;
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
     * 用户状态
     */
    private int status;
    /**
     * 用户创建时间
     */
    // 设置序列化方式，using里的值要和属性类型一致
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    // 格式化
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime  createTime;
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
