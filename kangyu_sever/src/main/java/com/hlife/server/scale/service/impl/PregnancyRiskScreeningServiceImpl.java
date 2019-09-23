package com.hlife.server.scale.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.framework.util.GuidUtil;
import com.hlife.framework.util.StringUtil;
import com.hlife.server.scale.dao.PregnancyRiskScreeningMapper;
import com.hlife.server.scale.model.PregnancyRiskScreening;
import com.hlife.server.scale.service.PregnancyRiskScreeningServcie;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 孕期风险筛查业务层实现
 */
@Service
public class PregnancyRiskScreeningServiceImpl implements PregnancyRiskScreeningServcie {

    @Autowired
    private PregnancyRiskScreeningMapper pregnancyRiskScreeningMapper;

    @Override
    public PregnancyRiskScreening saveOrEditPregnancyRiskScreening(PregnancyRiskScreening pregnancyRiskScreening) {
        String prsId = pregnancyRiskScreening.getPrsId();
        if (StringUtil.stringIsNotNull(prsId)) {
            PregnancyRiskScreening prs = this.pregnancyRiskScreeningMapper.findOneByPrsId(prsId);
            if (prs == null) {
                throw new RuntimeException("该数据已不存在");
            }
            pregnancyRiskScreening.setCreateTime(prs.getCreateTime());
            this.pregnancyRiskScreeningMapper.deleteOne(prsId);
        } else {
            pregnancyRiskScreening.setPrsId(GuidUtil.generateGuid()).setCreateTime(new Date());
        }

        return this.pregnancyRiskScreeningMapper.savePregnancyRiskScreening(pregnancyRiskScreening);
    }

    @Override
    public PageResult<PregnancyRiskScreening> findPregnancyRiskScreeningHistoryPagination(JSONObject jsonObject) {
        Document queryDoc = new Document();
        String guid = jsonObject.getString("guid");
        if (StringUtil.stringIsNull(guid)) {
            throw new RuntimeException("传入患者guid为空");
        }

        queryDoc.put("guid", guid);

        PageParam pageParam = new PageParam(jsonObject.getInteger(PageParam.PAGE_SIZE),
                jsonObject.getInteger(PageParam.PAGE_NUM));
        return this.pregnancyRiskScreeningMapper.findPregnancyRiskScreeningHistoryPagination(queryDoc, pageParam);
    }
}
