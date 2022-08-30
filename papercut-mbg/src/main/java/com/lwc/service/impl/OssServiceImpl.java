package com.lwc.service.impl;

import cn.hutool.json.JSONUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.lwc.config.ConfigEnum;
import com.lwc.dto.OssCallbackParam;
import com.lwc.dto.OssCallbackResult;
import com.lwc.dto.OssPolicyResult;
import com.lwc.service.OssService;
import com.sun.javafx.binding.StringFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author liuweichun
 * @date 2022/8/21 14:53
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Service
public class OssServiceImpl implements OssService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OssServiceImpl.class);
    @Value("${aliyun.oss.policy.expire}")
    private long ALIYUN_OSS_EXPIRE;
    @Value("${aliyun.oss.maxSize}")
    private int ALIYUN_OSS_MAX_SIZE;
    @Value("${aliyun.oss.callback}")
    private String ALIYUN_OSS_CALLBACK;
    @Value("${aliyun.oss.bucketName}")
    private String ALIYUN_OSS_BUCKET_NAME;
    @Value("${aliyun.oss.endpoint}")
    private String ALIYUN_OSS_ENDPOINT;
    @Value("${aliyun.oss.dir.prefix}")
    private String ALIYUN_OSS_DIR_PREFIX;

    @Value("${aliyun.oss.accessKeyId}")
    private String ALIYUN_OSS_ACCESSID;

    @Value("${aliyun.oss.roleArnacs}")
    private String  ALIYUN_OSS_ROLE;
    @Value("${aliyun.oss.accessKeySecret}")
    private String ALIYUN_OSS_SECRET;

    @Autowired
    private OSSClient ossClient;
    @Override
    public OssPolicyResult policy() {
        OssPolicyResult result = new OssPolicyResult();
        // 存储目录
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dir = ALIYUN_OSS_DIR_PREFIX+sdf.format(new Date());
        // 签名有效期
        long expireEndTime = System.currentTimeMillis() + ALIYUN_OSS_EXPIRE * 1000;
        Date expiration = new Date(expireEndTime);
        // 文件大小
        long maxSize = ALIYUN_OSS_MAX_SIZE * 1024 * 1024;
        // 回调
        OssCallbackParam callback = new OssCallbackParam();
        callback.setCallbackUrl(ALIYUN_OSS_CALLBACK);
        callback.setCallbackBody("filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
        callback.setCallbackBodyType("application/x-www-form-urlencoded");
        // 提交节点
        String action = "http://" + ALIYUN_OSS_BUCKET_NAME + "." + ALIYUN_OSS_ENDPOINT;
        try {
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxSize);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String policy = BinaryUtil.toBase64String(binaryData);
            String signature = ossClient.calculatePostSignature(postPolicy);
            String callbackData = BinaryUtil.toBase64String(JSONUtil.parse(callback).toString().getBytes("utf-8"));
            // 返回结果
            result.setAccessKeyId(ossClient.getCredentialsProvider().getCredentials().getAccessKeyId());
            result.setPolicy(policy);
            result.setSignature(signature);
            result.setDir(dir);
            result.setCallback(callbackData);
            result.setHost(action);
        } catch (Exception e) {
            LOGGER.error("签名生成失败", e);
        }
        return result;
    }

    @Override
    public OssCallbackResult callBack(HttpServletRequest request) {
        OssCallbackResult result= new OssCallbackResult();
        String filename = request.getParameter("filename");
        filename = "http://".concat(ALIYUN_OSS_BUCKET_NAME).concat(".").concat(ALIYUN_OSS_ENDPOINT).concat("/").concat(filename);
        result.setFilename(filename);
        result.setSize(request.getParameter("size"));
        result.setMimeType(request.getParameter("mimeType"));
        result.setWidth(request.getParameter("width"));
        result.setHeight(request.getParameter("height"));
        return result;
    }

    @Override
    public void uploadFile() throws FileNotFoundException {
        String bucketName = "papercut-info"; // Bucket的名称
        String localFile = "C:\\Users\\liu\\Pictures\\test.png";
        String fileKeyName = "photo2.jpg"; // 上传至阿里云的名称，可以使用 / 附带文件夹
        InputStream inputStream = new FileInputStream(localFile);
        PutObjectResult putObjectResult = ossClient.putObject(bucketName, fileKeyName, inputStream);
        System.out.println(putObjectResult.getETag());
    }

    @Override
    public  OssPolicyResult getPolicy() throws JSONException, UnsupportedEncodingException {
        // host的格式为 bucketname.endpoint
        String host = StringFormatter.concat("https://", ALIYUN_OSS_BUCKET_NAME, ".", ALIYUN_OSS_ENDPOINT).getValue();
        // callbackUrl为 上传回调服务器的URL，请将下面的IP和Port配置为您自己的真实信息。
        // String callbackUrl = "http://88.88.88.88:8888";
        // 每一天产生一个文件夹
        String dir = LocalDate.now().toString() + "/"; // 用户上传文件时指定的前缀,如果是 / 则自动检测为文件夹。

        OssPolicyResult result = new OssPolicyResult();
        long expireTime = 100;
        long expireEndTime = System.currentTimeMillis() + expireTime * 1000; //过期时间 100 秒
        Date expiration = new Date(expireEndTime);
        // PostObject请求最大可支持的文件大小为5 GB，即CONTENT_LENGTH_RANGE为5*1024*1024*1024。
        PolicyConditions policyConds = new PolicyConditions();
        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
        policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
        String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
        byte[] binaryData = postPolicy.getBytes("utf-8");
        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
        String postSignature = ossClient.calculatePostSignature(postPolicy);

        result.setHost(host);
        result.setDir(dir);
        result.setSignature(postSignature);
        result.setAccessKeyId(ALIYUN_OSS_ACCESSID);
        result.setPolicy(encodedPolicy);

        return result;

    }

    @Override
    public Map<String,String> getAppToken() {
        //指定token授权角色
        String sessionRoleName = "alice-001";
        // 此处必须为 HTTPS
        ProtocolType protocolType = ProtocolType.HTTPS;
        try{
            // 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
            IClientProfile profile = DefaultProfile.getProfile(ConfigEnum.ALIYUN_OSS_REGION.getPath(), ALIYUN_OSS_ACCESSID, ALIYUN_OSS_SECRET);
            DefaultAcsClient client = new DefaultAcsClient(profile);

            // 创建一个 AssumeRoleRequest 并设置请求参数
            final AssumeRoleRequest request = new AssumeRoleRequest();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            request.setVersion("2015-04-01");
            request.setMethod(MethodType.POST);
            request.setProtocol(protocolType);

            request.setRoleArn(ALIYUN_OSS_ROLE);
            request.setRoleSessionName(sessionRoleName);

            request.setDurationSeconds(ALIYUN_OSS_EXPIRE*10);

            //获取响应数据
            AssumeRoleResponse response = client.getAcsResponse(request);

            Map<String, String> respMap = new LinkedHashMap<String, String>();
            respMap.put("StatusCode", "200");
            respMap.put("AccessKeyId", response.getCredentials().getAccessKeyId());
            respMap.put("AccessKeySecret", response.getCredentials().getAccessKeySecret());
            respMap.put("SecurityToken", response.getCredentials().getSecurityToken());
            respMap.put("Expiration", response.getCredentials().getExpiration());
            return respMap;

        }catch (Exception e){
            LOGGER.info("获取token失败{}",e.getMessage());
        }
        return null;
    }
}
