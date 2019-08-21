package com.hlife.server.scale.controller;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageResult;
import com.hlife.framework.base.ResultVO;
import com.hlife.server.scale.model.HeightAndWeight;
import com.hlife.server.scale.service.HeightAndWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 身高体重量表控制器
 */
@RestController
@RequestMapping(value = "/heightAndWeight")
public class HeightAndWeightController {

    @Autowired
    private HeightAndWeightService heightAndWeightService;


    /**
     *
     * @param heightAndWeight
     * @return
     */
    @PostMapping(value = "/getHeightAdnWeightTestResult")
    public ResultVO<HeightAndWeight> getHeightAdnWeightTestResult(@RequestBody HeightAndWeight heightAndWeight) {
        return new ResultVO<>(this.heightAndWeightService.getHeightAdnWeightTestResult(heightAndWeight)) ;
    }


    @PostMapping(value = "/saveOrEditHeightAndWeight")
    public ResultVO<HeightAndWeight> saveOrEditHeightAndWeight(@RequestBody HeightAndWeight heightAndWeight) {
        return new ResultVO<>(this.heightAndWeightService.saveOrEditHeightAndWeight(heightAndWeight)) ;
    }

    @PostMapping(value = "findHeightAndWeightHistoryPagination")
    public ResultVO<PageResult<HeightAndWeight>> findHeightAndWeightHistoryPagination(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(this.heightAndWeightService.findHeightAndWeightHistoryPagination(jsonObject));
    }

    @GetMapping(value = "findHeightAndWeightGraphData/{guid}/{rand}")
    public ResultVO<JSONObject> findHeightAndWeightGraphData(@PathVariable("guid") String guid) {
        return new ResultVO<>(this.heightAndWeightService.findHeightAndWeightGraphData(guid));
    }

    /**
     * 微信测量数据保存
     * @param heightAndWeight 微信测量的数据
     * @return 保存成功的数据
     */
    @PostMapping(value = "/wpaSave")
    public ResultVO<HeightAndWeight> wpaSave(@RequestBody HeightAndWeight heightAndWeight) {
        return new ResultVO<>(this.heightAndWeightService.wpaSave(heightAndWeight)) ;
    }
}
