package com.hlife.server.scale.dao.mapping;

import com.hlife.framework.base.BaseMapper;
import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.server.scale.dao.PregnancyRiskScreeningMapper;
import com.hlife.server.scale.model.PregnancyRiskScreening;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * 孕期风险筛查持久层实现
 */
@Repository
public class PregnancyRiskScreeningMapperImpl extends BaseMapper implements PregnancyRiskScreeningMapper {
    @Override
    public PregnancyRiskScreening savePregnancyRiskScreening(PregnancyRiskScreening pregnancyRiskScreening) {
        return this.mongoTemplate.save(pregnancyRiskScreening);
    }

    @Override
    public PregnancyRiskScreening findOneByPrsId(String prsId) {
        return this.mongoTemplate.findOne(new Query(where("prsId").is(prsId)), PregnancyRiskScreening.class);
    }

    @Override
    public long deleteOne(String prsId) {
        return this.mongoTemplate.remove(new Query(where("prsId").is(prsId)), PregnancyRiskScreening.class).getDeletedCount();
    }

    @Override
    public PageResult<PregnancyRiskScreening> findPregnancyRiskScreeningHistoryPagination(Document queryDoc, PageParam pageParam) {
        return this.pageQuery(
                new BasicQuery(queryDoc),
                PregnancyRiskScreening.class,
                Optional.ofNullable(pageParam.getPageSize()).orElse(5),
                Optional.ofNullable(pageParam.getPageNum()).orElse(1),
                Sort.Order.desc("createTime")
        );
    }
}
