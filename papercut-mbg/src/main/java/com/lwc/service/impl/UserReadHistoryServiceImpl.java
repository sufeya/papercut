package com.lwc.service.impl;

import com.lwc.doc.UserReadHistory;
import com.lwc.entity.UserInfo;
import com.lwc.repository.UserReadHistoryRepository;
import com.lwc.service.UserInfoService;
import com.lwc.service.UserReadHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户历史记录服务类
 * @author liuweichun
 * @date 2022/8/23 14:36
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Service
public class UserReadHistoryServiceImpl implements UserReadHistoryService {
    @Autowired
    private UserReadHistoryRepository userReadHistoryRepository;

    @Autowired
    private UserInfoService userInfoService;
    @Override
    public int create(UserReadHistory userReadHistory) {
        UserInfo userInfo = userInfoService.getCurrentUser();
        userReadHistory.setUserId(userInfo.getId());
        userReadHistory.setUserIcon(userInfo.getIcon());
        userReadHistory.setUserName(userInfo.getUsername());
        userReadHistory.setId(null);
        userReadHistory.setCreateDate(LocalDateTime.now());
        userReadHistoryRepository.save(userReadHistory);
        return 1;
    }

    @Override
    public int delete(List<String> ids) {
        List<UserReadHistory> deleteList = new ArrayList<>();
        for(String id : ids){
            UserReadHistory userReadHistory = new UserReadHistory();
            userReadHistory.setId(id);
            deleteList.add(userReadHistory);
        }
        userReadHistoryRepository.deleteAll(deleteList);
        return ids.size();
    }

    @Override
    public Page<UserReadHistory> list(Integer pageNum, Integer pageSize) {
        UserInfo userInfo = userInfoService.getCurrentUser();
        Pageable pageable = PageRequest.of(pageNum-1,pageSize);
        return userReadHistoryRepository.findByUserIdOrderByCreateDate(userInfo.getId(),pageable);
    }

    @Override
    public void clear() {
        UserInfo userInfo = userInfoService.getCurrentUser();
        userReadHistoryRepository.deleteAllByUserId(userInfo.getId());
    }
}
