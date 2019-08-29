package com.hlife.task_manager.job.impl;

import com.hlife.task_manager.job.BaseJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

@Slf4j
public class HelloJob  implements BaseJob {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.error("New Job执行时间: {}", new Date());
    }
}
