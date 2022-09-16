package com.lwc.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lwc.common.CommonPage;
import com.lwc.common.CommonResult;
import com.lwc.config.IntegerConfigEnum;
import com.lwc.entity.WorkInfo;
import com.lwc.service.WorkInfoService;
import com.lwc.service.WorkLikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 作品信息
 * @author liuweichun
 * @date 2022/8/25 9:11
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@RequestMapping("/workInfo")
@RestController
@Api(value = "/workInfo",tags = "作品信息管理")
public class WorkInfoController {
    @Autowired
    private WorkInfoService workInfoService;

    @Autowired
    private WorkLikeService workLikeService;

    @ApiOperation(value = "/create",tags = "创建作品")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public CommonResult createWork(@RequestBody WorkInfo workInfo){

        boolean result = workInfoService.createWork(workInfo);
        if(result){
            return CommonResult.success(workInfo);
        }else{
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "/updateWork",tags = "更新作品信息")
    @RequestMapping(value = "/updateWork",method = RequestMethod.POST)
    public CommonResult updateWork(@RequestBody WorkInfo workInfo){
       boolean result = workInfoService.updateWork(workInfo);
       if(result){
           return CommonResult.success(workInfo);
       }else{
           return CommonResult.failed();
       }
    }

    @ApiOperation(value = "/deleteWork",tags = "删除作品")
    @RequestMapping(value = "/deleteWork",method = RequestMethod.POST)
    public CommonResult deleteWork(@RequestBody List<String> ids){
       boolean reuslt = workInfoService.deleteWork(ids);
       if(reuslt){
           return CommonResult.success(ids);
       }else{
          return CommonResult.failed();
       }
    }

    @ApiOperation(value = "/searchWork",tags = "搜索作评")
    @RequestMapping(value = "/searchWork",method = RequestMethod.GET)
    public CommonResult searchWork(String keyWords, @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,@RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize){
        Page<WorkInfo> workInfoIPage = new Page<>();
        workInfoIPage.setCurrent(pageNum).setSize(pageSize);
       IPage<WorkInfo> result = workInfoService.searchWork(keyWords,workInfoIPage);
       return CommonResult.success(result);
    }

    @ApiOperation(value = "/getList",tags = "获取list")
    @RequestMapping(value = "/getList",method = RequestMethod.POST)
    public CommonResult getList(@RequestBody Page<WorkInfo> page){
        IPage<WorkInfo> result = workInfoService.getList(page);
        return CommonResult.success(result.getRecords());
    }

    @ApiOperation(value = "/getOwnerWork",tags = "获取自己的作品")
    @RequestMapping(value = "/getOwnerWork",method = RequestMethod.GET)
    public CommonResult getOwnerWork(@RequestBody Page<WorkInfo> page){
        IPage<WorkInfo> result = workInfoService.getOwnerWork(page);
        return CommonResult.success(result);
    }

    @ApiOperation(value = "/likeWork",tags = "给作品点赞")
    @RequestMapping(value = "/likeWork",method = RequestMethod.GET)
    public CommonResult likeWork(String workId){
       int result = workLikeService.setWorkLikeStatus(workId,IntegerConfigEnum.WORK_LIKE.getValue());
       if(result > 0){
           workLikeService.increaseWorkLike(workId);
       }
        return CommonResult.success(workId);
    }

    @ApiOperation(value = "/ulikeWork",tags = "取消点赞")
    @RequestMapping(value = "/ulikeWork",method = RequestMethod.GET)
    public CommonResult ulikeWork(String workId){
        int result = workLikeService.setWorkLikeStatus(workId,IntegerConfigEnum.WORK_DISLIKE.getValue());
        if(result>0){
            workLikeService.decreaseWorkLike(workId);
        }
        return CommonResult.success(workId);
    }
}
