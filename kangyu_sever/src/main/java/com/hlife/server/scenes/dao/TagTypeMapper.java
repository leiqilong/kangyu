package com.hlife.server.scenes.dao;

import com.hlife.server.scenes.model.TagType;

import java.util.List;

/**
 * 标签类别持久层接口
 */
public interface TagTypeMapper {

    List<TagType> selectTagTypeListAll();
}
