package com.lwc.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwc.entity.UserInfo;
import com.lwc.entity.WorkInfo;
import com.lwc.entity.WorkLike;
import com.lwc.exception.ApiException;
import com.lwc.mapper.WorkLikeMapper;
import com.lwc.service.RedisService;
import com.lwc.service.UserInfoService;
import com.lwc.service.WorkInfoService;
import com.lwc.service.WorkLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liuweichun
 * @date 2022/8/25 18:43
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Service
public class WorkLikeServiceImpl extends ServiceImpl<WorkLikeMapper, WorkLike> implements WorkLikeService {
    //redis数据库名
    @Value("${redis.database}")
    private  String REDIS_DATABASE;
    //过期时间
    @Value("${redis.expire.common}")
    private Long EXPIRE_TIME;
    //用户的键
    @Value("${redis.key.workLike}")
    private String REDIS_WORKLIKE_KEY;

    @Value("${redis.key.workLikeCount}")
    private String REDIS_WORKCOUNT_KEY;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private WorkInfoService workInfoService;

    @Override
    @Transactional
    public boolean create(WorkLike workLike) {
        workLike.setCreateTime(LocalDateTime.now());
        return this.save(workLike);
    }

    @Override
    public IPage<WorkLike> getPageByWorkId(String workId, Page<WorkLike> page) {
        IPage<WorkLike> pages = this.page(page,Wrappers.lambdaQuery(WorkLike.class).eq(WorkLike::getWorkId,workId));
        return pages;
    }

    @Override
    public IPage<WorkLike> getPageByUserId(String userId,Page<WorkLike> page) {
        IPage<WorkLike> pages = this.page(page,Wrappers.lambdaQuery(WorkLike.class).eq(WorkLike::getUserId,userId));
        return pages;
    }

    @Override
    public boolean changeRecordToRedis() {

        return false;
    }

    @Override
    public void saveRecordFromRedis() {
        Map<Object ,Object> map  = redisService.hGetAll(REDIS_DATABASE+":"+REDIS_WORKLIKE_KEY);
        for(Map.Entry<Object,Object> entry : map.entrySet()){
           String key = (String) entry.getKey();
           String[] value = key.split("::");
           Integer keyValue = (Integer) entry.getValue();
           WorkLike workLike = new WorkLike();
           workLike.setUserId(value[0]);
           workLike.setWorkId(value[1]);
           workLike.setStatus(keyValue);
           WorkLike workLike1 = getWorkLikeById(value[0],value[1]);
           if(workLike1 == null){
               this.create(workLike);
           }else{
               workLike1.setStatus(workLike.getStatus());
               update(workLike1,Wrappers.lambdaQuery(WorkLike.class).eq(WorkLike::getId,workLike1.getId()));
           }
           //将数据从数据库中删除
           redisService.hDel(REDIS_WORKLIKE_KEY,key);
        }

    }

    @Override
    public WorkLike getWorkLikeById(String userId, String workId) {
        LambdaQueryWrapper<WorkLike> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(WorkLike::getUserId,userId).eq(WorkLike::getWorkId,workId);
        WorkLike workLike = this.getOne(lambdaQueryWrapper);
        return workLike;
    }

    @Override
    public void increaseWorkLike(String workId) {
        UserInfo user = userInfoService.getCurrentUser();
        String key = REDIS_DATABASE+":"+REDIS_WORKCOUNT_KEY;
        String hash = user.getId()+"::"+workId;
        redisService.hIncr(key,hash,1L);
    }

    @Override
    public void decreaseWorkLike(String workId) {
        UserInfo userInfo = userInfoService.getCurrentUser();
        String key = REDIS_DATABASE+":"+REDIS_WORKCOUNT_KEY;
        String hash = userInfo.getId()+"::"+workId;
        redisService.hDecr(key,hash,1L);
    }

    @Override
    public int setWorkLikeStatus(String workId, Integer status) {
        if(StrUtil.isBlank(workId)){
            throw new ApiException("请选择相应作品");
        }
        int count = workInfoService.count(Wrappers.lambdaQuery(WorkInfo.class).eq(WorkInfo::getId,workId));
        if(count == 0){
            return -1;
        }
        UserInfo userInfo = userInfoService.getCurrentUser();
        //不能重复点赞,或者重复取消点赞
        WorkLike workLike = this.getWorkLikeById(userInfo.getId(),workId);
        if(workLike != null && workLike.getStatus() == status){
            return -1;
        }

        String key = REDIS_DATABASE+":"+REDIS_WORKLIKE_KEY;
        String hash = userInfo.getId()+"::"+workId;
        Integer preStatus = (Integer) redisService.hGet(key,hash);
        if(preStatus == status){
            return -1;
        }
        redisService.hSet(key,hash,status,EXPIRE_TIME);
        return 1;
    }

    @Override
    public void setWorkLikeCountFormRedis() {
        String key = REDIS_DATABASE+":"+REDIS_WORKCOUNT_KEY;
        Map<Object,Object> map = redisService.hGetAll(key);
        for(Map.Entry<Object,Object> entry : map.entrySet()){
            String tempKey = (String) entry.getKey();
            String[] ids = tempKey.split("::");
            WorkInfo workInfo = workInfoService.getOne(Wrappers.lambdaQuery(WorkInfo.class).eq(WorkInfo::getId,ids[1]));
            workInfo.setLikeCount(workInfo.getLikeCount()+(Integer) entry.getValue());
            workInfoService.updateWork(workInfo);
            //删除原来的键值对
            redisService.hDel(key,tempKey);
        }
    }
}
