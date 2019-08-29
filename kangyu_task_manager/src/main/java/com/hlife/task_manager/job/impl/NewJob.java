package com.hlife.task_manager.job.impl;

import com.hlife.task_manager.job.BaseJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.impl.JobDetailImpl;

@Slf4j
public class NewJob implements BaseJob {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //log.error("New Job执行时间: {}", new Date());
        JobDetailImpl jobDetail = (JobDetailImpl) context.getJobDetail();
        log.info("group: {}", jobDetail.getGroup());
        log.info("description: {}", jobDetail.getDescription());
        log.info("mergedJobDataMap: {}", context.getMergedJobDataMap());

        //ApplicationContext applicationContext = new W
    }
}
