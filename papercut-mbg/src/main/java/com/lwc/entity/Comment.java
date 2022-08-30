package com.lwc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论实体类
 * @author liuweichun
 * @date 2022/8/9 22:53
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Data
@TableName("ppc_comment")
@ApiModel("评论回复")
@EqualsAndHashCode(callSuper=false)
public class Comment extends Model<Comment> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 作品id
     */
    private String workId;
    /**
     * 用户昵称
     */
    private String userNickName;
    /**
     * 作品名
     */
    private String workName;
    /**
     * 关注人数
     */
    private Integer star;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 展示状态 0 仅自己跟作品人 1 所有人
     */
    private Integer showStatus;
    /**
     * 收藏人数
     */
    private Integer collectCount;
    /**
     * 阅读人数
     */
    private Integer readCount;
    /**
     * 内容
     */
    private String content;
    /**
     * 评论的父id
     */
    private String parentId;
    /**
     * 用户头像
     */
    private String userIcon;
    /**
     * 评论回复
     */
    List<? extends Comment> commentReplays;
}
