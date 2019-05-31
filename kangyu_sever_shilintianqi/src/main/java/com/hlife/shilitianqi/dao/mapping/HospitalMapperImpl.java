package com.hlife.shilitianqi.dao.mapping;

import com.hlife.framework.base.BaseMapper;
import com.hlife.shilitianqi.dao.HospitalMapper;
import com.hlife.shilitianqi.model.Hospital;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

/**
 * 医院持久层实现
 */
@Repository
public class HospitalMapperImpl extends BaseMapper implements HospitalMapper {

    @Override
    public Hospital getHospitalById(String id) {
        return this.mongoTemplate.findOne(new BasicQuery(new Document("id", id)), Hospital.class);
    }
}
