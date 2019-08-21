package com.hlife.server.core.service;

import com.alibaba.fastjson.JSONObject;
import com.hlife.server.core.model.Record;

/**
 * 档案服务层接口
 */
public interface RecordService {
    Record findOne(String guid);

    Record findOne(JSONObject jsonObject);

}
