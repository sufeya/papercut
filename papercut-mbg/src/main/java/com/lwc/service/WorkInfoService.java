package com.lwc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lwc.entity.WorkInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuweichun
 * @date 2022/8/11 9:53
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Service
public interface WorkInfoService extends IService<WorkInfo> {
    /**
     * 创建作品
     * @param workInfo
     * @return
     */
    boolean createWork(WorkInfo workInfo);

    /**
     * 更新作品
     */
    boolean updateWork(WorkInfo workInfo);

    /**
     * 删除相关作品
     */
    boolean deleteWork(List<String> ids);

    /**
     * 跟据关键字模糊查询作评
     */
    IPage<WorkInfo> searchWork(String keyWork,Page<WorkInfo> infoIPage);

    /**
     * 分页查询,查询所有公开的和自己关注的作品
     * @param page
     * @return
     */
    IPage<WorkInfo> getList(Page<WorkInfo> page);

    /**
     *获取个人作品
     */
    IPage<WorkInfo> getOwnerWork(Page<WorkInfo> page);

    /**
     * 获取个人作品的数量
     */
    int getUserWorkCount();
}
