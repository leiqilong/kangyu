package com.hlife.server.core.dao.mapping;

import com.hlife.framework.base.BaseMapper;
import com.hlife.server.core.dao.ChildOtherArchivesInfoMapper;
import com.hlife.server.core.model.ChildOtherArchivesInfo;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class ChildOtherArchivesInfoMapperImpl extends BaseMapper implements ChildOtherArchivesInfoMapper {

    @Override
    public ChildOtherArchivesInfo findOne(String guid) {
        return this.mongoTemplate.findOne(new Query(where("userId").is(guid)), ChildOtherArchivesInfo.class);
    }
}
