package com.hlife.server.scenes.dao.impl;

import com.hlife.server.scenes.dao.DataCollectionOutMapper;
import com.hlife.server.scenes.model.DataCollectionOut;
import com.mongodb.client.result.DeleteResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * 数据采集
 */
@Slf4j
@Repository
public class DataCollectionOutMapperImpl implements DataCollectionOutMapper {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public <T> DataCollectionOut<T> saveDataCollectionOut(DataCollectionOut<T> dataCollectionOut) {
        return mongoTemplate.save(dataCollectionOut);
    }

    @Override
    public <T> DataCollectionOut findOneDataCollectionOut(Criteria criteria) {
        return mongoTemplate.findOne(new Query(criteria), DataCollectionOut.class);
    }

    @Override
    public long countDataCollectionOut(Criteria criteria) {
        return mongoTemplate.count(new Query(criteria), DataCollectionOut.class);
    }

    @Override
    public <T> void updateDataCollectionOut(DataCollectionOut<T> dataCollectionOut) {}

    @Override
    public long deleteById(String id) {
        DeleteResult deleteResult = mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), DataCollectionOut.class);
        log.debug("deleteResult ==> {}", deleteResult);
        return deleteResult.getDeletedCount();
    }
}

