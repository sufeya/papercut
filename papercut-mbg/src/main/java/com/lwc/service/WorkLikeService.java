package com.lwc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lwc.entity.WorkLike;

/**
 * @author liuweichun
 * @date 2022/8/25 18:10
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public interface WorkLikeService extends IService<WorkLike> {
    /**
     * 创建点赞记录
     */
    boolean create(WorkLike workLike);

    /**
     * 跟据作品id查询点赞记录
     */
    IPage<WorkLike> getPageByWorkId(String workId, Page<WorkLike> page);

    /**
     * 跟据人的id对点赞记录进行查询
     */
    IPage<WorkLike> getPageByUserId(String userId, Page<WorkLike> page);

    /**
     * 将点赞记录存入redis数据库
     */
    boolean changeRecordToRedis();

    /**
     * 将redis中的点赞记录存入数据库
     */
    void saveRecordFromRedis();

    /**
     * 跟据用户id和作品id判断是否有点赞记录
     * @param userId
     * @param workId
     * @return
     */
    WorkLike getWorkLikeById(String userId,String workId);

    /**
     * 点赞加一
     */
    void increaseWorkLike(String workId);
    /**
     * 点赞减一
     */
    void decreaseWorkLike(String workId);

    /**
     * 设置点赞状态
     */
    int setWorkLikeStatus(String workId,Integer status);

    /**
     * 从redis中获取记录设置作品的点赞数量
     */
    void setWorkLikeCountFormRedis();
}
