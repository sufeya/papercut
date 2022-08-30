package com.lwc.vo;

import com.lwc.entity.Comment;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论的映射类
 * @author liuweichun
 * @date 2022/8/27 8:27
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Data
public class CommentVo extends Comment {
    /**
     * 回复的名字
     */
    private String replayName;

}
