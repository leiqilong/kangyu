package com.hlife.shilitianqi.dao.mapping;

import com.hlife.framework.base.BaseMapper;
import com.hlife.shilitianqi.dao.AdditionalTagMapper;
import com.hlife.shilitianqi.model.AdditionalTag;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 附加标签持久层实现
 */
@Repository
public class AdditionalTagMapperImpl extends BaseMapper implements AdditionalTagMapper {

    @Override
    public long deleteAdditionalTagByMainTagId(String mainTagId) {
        return this.mongoTemplate.remove(
                new BasicQuery(new Document("mainTagId", mainTagId)),
                AdditionalTag.class
        ).getDeletedCount();
    }

    @Override
    public void saveAdditionalTagsBatch(List<AdditionalTag> additionalTagList) {
        this.saveBatch(additionalTagList, AdditionalTag.class);
    }

    @Override
    public List<AdditionalTag> selectAdditionalTagListByMainTagId(String mainTagId) {
        return this.mongoTemplate.find(new BasicQuery(new Document("mainTagId", mainTagId)), AdditionalTag.class);
    }
}
