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
 * 主题世界种类
 * @author liuweichun
 * @date 2022/8/9 22:53
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Data
@TableName("ppc_subject_category")
@ApiModel("主题世界种类")
@EqualsAndHashCode(callSuper=false)
public class SubjectCategory extends Model<SubjectCategory> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 种类名
     */
    private String name;
    /**
     * 图标
     */
    private String icon;
    /**
     * 种类所属主题数
     */
    private Integer subjectCount;
    /**
     * 展示状态
     */
    private Integer showStatus;
}
