package com.hlife.server.scale.dao;

import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.server.scale.model.PregnancyRiskScreening;
import org.bson.Document;

/**
 * 孕期风险筛查持久层接口
 */
public interface PregnancyRiskScreeningMapper {

    /**
     * 保存筛查结果
     *
     * @param pregnancyRiskScreening 筛查结果
     * @return 筛查结果
     */
    PregnancyRiskScreening savePregnancyRiskScreening(PregnancyRiskScreening pregnancyRiskScreening);

    PregnancyRiskScreening findOneByPrsId(String prsId);

    long deleteOne(String prsId);

    /**
     * 分页查询
     *
     * @param queryDoc  guid 患者guid
     * @param pageParam 分页参数
     * @return 当前页数据
     */
    PageResult<PregnancyRiskScreening> findPregnancyRiskScreeningHistoryPagination(Document queryDoc, PageParam pageParam);
}
