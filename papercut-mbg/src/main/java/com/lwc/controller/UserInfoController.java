package com.lwc.controller;

import com.lwc.common.CommonResult;
import com.lwc.entity.UserInfo;
import com.lwc.service.UserInfoService;
import com.lwc.util.FileUploadUtil;
import com.lwc.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author liuweichun
 * @date 2022/8/9 10:43
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@RequestMapping("/admin")
@RequiredArgsConstructor
@RestController
@Api(tags = "UserInfoController",value = "用户管理")
public class UserInfoController {
    private final UserInfoService userInfoService;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @PostMapping("/add")
    public CommonResult addUser(@RequestBody UserInfo userInfo){
        userInfoService.save(userInfo);
        return CommonResult.success(userInfo,"成功");
    }
    @GetMapping("/hello")
    public String  hello(){
        return "hellow";
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public CommonResult register(@RequestBody UserVo userVo){
        UserInfo userInfo = userInfoService.register(userVo);
        return CommonResult.success(userInfo,"注册成功");
    }

    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public CommonResult login(@RequestBody UserVo userVo){
        String token = userInfoService.login(userVo);
        if(token == null){
            return CommonResult.failed("用户名或者密码错误");
        }else{
            return CommonResult.success(token,"登入成功");
        }
    }

    @PostMapping("/uploadFile")
    public CommonResult uploadFile(MultipartFile multipartFile){
        String url = FileUploadUtil.uploadFile(multipartFile);
        return CommonResult.success(url,"成功");
    }
}
