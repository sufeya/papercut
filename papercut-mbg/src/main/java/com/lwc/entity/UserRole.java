package com.lwc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 用户角色
 * @author liuweichun
 * @date 2022/8/19 12:11
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Data
@TableName("ppc_role")
@ApiModel("用户角色")
@EqualsAndHashCode(callSuper=false)
public class UserRole extends Model<UserRole> {
    private static final long serialVersionUID = 1L;
    //用户主键
    @TableId(value ="id",type = IdType.ASSIGN_ID)
    private String id;
    //角色名
    private String roleName;
    //角色描述
    private String roleDescription;
    //角色下用户数量
    private Integer userCount;
    //角色创建时间
    private LocalDateTime createTime;
    //该角色状态
    private Integer status;
}
