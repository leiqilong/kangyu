package com.hlife.server.scale.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.util.StringUtil;
import com.hlife.server.scale.dao.GaugeGrowMapper;
import com.hlife.server.scale.model.GaugeGrow;
import com.hlife.server.scale.service.GaugeGrowService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 身高体重标准数据服务层实现
 */
@Service
public class GaugeGrowServiceImpl implements GaugeGrowService {

    @Autowired
    private GaugeGrowMapper gaugeGrowMapper;

    @Override
    public GaugeGrow findGaugeGrowByMonthOfAgeAndSex(double monthAge, String sex) {
        return this.gaugeGrowMapper.findGaugeGrowByMonthOfAgeAndSex(monthAge, sex);
    }

    @Override
    public List<GaugeGrow> findGaugeGrowByPeriodAndSex(JSONObject gaugeGrowParam) {
        Document queryDoc = new Document();
        String sex = gaugeGrowParam.getString("sex");
        if (StringUtil.stringIsNotNull(sex)) {
            queryDoc.put("sex", sex);
        }

        Double max = gaugeGrowParam.getDouble("max");
        Double min = gaugeGrowParam.getDouble("min");
        if (max != null && min != null) {
            queryDoc.put("monthofage", new Document("$gte", min).append("$lte", max));
        }


        return this.gaugeGrowMapper.findGaugeGrowByPeriodAndSex(queryDoc);
    }
}
