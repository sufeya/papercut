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

/**
 * @author liuweichun
 * @date 2022/8/9 22:54
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@EqualsAndHashCode(callSuper=false)
@Data
@TableName("ppc_work_info")
@ApiModel("作品信息")
public class WorkInfo extends Model<WorkInfo> implements Serializable {
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
     * 名字
     */
    private String name;
    /**
     * 图片链接
     */
    private String pic;
    /**
     *发布状态  0 仅自己 1 关注可见 2 所有人可见
     */
    private String publishStatus;
    /**
     * 作品描述
     */
    private String description;
    /**
     * 细节描述
     */
    private String detailDesc;
    /**
     * 作品关键字
     */
    private String keywords;
    /**
     * 喜欢人数
     */
    private Integer likeCount;
    /**
     * 详情展示标题
     */
    private String detailTitle;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
