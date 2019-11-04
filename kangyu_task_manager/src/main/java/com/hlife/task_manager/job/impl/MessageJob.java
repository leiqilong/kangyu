package com.hlife.task_manager.job.impl;

import com.hlife.task_manager.job.BaseJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
public class MessageJob implements BaseJob {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        log.debug("MergedJobDataMap=={}", jobExecutionContext.getMergedJobDataMap());
    }
}
