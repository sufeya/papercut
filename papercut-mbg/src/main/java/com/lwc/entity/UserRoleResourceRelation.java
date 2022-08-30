package com.lwc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户角色与资源关系表
 * @author liuweichun
 * @date 2022/8/19 12:12
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Data
@TableName("ppc_role_resource_relation")
@ApiModel("用户角色与资源关系表")
@EqualsAndHashCode(callSuper=false)
public class UserRoleResourceRelation extends Model<UserRoleResourceRelation> {
    @TableId(value ="id",type = IdType.ASSIGN_ID)
    private String id;
    private String roleId;
    private String resourceId;
}
