package com.lwc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuweichun
 * @date 2022/8/11 21:33
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Data
@Component
//读取application.xml配置文件中的白名单
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrlsConfig {
    private List<String> urls = new ArrayList<>();
}
