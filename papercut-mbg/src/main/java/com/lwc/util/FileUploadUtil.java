package com.lwc.util;

import com.lwc.config.ConfigEnum;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author liuweichun
 * @date 2022/8/21 10:05
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public class FileUploadUtil {


    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadUtil.class);


    //单个文件上传
    public  static String uploadFile(MultipartFile imageFile){
        if(imageFile.isEmpty()){
            return null;
        }
        //获取文件名
        String fileName = imageFile.getOriginalFilename();

        //获取文件后缀名
        String suff = null;
        if(fileName.contains(".")){
            suff = fileName.substring(fileName.lastIndexOf("."));
        }else{
            suff = "";
        }
        String filePath = System.getProperty("user.dir") + ConfigEnum.FILE_UPLOAD_PATH.getPath();
        String uuid = UUID.randomUUID().toString().replace("-","");
        String nFileName = uuid+suff;
        // 解决中文路径,图片显示问题
        fileName = UUID.randomUUID() + suff;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try{
            imageFile.transferTo(dest);
        }catch (Exception e){
            LOGGER.info("文件传输出错{}",e.getMessage());
        }

        String url = "/img/"+nFileName;
        return url;
    }
}
