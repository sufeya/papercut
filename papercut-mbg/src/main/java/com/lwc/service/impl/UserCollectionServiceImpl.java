package com.lwc.service.impl;

import com.lwc.doc.UserCollection;
import com.lwc.entity.UserInfo;
import com.lwc.repository.UserCollectionRepository;
import com.lwc.service.UserCollectionService;
import com.lwc.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author liuweichun
 * @date 2022/8/23 15:20
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Service
public class UserCollectionServiceImpl implements UserCollectionService {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserCollectionRepository userCollectionRepository;

    @Override
    public int add(UserCollection userCollection) {
        int count = 0;
        UserInfo userInfo = userInfoService.getCurrentUser();
        userCollection.setUserId(userInfo.getId());
        userCollection.setUserName(userInfo.getUsername());
        userCollection.setIcon(userInfo.getIcon());
        userCollection.setCreateDate(LocalDateTime.now());
        UserCollection findCol = userCollectionRepository.findUserCollectionsByUserIdAndAndWorkId(userInfo.getId(),userCollection.getWorkId());
        if(findCol == null){
            userCollectionRepository.save(userCollection);
            count = 1;
        }
        return count;
    }

    @Override
    public int delete(String workId) {
        UserInfo userInfo = userInfoService.getCurrentUser();
        return userCollectionRepository.deleteAllByUserIdAndWorkId(userInfo.getId(),workId);
    }

    @Override
    public Page<UserCollection> list(Integer pageNum, Integer pageSize) {
        UserInfo userInfo = userInfoService.getCurrentUser();
        Pageable pageable = PageRequest.of(pageNum-1,pageSize);
        return userCollectionRepository.findByUserId(userInfo.getId(),pageable);
    }

    @Override
    public UserCollection detail(String workId) {
        UserInfo userInfo = userInfoService.getCurrentUser();
        return userCollectionRepository.findUserCollectionsByUserIdAndAndWorkId(userInfo.getId(),workId);
    }

    @Override
    public void clear() {
        UserInfo user = userInfoService.getCurrentUser();
        userCollectionRepository.deleteAllByUserId(user.getId());
    }
}
