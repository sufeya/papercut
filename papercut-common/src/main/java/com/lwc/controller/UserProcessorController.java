package com.lwc.controller;

import com.lwc.annotation.WebLogs;
import com.lwc.common.Response;
import com.lwc.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuweichun
 * @date 2022/8/4 17:16
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@RestController
public class UserProcessorController {
    @Autowired
    private RedisService redisService;

    @GetMapping("/get")
    @WebLogs(describe = "测试")
    public Response test(String parm){
        Response response = new Response<String>();
        response.setData(parm);
        response.setCode("200");
        response.setMsg("cooc");
        redisService.set("test01","helo");
        return response;
    }
}
