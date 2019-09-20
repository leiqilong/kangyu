package com.hlife.server.basicdata.controller;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.ResultVO;
import com.hlife.server.basicdata.model.PregnancyRiskManager;
import com.hlife.server.basicdata.service.PregnancyRiskManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 孕产妇妊娠风险管理控制器
 */
@RestController
@RequestMapping(value = "/pregnancyRiskManager")
public class PregnancyRiskManagerController {

    @Autowired
    private PregnancyRiskManagerService pregnancyRiskManagerService;

    /**
     * 保存妊娠风险信息
     *
     * @param pregnancyRiskManager 妊娠风险信息
     * @return resultVO.resultData 保存的妊娠风险信息
     */
    @PostMapping(value = "/saveOrEditPregnancyRiskManager")
    public ResultVO<PregnancyRiskManager> saveOrEditPregnancyRiskManager(@RequestBody PregnancyRiskManager pregnancyRiskManager) {
        return new ResultVO<>(this.pregnancyRiskManagerService.saveOrEditPregnancyRiskManager(pregnancyRiskManager));
    }

    /**
     * 删除妊娠风险信息
     *
     * @param prmId 唯一标志
     * @return resultVO.resultData 删除的行数
     */
    @DeleteMapping(value = "/deletePregnancyRiskManager/{prmId}/{rand}")
    public ResultVO<Long> deletePregnancyRiskManager(@PathVariable(value = "prmId") String prmId) {
        return new ResultVO<>(this.pregnancyRiskManagerService.deletePregnancyRiskManager(prmId));
    }


    /**
     * 查询妊娠风险信息树
     *
     * @param jsonObject 参数
     * @return 妊娠风险信息树
     */
    @PostMapping(value = "/searchPregnancyRiskTree")
    public ResultVO<List<PregnancyRiskManager>> searchPregnancyRiskTree(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(this.pregnancyRiskManagerService.searchPregnancyRiskTree(jsonObject));
    }
}
