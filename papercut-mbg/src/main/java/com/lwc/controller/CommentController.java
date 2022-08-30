package com.lwc.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lwc.common.CommonResult;
import com.lwc.entity.Comment;
import com.lwc.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuweichun
 * @date 2022/8/27 10:07
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@RestController
@Api(value = "/comment",tags = "评论")
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "/create",tags = "评论创建")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public CommonResult create(@RequestBody Comment comment){
        commentService.create(comment);
        return CommonResult.success(comment);
    }

    @ApiOperation(value = "/getComment",tags = "获取评论")
    @RequestMapping(value = "/getComment",method = RequestMethod.GET)
    public CommonResult getComment(String workId, Page<Comment> commentPage){
       IPage<Comment> page = commentService.getPage(workId,commentPage);
       return CommonResult.success(page);
    }
}
