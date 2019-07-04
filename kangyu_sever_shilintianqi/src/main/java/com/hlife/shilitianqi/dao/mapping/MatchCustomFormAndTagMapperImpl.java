package com.hlife.shilitianqi.dao.mapping;

import com.hlife.framework.base.BaseMapper;
import com.hlife.shilitianqi.dao.MatchCustomFormAndTagMapper;
import com.hlife.shilitianqi.model.MatchCustomFormAndTag;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 自定义表单 标签匹配 持久层实现
 */
@Repository
public class MatchCustomFormAndTagMapperImpl extends BaseMapper implements MatchCustomFormAndTagMapper {

    @Override
    public long deleteMatchCustomFormAndTagByTagId(String tagId) {
        return this.mongoTemplate
                .remove(new BasicQuery(new Document("tagId", tagId)),
                        MatchCustomFormAndTag.class
                ).getDeletedCount();
    }

    @Override
    public void saveMatchCustomFormAndTagBatch(List<MatchCustomFormAndTag> matchCustomFormAndTags) {
        this.saveBatch(matchCustomFormAndTags, MatchCustomFormAndTag.class);
    }

    @Override
    public List<MatchCustomFormAndTag> selectMatchCustomFormAndTagListByTagId(String tagId) {
        return this.mongoTemplate.find(new BasicQuery(new Document("tagId", tagId)), MatchCustomFormAndTag.class);
    }

    @Override
    public List<MatchCustomFormAndTag> selectCustomFormsByTagIdList(List<String> tagIdList) {
        return this.mongoTemplate.find(new BasicQuery(new Document("tagId", new Document("$in", tagIdList))), MatchCustomFormAndTag.class);
    }

    @Override
    public MatchCustomFormAndTag addMatchCustomFormAndTag(MatchCustomFormAndTag matchCustomFormAndTag) {
        return this.mongoTemplate.save(matchCustomFormAndTag);
    }

    @Override
    public List<MatchCustomFormAndTag> getTagListByFormId(String formId) {
        return this.mongoTemplate.find(new BasicQuery(new Document("customFormId", formId)), MatchCustomFormAndTag.class);
    }

    @Override
    public Long deleteMatchCustomFormAndTag(Document queryDoc) {
        //Document document = new Document("customFormId", formId).append("tagId", tagId);
        return this.mongoTemplate.remove(new BasicQuery(queryDoc), MatchCustomFormAndTag.class).getDeletedCount();
    }

    @Override
    public boolean isExists(Document queryDoc) {
        return this.mongoTemplate.exists(new BasicQuery(queryDoc), MatchCustomFormAndTag.class);
    }

    @Override
    public Long deleteMatchCustomFormAndTagByFormId(String formId) {
        return this.mongoTemplate.remove(new BasicQuery(new Document("customFormId", formId)), MatchCustomFormAndTag.class).getDeletedCount();
    }
}
