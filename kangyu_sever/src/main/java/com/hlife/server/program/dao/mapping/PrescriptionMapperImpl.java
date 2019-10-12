package com.hlife.server.program.dao.mapping;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.BaseMapper;
import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.server.program.dao.PrescriptionMapper;
import com.hlife.server.program.model.Prescription;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * 文件上传下载持久层实现
 */
@Repository
public class PrescriptionMapperImpl extends BaseMapper implements PrescriptionMapper {

    @Override
    public PageResult<Prescription> findPrescriptionPageResult(Document queryDoc, PageParam pageParam) {
        return this.pageQuery(
                new BasicQuery(queryDoc),
                Prescription.class,
                Optional.ofNullable(pageParam.getPageSize()).orElse(5),
                Optional.ofNullable(pageParam.getPageNum()).orElse(1),
                Sort.Order.desc("createTime")
        );
    }

    @Override
    public Prescription save(Prescription prescription) {
        return this.mongoTemplate.save(prescription);
    }

    @Override
    public List<Prescription> findPrescriptionList(Document queryDoc) {
        return this.mongoTemplate.find(new BasicQuery(queryDoc), Prescription.class);
    }

    @Override
    public List<Prescription> findPrescriptionArray(JSONObject jsonObject) {
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.lookup("match_customform_and_tag", "", "", ""));

        //this.mongoTemplate.aggregate()
        return null;
    }

    @Override
    public boolean isExist(String id) {
        return this.mongoTemplate.exists(new Query(where("id").is(id)), Prescription.class);
    }

    @Override
    public long delete(String id) {
        return this.mongoTemplate.remove(new Query(where("id").is(id)), Prescription.class).getDeletedCount();
    }

    @Override
    public Prescription findOne(String id) {
        return this.mongoTemplate.findOne(new Query(where("id").is(id)), Prescription.class);
    }
}
