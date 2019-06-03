package com.hlife.shilitianqi.dao.mapping;

import com.hlife.framework.base.BaseMapper;
import com.hlife.shilitianqi.dao.MatchCustomFormAndTagMapper;
import com.hlife.shilitianqi.model.MatchCustomFormAndTag;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

@Repository
public class MatchCustomFormAndTagMapperImpl extends BaseMapper implements MatchCustomFormAndTagMapper {
    @Override
    public long deleteMatchCustomFormAndTagByTagId(String tagId) {
        return this.mongoTemplate
                .remove(new BasicQuery(new Document("tagId", tagId)),
                        MatchCustomFormAndTag.class
                ).getDeletedCount();
    }
}
