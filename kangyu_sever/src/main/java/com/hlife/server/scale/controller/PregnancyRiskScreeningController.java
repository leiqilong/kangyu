package com.hlife.server.scale.controller;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageResult;
import com.hlife.framework.base.ResultVO;
import com.hlife.server.scale.model.PregnancyRiskScreening;
import com.hlife.server.scale.service.PregnancyRiskScreeningServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 孕期风险筛查控制器
 */
@RestController
@RequestMapping(value = "/pregnancyRiskScreening")
public class PregnancyRiskScreeningController {

    @Autowired
    private PregnancyRiskScreeningServcie pregnancyRiskScreeningServcie;

    /**
     * 保存筛查结果
     *
     * @param pregnancyRiskScreening 筛查结果
     * @return resultVO.resultData 筛查结果
     */
    @PostMapping(value = "/saveOrEditPregnancyRiskScreening")
    public ResultVO<PregnancyRiskScreening> saveOrEditPregnancyRiskScreening(@RequestBody PregnancyRiskScreening pregnancyRiskScreening) {
        return new ResultVO<>(this.pregnancyRiskScreeningServcie.saveOrEditPregnancyRiskScreening(pregnancyRiskScreening));
    }

    /**
     * 分页查询
     *
     * @param jsonObject pagesize 每页大小
     *                   pagenum 页数
     *                   guid 患者guid
     *
     * @return resultVO.restultData 当前页数据
     */
    @PostMapping(value = "/findPregnancyRiskScreeningHistoryPagination")
    public ResultVO<PageResult<PregnancyRiskScreening>> findPregnancyRiskScreeningHistoryPagination(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(this.pregnancyRiskScreeningServcie.findPregnancyRiskScreeningHistoryPagination(jsonObject));
    }
}
