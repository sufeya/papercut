package com.lwc.controller;

import com.aliyuncs.CommonRequest;
import com.lwc.common.CommonResult;
import com.lwc.doc.UserCollection;
import com.lwc.doc.UserReadHistory;
import com.lwc.service.UserReadHistoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liuweichun
 * @date 2022/8/23 16:32
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@RestController
@RequestMapping("/userReadHistory")
@ApiOperation("用户浏览历史")
public class UserReadHistoryController {

    @Autowired
    private UserReadHistoryService userReadHistoryService;

    @ApiOperation("创建浏览记录")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public CommonResult create(@RequestBody UserReadHistory userReadHistory){
        int count = userReadHistoryService.create(userReadHistory);
        return CommonResult.success(count,"success");
    }

    @ApiOperation("删除浏览记录")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public CommonResult delete(@RequestBody List<String> ids){
        int count = userReadHistoryService.delete(ids);
        if(count>0){
           return CommonResult.success(count,"success");
        }else{
            return CommonResult.failed();
        }
    }
    @ApiOperation("分页查询")
    @RequestMapping(value = "/list/{pageSize}/{pageNum}",method = RequestMethod.GET)
    public CommonResult list(@PathVariable("pageSize") Integer pageSize,@PathVariable("pageNum") Integer pageNum){
        Page<UserReadHistory> userReadHistories = userReadHistoryService.list(pageNum,pageSize);
        return CommonResult.success(userReadHistories,"success");
    }

    @ApiOperation("清除记录")
    @RequestMapping(value = "/clear",method = RequestMethod.GET)
    public CommonResult clear(){
        userReadHistoryService.clear();
        return CommonResult.success(null,"清除完毕");
    }
}
