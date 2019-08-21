package com.hlife.server.core.dao;

import com.hlife.server.core.model.Record;
import org.bson.Document;

/**
 * 档案持久层接口
 */
public interface RecordMapper {
    Record findOne(Document queryDoc);
}
