package com.lwc.controller;

import com.lwc.common.CommonResult;
import com.lwc.dto.OssCallbackResult;
import com.lwc.dto.OssPolicyResult;
import com.lwc.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 阿里云oss控制类
 * @author liuweichun
 * @date 2022/8/21 15:25
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@RestController
@Api(tags = "OssController", description = "Oss管理")
@RequestMapping("/oss")
public class OssController {
    @Autowired
    private OssService ossService;

    @ApiOperation(value = "Oss上传签名生成")
    @RequestMapping(value = "/policy", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<OssPolicyResult> policy() {
        OssPolicyResult result = ossService.policy();
        return CommonResult.success(result,"成功");
    }

    @ApiOperation(value = "Oss上传成功回调")
    @RequestMapping(value = "callback", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<OssCallbackResult> callback(HttpServletRequest request) {
        OssCallbackResult ossCallbackResult = ossService.callBack(request);
        return CommonResult.success(ossCallbackResult,"成功");
    }
    @RequestMapping("/test")
    public CommonResult test() throws FileNotFoundException, UnsupportedEncodingException, JSONException {
        OssPolicyResult objec = ossService.getPolicy();
        return CommonResult.success(objec,"sucess");
    }
    @RequestMapping("/getAppToken")
    public CommonResult getAppToken(){
        Map<String,String> map = ossService.getAppToken();
        return CommonResult.success(map,"ok");
    }

}
