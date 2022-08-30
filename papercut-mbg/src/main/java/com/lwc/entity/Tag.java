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
 * @date 2022/8/9 22:53
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Data
@TableName("ppc_tag")
@ApiModel("成就标签")
@EqualsAndHashCode(callSuper=false)
public class Tag extends Model<Tag> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 标签名字
     */
    private String name;
    /**
     * 标签说明
     */
    private String description;
    /**
     * 完成多少作品才可以得到
     */
    private Integer finishWorkCount;
    /**
     * 标签图标
     */
    private String icon;

    /**
     * 标签状态
     */
    private Integer status;
}
