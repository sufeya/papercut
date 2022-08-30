package com.lwc.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwc.entity.UserForks;
import com.lwc.entity.UserInfo;
import com.lwc.entity.WorkInfo;
import com.lwc.exception.ApiException;
import com.lwc.mapper.WorkInfoMapper;
import com.lwc.service.WorkInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liuweichun
 * @date 2022/8/11 9:54
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Service
public class WorkInfoServiceImpl extends ServiceImpl<WorkInfoMapper, WorkInfo> implements WorkInfoService {

    @Autowired
    private UserInfoServiceImpl userInfoService;

    @Autowired
    private UserForksServiceImpl userForksService;


    @Override
    public boolean createWork(WorkInfo workInfo) {

        UserInfo userInfo = userInfoService.getCurrentUser();
        workInfo.setUserId(userInfo.getId());
        //默认全部可见
        if(StrUtil.isBlank(workInfo.getPublishStatus())){
            workInfo.setPublishStatus("2");
        }
        //初始时设置为零
        workInfo.setLikeCount(0);
        workInfo.setCreateTime(LocalDateTime.now());
        workInfo.setUpdateTime(LocalDateTime.now());

        return this.save(workInfo);
    }

    public boolean updateWork(WorkInfo workInfo){
        LambdaQueryWrapper<WorkInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(WorkInfo::getId,workInfo.getId());
        WorkInfo updateWork = this.getOne(lambdaQueryWrapper);
        if(updateWork == null){
            throw new ApiException("更新失败");
        }
        workInfo.setUpdateTime(LocalDateTime.now());
       return  this.update(workInfo,lambdaQueryWrapper);
    }

    @Override
    public boolean deleteWork(List<String> ids) {
        if(ids.size()>0){
            return this.removeByIds(ids);
        }
        return false;
    }

    @Override
    public IPage<WorkInfo> searchWork(String keyWork,Page<WorkInfo> infoIPage) {
        UserInfo userInfo = userInfoService.getCurrentUser();
        List<UserForks> userForks = userForksService.list(Wrappers.lambdaQuery(UserForks.class).eq(UserForks::getForkedUser,userInfo.getId()));
        List<String> ids = null;
        if(userForks.size()>0){
           ids = userForks.stream().map(UserForks::getForkUser).collect(Collectors.toList());
        }
        LambdaQueryWrapper<WorkInfo>lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(WorkInfo::getKeywords,keyWork).and(wq->wq.eq(WorkInfo::getPublishStatus,"2")).or(wq->wq.eq(WorkInfo::getUserId,userInfo.getId()));
        if(ids != null){
            List<String> finalIds = ids;
            lambdaQueryWrapper.or(wq->wq.in(WorkInfo::getUserId, finalIds).and(wq2->wq2.eq(WorkInfo::getPublishStatus,"1")));
        }
        return this.page(infoIPage,lambdaQueryWrapper);
    }

    @Override
    public IPage<WorkInfo> getList(Page<WorkInfo> page) {
        UserInfo userInfo = userInfoService.getCurrentUser();
        List<UserForks> userForks = userForksService.list(Wrappers.lambdaQuery(UserForks.class).eq(UserForks::getForkedUser,userInfo.getId()));
        List<String> ids = null;
        if(userForks.size()>0){
            ids = userForks.stream().map(UserForks::getForkUser).collect(Collectors.toList());
        }
        LambdaQueryWrapper<WorkInfo>lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(WorkInfo::getPublishStatus,"2").or(wq->wq.eq(WorkInfo::getUserId,userInfo.getId()));
        if(ids != null){
            List<String> finalIds = ids;
            lambdaQueryWrapper.or(wq->wq.in(WorkInfo::getUserId, finalIds).and(wq2->wq2.eq(WorkInfo::getPublishStatus,"1")));
        }
        return this.page(page,lambdaQueryWrapper);
    }

    @Override
    public IPage<WorkInfo> getOwnerWork(Page<WorkInfo> page) {
        UserInfo userInfo = userInfoService.getCurrentUser();
        return this.page(page,Wrappers.lambdaQuery(WorkInfo.class).eq(WorkInfo::getUserId,userInfo.getId()));
    }

    @Override
    public int getUserWorkCount() {
        UserInfo userInfo = userInfoService.getCurrentUser();
        return  count(Wrappers.lambdaQuery(WorkInfo.class).eq(WorkInfo::getUserId,userInfo.getId()));
    }
}
