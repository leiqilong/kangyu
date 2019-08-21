package com.hlife.server.scale.service;

import com.alibaba.fastjson.JSONObject;
import com.hlife.server.scale.model.GaugeGrow;

import java.util.List;

/**
 * 身高体重标准数据服务层接口
 */
public interface GaugeGrowService {

    GaugeGrow findGaugeGrowByMonthOfAgeAndSex(double monthAge, String sex);

    List<GaugeGrow> findGaugeGrowByPeriodAndSex(JSONObject gaugeGrowParam);
}
