package com.hlife.server.scale.controller;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageResult;
import com.hlife.framework.base.ResultVO;
import com.hlife.server.scale.model.SnapIv;
import com.hlife.server.scale.service.SnapIvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * SNAP_IV 控制器
 */
@RestController
@RequestMapping(value = "snapIv")
public class SnapIvController {

    @Autowired
    private SnapIvService snapIvService;

    @PostMapping(value = "saveOrEditSnapIv")
    public ResultVO<SnapIv> saveOrEditSnapIv(@RequestBody SnapIv snapIv) {
        return new ResultVO<>(this.snapIvService.saveOrEditSnapIv(snapIv));
    }

    @PostMapping(value = "findSnapIvHistory")
    public ResultVO<List<SnapIv>> findSnapIvHistory(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(this.snapIvService.findSnapIvHistory(jsonObject));
    }

    @PostMapping(value = "findSnapIvHistoryPagination")
    public ResultVO<PageResult<SnapIv>> findSnapIvHistoryPagination(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(this.snapIvService.findSnapIvHistoryPagination(jsonObject));
    }
}
