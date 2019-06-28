package com.hlife.shilitianqi.dao.mapping;

import com.hlife.framework.base.BaseMapper;
import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.shilitianqi.dao.CustomFormTagMapper;
import com.hlife.shilitianqi.model.CustomFormTag;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 自定义表单标签持久层实现
 */
@Repository
public class CustomFormTagMapperImpl extends BaseMapper implements CustomFormTagMapper {

    @Override
    public CustomFormTag addCustomFormTag(CustomFormTag customFormTag) {
        return this.mongoTemplate.save(customFormTag);
    }

    @Override
    public PageResult<CustomFormTag> getCustomFormTagPageResult(Document queryDoc, PageParam pageParam) {
        return this.pageQuery(new BasicQuery(queryDoc), CustomFormTag.class,
                pageParam.getPageSize(), pageParam.getPageNum());
    }

    @Override
    public List<CustomFormTag> getCustomFormTagList(Document queryDoc) {
        return this.mongoTemplate.find(
                new BasicQuery(queryDoc)
                        .with(new Sort(new Sort.Order(Sort.Direction.DESC, "tagName"))),
                CustomFormTag.class
        );
    }

    @Override
    public boolean isExists(Document queryDoc) {
        return this.mongoTemplate.exists(new BasicQuery(queryDoc), CustomFormTag.class);
    }

    @Override
    public long deleteCustomFormTagById(String id) {
        return this.mongoTemplate.remove(new BasicQuery(new Document("id", id)), CustomFormTag.class)
                .getDeletedCount();
    }

    @Override
    public CustomFormTag selectCustomFormTagById(String id) {
        return this.mongoTemplate.findOne(new BasicQuery(new Document("id", id)), CustomFormTag.class);
    }
}
