package com.hlife.task_manager.controller;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.hlife.framework.base.ResultVO;
import com.hlife.task_manager.model.JobAndTrigger;
import com.hlife.task_manager.service.IJobAndTriggerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping(value = "/job")
public class JobController {
    @Autowired
    private IJobAndTriggerService iJobAndTriggerService;

    @PostMapping(value = "/addjob")
    public void addJob(@RequestBody JSONObject jsonObject) throws Exception {
        this.iJobAndTriggerService.addJob(jsonObject);
    }

    @PostMapping(value = "/pausejob")
    public void pauseJob(@RequestBody JSONObject jsonObject) throws Exception {
        this.iJobAndTriggerService.pauseJob(jsonObject);
    }


    @PostMapping(value = "/resumejob")
    public void resumeJob(@RequestBody JSONObject jsonObject) {
        this.iJobAndTriggerService.resumeJob(jsonObject);
    }

    @PostMapping(value = "/reschedulejob")
    public void rescheduleJob(@RequestBody JSONObject jsonObject) {
        this.iJobAndTriggerService.rescheduleJob(jsonObject);
    }

    @PostMapping(value = "/deletejob")
    public void deleteJob(@RequestBody JSONObject jsonObject) {
        this.iJobAndTriggerService.deleteJob(jsonObject);
    }


    @GetMapping(value = "/queryjob")
    public ResultVO<Map<String, Object>> queryjob(@RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize) {
        PageInfo<JobAndTrigger> jobAndTrigger = iJobAndTriggerService.getJobAndTriggerDetails(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("JobAndTrigger", jobAndTrigger);
        map.put("number", jobAndTrigger.getTotal());
        return new ResultVO<>(map);
    }
}
