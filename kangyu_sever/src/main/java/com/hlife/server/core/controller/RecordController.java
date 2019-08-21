package com.hlife.server.core.controller;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.ResultVO;
import com.hlife.server.core.model.Record;
import com.hlife.server.core.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 档案控制器
 *
 * @author LQL
 **/
@RestController
@RequestMapping(value = "/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @GetMapping(value = "/findOne/{guid}/{rand}")
    public ResultVO<Record> findOne(@PathVariable("guid") String guid) {
        return new ResultVO<>(this.recordService.findOne(guid)) ;
    }

}
