package com.hlife.server.core.dao.mapping;

import com.hlife.framework.base.BaseMapper;
import com.hlife.server.core.dao.RecordMapper;
import com.hlife.server.core.model.Record;
import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class RecordMapperImpl extends BaseMapper implements RecordMapper {

    @Override
    public Record findOne(Document queryDoc) {
        return this.mongoTemplate.findOne(new BasicQuery(queryDoc), Record.class);
    }

}
