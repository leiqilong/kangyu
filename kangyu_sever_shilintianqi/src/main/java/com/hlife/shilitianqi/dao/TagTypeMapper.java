package com.hlife.shilitianqi.dao;

import com.hlife.shilitianqi.model.TagType;

import java.util.List;

/**
 * 标签类别持久层接口
 */
public interface TagTypeMapper {

    List<TagType> selectTagTypeListAll();
}
