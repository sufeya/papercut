package com.lwc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lwc.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author liuweichun
 * @date 2022/8/9 10:37
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Mapper
public interface UserInfoMapper  extends BaseMapper<UserInfo> {
}
