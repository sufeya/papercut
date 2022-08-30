package com.lwc.component;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * @author liuweichun
 * @date 2022/8/18 16:06
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public interface DynamicSecurityService {
    /**
     * 加载资源ANT通配符和资源对应MAP
     */
    Map<String, ConfigAttribute> loadDataSource();
}
