package com.lwc.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lwc.common.CommonResult;
import com.lwc.entity.Tag;
import com.lwc.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author liuweichun
 * @date 2022/8/27 10:17
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@RestController
@Api(value = "/tag",tags = "用户标签")
@RequestMapping("/tag")
public class UserTagController {
    @Autowired
    private TagService tagService;

    @ApiOperation(value = "/create",tags = "创建标签")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public CommonResult create(@RequestBody Tag tag){
        tagService.addUserTag(tag);
       return  CommonResult.success(tag);
    }

    @ApiOperation(value = "/userGetTag",tags = "用户获取标签")
    @RequestMapping(value = "/userGetTag" ,method = RequestMethod.GET)
    public CommonResult userGetTag(String tagId){
       boolean result = tagService.userGetTag(tagId);
       if(result){
           return CommonResult.success(tagId);
       }else{
          return CommonResult.failed("领取失败，加吧劲创作噢");
       }
    }

    @RequestMapping(value = "/getAllTag",method = RequestMethod.GET)
    @ApiOperation(value = "/getAllTag",tags = "获取所有标签")
    public CommonResult getAllTag(Page<Tag> page){
        IPage<Tag> pages = tagService.getAllPage(page);
        return CommonResult.success(pages);
    }

    @RequestMapping(value = "/allUserTag",method = RequestMethod.GET)
    @ApiOperation(value = "/allUserTag",tags = "获取所有用户的标签")
    public CommonResult allUserTag(Page<Tag> page){
       IPage<Tag> result = tagService.getUserTagPage(page);
       return CommonResult.success(result);
    }
}
