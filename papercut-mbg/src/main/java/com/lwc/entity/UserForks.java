package com.lwc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author liuweichun
 * @date 2022/8/9 22:54
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Data
@TableName("ppc_user_forks")
@ApiModel("成就标签")
@EqualsAndHashCode(callSuper=false)
public class UserForks extends Model<UserForks> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 关注的人
     */
    private String forkUser;
    /**
     * 被关注的人
     */
    private String forkedUser;
}
