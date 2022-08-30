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
 * 主题世界
 * @author liuweichun
 * @date 2022/8/9 22:53
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Data
@TableName("ppc_subject")
@ApiModel("主题世界")
@EqualsAndHashCode(callSuper=false)
public class Subject extends Model<Subject> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;
    /**
     *主题种类
     */
    private String categoryId;
    /**
     * 主题标题
     */
    private String title;
    /**
     * 主题图片
     */
    private String pic;
    /**
     * 收藏数
     */
    private Integer collectCount;
    /**
     * 评论人数
     */
    private Integer commentCount;
    /**
     * 描述
     */
    private String description;
    /**
     * 展示状态
     */
    private Integer showStatus;
    /**
     * 展示图片
     */
    private String albumPics;
}
