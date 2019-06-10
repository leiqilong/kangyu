package com.hlife.shilitianqi.dao;

import com.hlife.shilitianqi.model.MatchCustomFormAndTag;

import java.util.List;

/**
 * 自定义表单 标签匹配 持久层接口
 */
public interface MatchCustomFormAndTagMapper {

    /**
     * 根据标签id删除相应的 自定义表单 标签匹配 数据
     *
     * @param tagId 标签id
     * @return 影响的条数
     */
    long deleteMatchCustomFormAndTagByTagId(String tagId);

    /**
     * 批量保存 自定义表单 标签匹配 数据
     * @param matchCustomFormAndTags 自定义表单 标签匹配 数据list
     */
    void saveMatchCustomFormAndTagBatch(List<MatchCustomFormAndTag> matchCustomFormAndTags);

    /**
     * 根据 标签id 查询匹配表单信息
     * @param tagId
     * @return
     */
    List<MatchCustomFormAndTag> selectMatchCustomFormAndTagListByTagId(String tagId);
}
