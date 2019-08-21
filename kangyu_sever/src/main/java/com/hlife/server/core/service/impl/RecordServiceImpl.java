package com.hlife.server.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.util.StringUtil;
import com.hlife.server.core.dao.RecordMapper;
import com.hlife.server.core.model.Record;
import com.hlife.server.core.service.RecordService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordMapper recordMapper;


    @Override
    public Record findOne(String guid) {
        return this.findOne(new JSONObject().fluentPut("guid", guid));
    }

    @Override
    public Record findOne(JSONObject jsonObject) {
        Document queryDoc = new Document();
        String guid = jsonObject.getString("guid");
        if (StringUtil.stringIsNotNull(guid)) {
            queryDoc.put("guid", guid);
        }
        return this.recordMapper.findOne(queryDoc).putAgeDetail();
    }
}
