package com.hlife.server.scale.dao.mapping;

import com.hlife.framework.base.BaseMapper;
import com.hlife.server.scale.dao.GaugeGrowMapper;
import com.hlife.server.scale.model.GaugeGrow;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * 身高体重标准表持久层实现
 */
@Repository
public class GaugeGrowMapperImpl extends BaseMapper implements GaugeGrowMapper {

    @Override
    public GaugeGrow findGaugeGrowByMonthOfAgeAndSex(double monthAge, String sex) {
        return this.mongoTemplate.findOne(new Query(where("monthofage").is(monthAge).and("sex").is(sex)), GaugeGrow.class);
    }

    @Override
    public List<GaugeGrow> findGaugeGrowByPeriodAndSex(Document queryDoc) {
        return this.mongoTemplate.find(new BasicQuery(queryDoc).with(Sort.by("monthofage")), GaugeGrow.class);
    }
}
