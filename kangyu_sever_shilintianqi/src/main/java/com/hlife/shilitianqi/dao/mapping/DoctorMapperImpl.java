package com.hlife.shilitianqi.dao.mapping;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.BaseMapper;
import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.shilitianqi.dao.DoctorMapper;
import com.hlife.shilitianqi.model.Doctor;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;


/**
 * 医生持久层实现
 */
@Repository
public class DoctorMapperImpl extends BaseMapper implements DoctorMapper {

    @Override
    public Doctor selectByPhone(String phone) {
        return this.mongoTemplate.findOne(new BasicQuery(new Document("phone", phone)), Doctor.class);
    }

    @Override
    public PageResult<Doctor> getDoctorList(Document document, PageParam pageParam) {
        BasicQuery query = (BasicQuery)
                new BasicQuery(document)
                        .addCriteria(Criteria.where("phone").ne("admin"));
        return this.pageQuery(query, Doctor.class, pageParam.getPageSize(), pageParam.getPageNum());
    }

    @Override
    public Doctor selectDoctorById(String id) {
        return this.mongoTemplate.findOne(new BasicQuery(new Document("id", id)), Doctor.class);
    }

    @Override
    public long deleteDoctorById(String id) {
        return this.mongoTemplate.remove(new BasicQuery(new Document("id", id)), Doctor.class)
                .getDeletedCount();
    }

    @Override
    public Doctor saveDoctor(Doctor doctor) {
        return this.mongoTemplate.save(doctor);
    }

    @Override
    public boolean isExists(Document queryDoc) {
        return this.mongoTemplate.exists(new BasicQuery(queryDoc), Doctor.class);
    }
}
