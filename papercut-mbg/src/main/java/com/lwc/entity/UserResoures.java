package com.lwc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 用户能访问的资源
 * @author liuweichun
 * @date 2022/8/19 11:43
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Data
@TableName("ppc_user_resource")
@ApiModel("用户能访问的资源")
@EqualsAndHashCode(callSuper=false)
public class UserResoures extends Model<UserResoures> {

    @TableId(value ="id",type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "资源名称")
    private String resourceName;

    @ApiModelProperty(value = "资源URL")
    private String url;

    @ApiModelProperty(value = "描述")
    private String description;

    private static final long serialVersionUID = 1L;
}
