package com.hlife.server.scenes.dao.mapping;

import com.hlife.framework.base.BaseMapper;
import com.hlife.server.scenes.dao.TagTypeMapper;
import com.hlife.server.scenes.model.TagType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 标签类别持久层实现
 */
@Repository
public class TagTypeMapperImpl extends BaseMapper implements TagTypeMapper {
    @Override
    public List<TagType> selectTagTypeListAll() {
        return this.mongoTemplate.findAll(TagType.class);
    }
}
