package com.lwc.controller;

import com.lwc.common.CommonPage;
import com.lwc.common.CommonResult;
import com.lwc.doc.UserCollection;
import com.lwc.service.UserCollectionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 用户收藏
 * @author liuweichun
 * @date 2022/8/23 17:31
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@RequestMapping("/userCollection")
@ApiOperation("用户收藏")
@RestController
public class UserCollectionController {
    @Autowired
    private UserCollectionService userCollectionService;

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public CommonResult create(@RequestBody UserCollection userCollection){
        userCollectionService.add(userCollection);
        return CommonResult.success("","success");
    }

    @ApiOperation("删除商品收藏")
    @RequestMapping(value = "/delete/{workId}", method = RequestMethod.GET)
    public CommonResult delete(@PathVariable String workId) {
        int count = userCollectionService.delete(workId);
        if (count > 0) {
            return CommonResult.success(count,"success");
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("显示当前用户商品收藏列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserCollection>> list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                         @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        Page<UserCollection> page = userCollectionService.list(pageNum,pageSize);
        return CommonResult.success(CommonPage.restPage(page),"ok");
    }

    @ApiOperation("显示商品收藏详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public CommonResult<UserCollection> detail(@RequestParam String workId) {
        UserCollection memberProductCollection = userCollectionService.detail(workId);
        return CommonResult.success(memberProductCollection,"success");
    }

    @ApiOperation("清空当前用户商品收藏列表")
    @RequestMapping(value = "/clear", method = RequestMethod.POST)
    public CommonResult clear() {
        userCollectionService.clear();
        return CommonResult.success(null,"");
    }
}
