package com.ydf.user.config;

import com.ydf.user.mapper.TaskMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * spring内置定时任务(不用)
 */
@Component
@Slf4j
public class ScheduleTaskConfig {

//    @Autowired
//    private TaskMapper taskMapper;

//    /**
//     * 测试定时任务
//     * 每隔五秒执行
//     */
//    @Scheduled(cron = "0/5 * * * * ?")
//    public void test(){
//        taskMapper.deleteVolunteer();
//        log.info("定时任务");
//    }

//    /**
//     * 每隔一个小时执行一次
//     */
//    @Scheduled(cron = "0 0 0/1 * * ? ")
//    public void deleteVolunteer(){
//        taskMapper.deleteVolunteer();
//    }

}
