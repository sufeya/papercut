package com.lwc.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwc.entity.Comment;
import com.lwc.entity.UserInfo;
import com.lwc.mapper.CommentMapper;
import com.lwc.service.CommentService;
import com.lwc.service.UserInfoService;
import com.lwc.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 评论服务实现类
 * @author liuweichun
 * @date 2022/8/10 21:54
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserInfoService userInfoService;
    @Override
    public boolean create(Comment comment) {
        //没有父类标题
        if(StrUtil.isBlank(comment.getParentId())){
            comment.setParentId("-1");
        }
        UserInfo userInfo = userInfoService.getCurrentUser();
        comment.setUserNickName(userInfo.getUsername());
        comment.setUserIcon(userInfo.getIcon());
        comment.setCreateTime(LocalDateTime.now());
        return save(comment);
    }

    @Override
    public boolean delete(String commentId) {
        return false;
    }

    @Override
    public IPage<Comment> getPage(String workId,Page<Comment> page) {
        IPage<Comment> commentPage = commentMapper.findTopCommentByParentIdAndWorkId("-1",workId,page);
        List<Comment> comments = commentPage.getRecords();
        //将树形评论都变成二级评论
        for(Comment topComment: comments){
            List<CommentVo> commentVos = new LinkedList<>();
            //处理二级评论
            List<Comment> commentList = (List<Comment>) topComment.getCommentReplays();
            if(commentList == null || commentList.size()==0){
                continue;
            }
          for(Comment replay : commentList){
              CommentVo commentVo = new CommentVo();
              BeanUtil.copyProperties(replay,commentVo);
              commentVo.setReplayName(topComment.getUserNickName());
              commentVo.setCommentReplays(null);
              commentVos.add(commentVo);
              processChildCommnet(replay,commentVos);
          }
          topComment.setCommentReplays(commentVos);
        }
        commentPage.setRecords(comments);
        return commentPage;
    }

    //递归处理评论
    public void processChildCommnet(Comment comment,List<CommentVo> parent){
        List<Comment> replays = (List<Comment>) comment.getCommentReplays();
        comment.setCommentReplays(null);
        if(replays == null || replays.size()==0){
            return ;
        }
        for(Comment co : replays){
            CommentVo cv = new CommentVo();
            BeanUtil.copyProperties(co,cv);
            cv.setReplayName(comment.getUserNickName());
            cv.setCommentReplays(null);
            parent.add(cv);
            //递归处理子评论
            processChildCommnet(co,parent);
        }
    }
}
