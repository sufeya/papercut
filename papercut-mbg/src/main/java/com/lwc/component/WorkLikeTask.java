package com.lwc.component;

import com.lwc.service.WorkLikeService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时任务配置
 * @author liuweichun
 * @date 2022/8/26 13:54
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Slf4j
public class WorkLikeTask extends QuartzJobBean {
    @Autowired
    private WorkLikeService workLikeService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("workLikeTask ----- {}",sdf.format(new Date()));

        //将redis中的点赞信息同步到数据库中
        workLikeService.setWorkLikeCountFormRedis();
        workLikeService.saveRecordFromRedis();
    }
}
