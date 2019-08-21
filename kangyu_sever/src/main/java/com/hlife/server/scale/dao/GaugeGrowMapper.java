package com.hlife.server.scale.dao;

import com.hlife.server.scale.model.GaugeGrow;
import org.bson.Document;

import java.util.List;

/**
 * 身高体重标准表持久层接口
 */
public interface GaugeGrowMapper {
    GaugeGrow findGaugeGrowByMonthOfAgeAndSex(double monthAge, String sex);

    List<GaugeGrow> findGaugeGrowByPeriodAndSex(Document queryDoc);
}
