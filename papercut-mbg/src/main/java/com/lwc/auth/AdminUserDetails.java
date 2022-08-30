package com.lwc.auth;

import com.lwc.entity.UserInfo;
import com.lwc.entity.UserResoures;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liuweichun
 * @date 2022/8/11 17:45
 * @company Hangzhou Yunphant internet technology co.ltd
 */
public class AdminUserDetails implements UserDetails {
    //后台用户信息
    private UserInfo userInfo;
    //用户所能访问的资源
    private List<UserResoures> resoures;
    //这里可以添加权限进行限制
    public AdminUserDetails(UserInfo userInfo,List<UserResoures> resoures){
        this.resoures = resoures;
        this.userInfo = userInfo;
    }
    //然后在这里设置验证
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(resoures != null && resoures.size()>0){
            return resoures.stream().map(resoure->{return new SimpleGrantedAuthority(resoure.getId()+":"+resoure.getResourceName());
            }).collect(Collectors.toList());
        }
      return null;
    }

    @Override
    public String getPassword() {
        return userInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return userInfo.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userInfo.getStatus() == 1;
    }
    public UserInfo getUserInfo(){
        return userInfo;
    }


}
