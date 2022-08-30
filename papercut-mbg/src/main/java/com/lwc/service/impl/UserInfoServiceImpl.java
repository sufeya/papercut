package com.lwc.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwc.auth.AdminUserDetails;
import com.lwc.common.CommonResult;
import com.lwc.entity.*;
import com.lwc.exception.ApiException;
import com.lwc.exception.Assert;
import com.lwc.mapper.*;
import com.lwc.service.UserCachService;
import com.lwc.service.UserInfoService;
import com.lwc.util.JwtTokenUtil;
import com.lwc.vo.UpdatePassWordParam;
import com.lwc.vo.UserVo;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户信息接口
 * @author liuweichun
 * @date 2022/8/9 10:40
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoServiceImpl.class);
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserCachService userCachService;

    @Autowired
    private UserResourceMapper userResourceMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserRoleRelationMapper userRoleRelationMapper;

    @Autowired
    private UserRoleResourceRelationMapper userRoleResourceRelationMapper;

    @Override
    public UserInfo register(UserVo userVo) {
        UserInfo userInfo = new UserInfo();
        BeanUtil.copyProperties(userVo,userInfo);
        //设置状态
        userInfo.setStatus(1);
        userInfo.setCreateTime(LocalDateTime.now());
        //查询是否已经注册过
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(UserInfo::getPhone,userInfo.getPhone());
        int count = this.count(lambdaQueryWrapper);
        if(count>0){
            throw new ApiException("注册失败用户已经存在");
        }
        //对密码进行加密操作
        String ecodePassWord = passwordEncoder.encode(userInfo.getPassword());
        userInfo.setPassword(ecodePassWord);
        this.save(userInfo);
        return userInfo;
    }

    @Override
    public UserInfo getUserByUserName(String userName) {
        //现在redis缓存中进行查找
        UserInfo user = userCachService.getUserInfo(userName);
        if(user != null){
           return user;
        }
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserInfo::getUsername,userName);
        user = this.getOne(lambdaQueryWrapper);
        if(user != null){
            //存入缓存中
            userCachService.setUserInfo(user);
            return user;
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUserName(String userName) {
       UserInfo userInfo = getUserByUserName(userName);
       if(userInfo != null ){
           List<UserResoures> resoures = getResource(userInfo.getId());
           return new AdminUserDetails(userInfo,resoures);
       }
        throw  new UsernameNotFoundException("用户名或者密码错误");
    }

    public String login(UserVo userVo){
        UserInfo userInfo = new UserInfo();
        BeanUtil.copyProperties(userVo,userInfo);
        String token = null;
        try{
            UserDetails userDetails = loadUserByUserName(userInfo.getUsername());
            if(!passwordEncoder.matches(userInfo.getPassword(),userDetails.getPassword())){
                Assert.fail("用户密码错误");
            }
            if(!userDetails.isEnabled()){
                Assert.fail("用户以禁用");
            }
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            token = jwtTokenUtil.generateToken(userDetails);
        }catch (AuthenticationException e){
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public void updateUser(String id, UserInfo userInfo) {
        userInfo.setId(id);
        UserInfo rawUser = this.getById(id);
        //如果密码相同则不需要进行修改
        if(rawUser.getPassword().equals(userInfo.getPassword())){
            userInfo.setPassword(null);
        }else{
            if(StrUtil.isNotEmpty(userInfo.getPassword())){
                String ecodePassword = passwordEncoder.encode(userInfo.getPassword());
                userInfo.setPassword(ecodePassword);
            }else{
                userInfo.setPassword(null);
            }
        }
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserInfo::getId,id);
        this.update(userInfo,lambdaQueryWrapper);
    }

    @Override
    public IPage<UserInfo> getPage(Page<UserInfo> page, String keyword) {
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(UserInfo::getUsername,keyword);
        return  this.page(page,wrapper);
    }

    @Override
    public String refreshToken(String oldToken) {
        return jwtTokenUtil.refreshHeadToken(oldToken);
    }

    @Override
    public void delteUser(String userId) {
        userCachService.deleteUser(userId);
        this.removeById(userId);
    }

    @Override
    public int updatePassWord(UpdatePassWordParam updatePassWordParam) {
        //参数为空
        if(StrUtil.isEmpty(updatePassWordParam.getOldPassword()) || StrUtil.isEmpty(updatePassWordParam.getNewPassword())
            || StrUtil.isEmpty(updatePassWordParam.getUsername())){
            return -1;
        }
        //用户名错误
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserInfo::getUsername,updatePassWordParam.getUsername());
        UserInfo userInfo=  this.getOne(lambdaQueryWrapper);
        if(userInfo == null){
            return -2;
        }
        //密码错误
        if(!passwordEncoder.matches(updatePassWordParam.getOldPassword(),userInfo.getPassword())){
            return -3;
        }
        userInfo.setPassword(passwordEncoder.encode(updatePassWordParam.getNewPassword()));
        this.update(userInfo,lambdaQueryWrapper);
        userCachService.deleteUser(userInfo.getId());
        return 1;
    }

    @Override
    public List<UserResoures> getResource(String userId) {

        //用户角色关系
        List<UserRoleRelation> roleRelations = userRoleRelationMapper.selectList(Wrappers.lambdaQuery(UserRoleRelation.class).eq(UserRoleRelation::getUserId,userId));
        if(roleRelations != null && roleRelations.size()>0){
            //获取该用户的所有角色
          List<UserRole> roles = userRoleMapper.selectList(Wrappers.lambdaQuery(UserRole.class).in(UserRole::getId,roleRelations.stream().map(userRoleRelation -> userRoleRelation.getRoleId()).collect(Collectors.toList())));
          if(roles != null && roles.size()>0){
              List<UserRoleResourceRelation> userRoleResourceRelations = userRoleResourceRelationMapper.selectList(Wrappers.lambdaQuery(UserRoleResourceRelation.class).in(UserRoleResourceRelation::getRoleId,roles.stream().map(role -> role.getId()).collect(Collectors.toList())));
              if(userRoleResourceRelations != null && userRoleResourceRelations.size()>0){
                  List<UserResoures> resoures = userResourceMapper.selectList(Wrappers.lambdaQuery(UserResoures.class).in(UserResoures::getId,userRoleResourceRelations.stream().map(roleRelation -> roleRelation.getResourceId()).collect(Collectors.toList())));
                  return resoures;
              }
          }
        }

        return null;
    }

    @Override
    public UserInfo getCurrentUser() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication authentication = ctx.getAuthentication();
        AdminUserDetails adminUserDetails = (AdminUserDetails) authentication.getPrincipal();
        return adminUserDetails.getUserInfo();
    }
}
