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
@RequestMapping(value = "task/job")
public class JobController {
    @Autowired
    private IJobAndTriggerService iJobAndTriggerService;

    @PostMapping(value = "/addjob")
    public ResultVO<String> addJob(@RequestBody JSONObject jsonObject) {
        this.iJobAndTriggerService.addJob(jsonObject);
        return new ResultVO<>("操作成功");
    }

    @PostMapping(value = "/pausejob")
    public ResultVO<String> pauseJob(@RequestBody JSONObject jsonObject) {
        this.iJobAndTriggerService.pauseJob(jsonObject);
        return new ResultVO<>("操作成功");
    }


    @PostMapping(value = "/resumejob")
    public ResultVO<String> resumeJob(@RequestBody JSONObject jsonObject) {
        this.iJobAndTriggerService.resumeJob(jsonObject);
        return new ResultVO<>("操作成功");
    }

    @PostMapping(value = "/reschedulejob")
    public ResultVO<String> rescheduleJob(@RequestBody JSONObject jsonObject) {
        this.iJobAndTriggerService.rescheduleJob(jsonObject);
        return new ResultVO<>("操作成功");
    }

    @PostMapping(value = "/deletejob")
    public ResultVO<String> deleteJob(@RequestBody JSONObject jsonObject) {
        this.iJobAndTriggerService.deleteJob(jsonObject);
        return new ResultVO<>("操作成功");
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
