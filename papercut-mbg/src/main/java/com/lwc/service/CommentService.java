package com.lwc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lwc.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论服务接口
 * @author liuweichun
 * @date 2022/8/10 21:53
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Service
public interface CommentService extends IService<Comment> {
    /**
     * 添加评论
     */
    boolean create(Comment comment);

    /**
     * 删除评论
     */
    boolean delete(String commentId);

    /**
     * 分页查询相关评论
     */
    IPage<Comment> getPage(String workId,Page<Comment> page);
}
