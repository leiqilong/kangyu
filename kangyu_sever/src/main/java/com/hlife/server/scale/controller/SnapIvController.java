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

    /**
     * 保存修改 spanIv测量结果
     *
     * @param snapIv panIv测量结果
     * @return panIv测量结果
     */
    @PostMapping(value = "saveOrEditSnapIv")
    public ResultVO<SnapIv> saveOrEditSnapIv(@RequestBody SnapIv snapIv) {
        return new ResultVO<>(this.snapIvService.saveOrEditSnapIv(snapIv));
    }

    /**
     * 获取 spanIv测量结果历史列表
     *
     * @param jsonObject <br/>
     *                   guid 患者id</>
     *
     * @return spanIv测量结果历史列表
     */
    @PostMapping(value = "findSnapIvHistory")
    public ResultVO<List<SnapIv>> findSnapIvHistory(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(this.snapIvService.findSnapIvHistory(jsonObject));
    }

    /**
     * 获取 spanIv测量结果历史分页
     *
     * @param jsonObject panIv测量结果历史分页
     * @return panIv测量结果历史分页
     */
    @PostMapping(value = "findSnapIvHistoryPagination")
    public ResultVO<PageResult<SnapIv>> findSnapIvHistoryPagination(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(this.snapIvService.findSnapIvHistoryPagination(jsonObject));
    }
}
