package com.hlife.task_manager.dao;


import com.hlife.task_manager.model.JobAndTrigger;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JobAndTriggerMapper {
	List<JobAndTrigger> getJobAndTriggerDetails();
}
