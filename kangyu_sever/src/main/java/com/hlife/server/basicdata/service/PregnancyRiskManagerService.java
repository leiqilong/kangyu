package com.hlife.server.basicdata.service;

import com.alibaba.fastjson.JSONObject;
import com.hlife.server.basicdata.model.PregnancyRiskManager;

import java.util.List;

/**
 * 孕产妇妊娠风险管理业务层接口
 */
public interface PregnancyRiskManagerService {

    /**
     * 保存妊娠风险信息
     *
     * @param pregnancyRiskManager 妊娠风险信息
     * @return 保存的妊娠风险信息
     */
    PregnancyRiskManager saveOrEditPregnancyRiskManager(PregnancyRiskManager pregnancyRiskManager);

    long deletePregnancyRiskManager(String prmId);

    /**
     * 查询妊娠风险信息列表
     *
     * @param jsonObject 参数
     * @return 妊娠风险信息列表
     */
    List<PregnancyRiskManager> searchPregnancyRisk(JSONObject jsonObject);

    /**
     * 查询妊娠风险信息树
     *
     * @param jsonObject 参数
     * @return 妊娠风险信息树
     */
    List<PregnancyRiskManager> searchPregnancyRiskTree(JSONObject jsonObject);
}
