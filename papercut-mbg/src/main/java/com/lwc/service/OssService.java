package com.lwc.service;

import com.lwc.dto.OssCallbackResult;
import com.lwc.dto.OssPolicyResult;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 阿里云对象存储管理类
 * @author liuweichun
 * @date 2022/8/21 13:27
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public interface OssService {
    /**
     * 上传策略
     * @return
     */
    OssPolicyResult policy();

    /**
     * 成功回调
     * @return
     */
    OssCallbackResult callBack(HttpServletRequest request);
    void uploadFile() throws FileNotFoundException;

    OssPolicyResult getPolicy() throws JSONException, UnsupportedEncodingException;

    Map<String,String> getAppToken();
}
