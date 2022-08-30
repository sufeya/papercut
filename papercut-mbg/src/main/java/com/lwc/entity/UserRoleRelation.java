package com.lwc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户与角色关系表
 * @author liuweichun
 * @date 2022/8/19 12:11
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Data
@TableName("ppc_user_role_relation")
@ApiModel("用户与角色关系表")
@EqualsAndHashCode(callSuper=false)
public class UserRoleRelation extends Model<UserRoleRelation> {
    @TableId(value ="id",type = IdType.ASSIGN_ID)
    //主键id
    private String id;
    //角色id
    private String roleId;
    //用户id
    private String userId;
}
