package com.hlife.task_manager.service;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.hlife.task_manager.model.JobAndTrigger;

public interface IJobAndTriggerService {
	PageInfo<JobAndTrigger> getJobAndTriggerDetails(int pageNum, int pageSize);
	
	void addJob(JSONObject jsonObject);

    void pauseJob(JSONObject jsonObject);

    void resumeJob(JSONObject jsonObject);

    void rescheduleJob(JSONObject jsonObject);

    void deleteJob(JSONObject jsonObject);
}
