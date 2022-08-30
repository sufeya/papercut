package com.lwc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author liuweichun
 * @date 2022/8/9 22:54
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Data
@TableName("ppc_user_tag_relation")
@ApiModel("用户标签关系")
@EqualsAndHashCode(callSuper=false)
public class UserTagRelation extends Model<UserTagRelation> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户主键
     */
    @TableId(value ="id",type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 标签id
     */
    private String tagId;

    /**
     * 创建时期
     */
    private LocalDateTime createTime;
}
