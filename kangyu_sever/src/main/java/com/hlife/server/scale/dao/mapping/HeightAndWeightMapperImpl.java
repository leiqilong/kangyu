package com.hlife.server.scale.dao.mapping;

import com.hlife.framework.base.BaseMapper;
import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.server.scale.dao.HeightAndWeightMapper;
import com.hlife.server.scale.model.HeightAndWeight;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class HeightAndWeightMapperImpl extends BaseMapper implements HeightAndWeightMapper {
    @Override
    public HeightAndWeight findLastHeightAndWeight(String guid, Integer monthAgeInt) {
        Criteria criteria = where("guid").is(guid);
        if (monthAgeInt != null) {
            criteria.and("monthAgeInt").lt(monthAgeInt);
        }

        List<HeightAndWeight> heightAndWeightList = this.mongoTemplate
                .find(new Query(criteria).with(Sort.by(Sort.Order.desc("monthAgeInt"))), HeightAndWeight.class);

        if (heightAndWeightList == null || heightAndWeightList.isEmpty()) {
            return null;
        }

        return heightAndWeightList.get(0);
    }

    @Override
    public HeightAndWeight findOne(Document queryDoc) {
        return this.mongoTemplate.findOne(new BasicQuery(queryDoc), HeightAndWeight.class);
    }

    @Override
    public long remove(Document queryDoc) {
        return this.mongoTemplate.remove(new BasicQuery(queryDoc), HeightAndWeight.class).getDeletedCount();
    }

    @Override
    public HeightAndWeight save(HeightAndWeight heightAndWeight) {
        return this.mongoTemplate.save(heightAndWeight);
    }

    @Override
    public PageResult<HeightAndWeight> findHeightAndWeightHistoryPagination(Document queryDoc, PageParam pageParam) {
        return this.pageQuery(
                new BasicQuery(queryDoc),
                HeightAndWeight.class,
                Optional.ofNullable(pageParam.getPageSize()).orElse(5),
                Optional.ofNullable(pageParam.getPageNum()).orElse(1),
                Sort.Order.desc("createTime")
        );
    }

    @Override
    public List<HeightAndWeight> findHeightAndWeightList(Document queryDoc) {
        return this.mongoTemplate.find(new BasicQuery(queryDoc), HeightAndWeight.class);
    }

    @Override
    public boolean isExists(Document queryDoc) {
        return this.mongoTemplate.exists(new BasicQuery(queryDoc), HeightAndWeight.class);
    }
}
