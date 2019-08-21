package com.hlife.server.core.controller;

import com.hlife.framework.base.ResultVO;
import com.hlife.server.core.model.ChildOtherArchivesInfo;
import com.hlife.server.core.service.ChildOtherArchivesInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "childOtherArchivesInfoController")
public class ChildOtherArchivesInfoController {

    @Autowired
    private ChildOtherArchivesInfoService childOtherArchivesInfoService;

    @GetMapping("test/{guid}")
    public ResultVO<ChildOtherArchivesInfo> test(@PathVariable("guid") String guid) {
        return new ResultVO<>(this.childOtherArchivesInfoService.findOne(guid));
    }
}
