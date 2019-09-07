package com.hlife.task_manager.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hlife.task_manager.dao.JobAndTriggerMapper;
import com.hlife.task_manager.job.BaseJob;
import com.hlife.task_manager.model.JobAndTrigger;
import com.hlife.task_manager.service.IJobAndTriggerService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class JobAndTriggerImpl implements IJobAndTriggerService {

    @Resource
    private JobAndTriggerMapper jobAndTriggerMapper;

    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;

    public PageInfo<JobAndTrigger> getJobAndTriggerDetails(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<JobAndTrigger> list = jobAndTriggerMapper.getJobAndTriggerDetails();
        return new PageInfo<>(list);
    }

    @Override
    public void addJob(JSONObject jsonObject) {
        String jobClassName = jsonObject.getString("jobClassName");
        String jobGroupName = jsonObject.getString("jobGroupName");
        String cronExpression = jsonObject.getString("cronExpression");
        JSONObject jobData = jsonObject.getJSONObject("jobData");
        try {
            addJob(jobClassName, jobGroupName, cronExpression, jobData);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void pauseJob(JSONObject jsonObject) {
        String jobClassName = jsonObject.getString("jobClassName");
        String jobGroupName = jsonObject.getString("jobGroupName");
        try {
            scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void resumeJob(JSONObject jsonObject) {
        String jobClassName = jsonObject.getString("jobClassName");
        String jobGroupName = jsonObject.getString("jobGroupName");
        try {
            scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rescheduleJob(JSONObject jsonObject) {
        String jobClassName = jsonObject.getString("jobClassName");
        String jobGroupName = jsonObject.getString("jobGroupName");
        String cronExpression = jsonObject.getString("cronExpression");
        rescheduleJob(jobClassName, jobGroupName, cronExpression);
    }

    @Override
    public void deleteJob(JSONObject jsonObject) {
        String jobClassName = jsonObject.getString("jobClassName");
        String jobGroupName = jsonObject.getString("jobGroupName");

        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
            scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addJob(String jobClassName, String jobGroupName, String cronExpression, JSONObject jobData) throws Exception {
        // 启动调度器
        scheduler.start();

        // 构建job信息
        JobDetail jobDetail =
                JobBuilder.newJob(getClass(jobClassName).getClass())
                        .withIdentity(jobClassName, jobGroupName)
                        .usingJobData(new JobDataMap(jobData))
                        .build();

        // 表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        // 按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger =
                TriggerBuilder.newTrigger()
                        .withIdentity(jobClassName, jobGroupName)
                        .withSchedule(scheduleBuilder)
                        .usingJobData(new JobDataMap(jobData))
                        .build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new Exception("创建定时任务失败");
        }
    }

    private void rescheduleJob(String jobClassName, String jobGroupName, String cronExpression) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            System.out.println("更新定时任务失败" + e);
            throw new RuntimeException("更新定时任务失败");
        }
    }

    private static BaseJob getClass(String classname) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> class1 = Class.forName(classname);
        return (BaseJob) class1.newInstance();
    }
}
