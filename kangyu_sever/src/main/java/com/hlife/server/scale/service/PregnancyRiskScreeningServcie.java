package com.hlife.server.scale.service;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageResult;
import com.hlife.server.scale.model.PregnancyRiskScreening;

/**
 * 孕期风险筛查业务层接口
 */
public interface PregnancyRiskScreeningServcie {

    /**
     * 保存筛查结果
     *
     * @param pregnancyRiskScreening 筛查结果
     * @return 筛查结果
     */
    PregnancyRiskScreening saveOrEditPregnancyRiskScreening(PregnancyRiskScreening pregnancyRiskScreening);

    /**
     * 分页查询
     *
     * @param jsonObject pagesize 每页大小
     *                   pagenum 页数
     *                   guid 患者guid
     *
     * @return 当前页数据
     */
    PageResult<PregnancyRiskScreening> findPregnancyRiskScreeningHistoryPagination(JSONObject jsonObject);
}
