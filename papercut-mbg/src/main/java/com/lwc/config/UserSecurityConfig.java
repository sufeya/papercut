package com.lwc.config;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lwc.component.DynamicSecurityService;
import com.lwc.entity.UserResoures;
import com.lwc.mapper.UserResourceMapper;
import com.lwc.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuweichun
 * @date 2022/8/20 13:53
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserSecurityConfig extends SecurityConfig {
    @Autowired
    private  UserResourceMapper userResourceMapper;

    @Autowired
    private UserInfoService userInfoService;
    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> userInfoService.loadUserByUserName(username);
    }

    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
                List<UserResoures> resourceList = userResourceMapper.selectList(Wrappers.emptyWrapper());
                for (UserResoures resource : resourceList) {
                    map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getResourceName()));
                }
                return map;
            }
        };
    }
}
