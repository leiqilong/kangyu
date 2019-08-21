package com.hlife.server.scale.service;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageResult;
import com.hlife.server.scale.model.HeightAndWeight;

import java.util.List;

public interface HeightAndWeightService {
    HeightAndWeight getHeightAdnWeightTestResult(HeightAndWeight heightAndWeight);

    HeightAndWeight saveOrEditHeightAndWeight(HeightAndWeight heightAndWeight);

    PageResult<HeightAndWeight> findHeightAndWeightHistoryPagination(JSONObject jsonObject);

    JSONObject findHeightAndWeightGraphData(String guid);

    List<HeightAndWeight> findHeightAndWeightList(JSONObject jsonObject);

    HeightAndWeight wpaSave(HeightAndWeight heightAndWeight);
}
