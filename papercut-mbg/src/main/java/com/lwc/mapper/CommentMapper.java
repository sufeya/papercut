package com.lwc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lwc.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liuweichun
 * @date 2022/8/10 21:52
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    IPage<Comment> findTopCommentByParentIdAndWorkId(@Param("parentId") String parentId, @Param("workId") String workId, Page<Comment> page);
}
