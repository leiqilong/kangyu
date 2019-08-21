package com.hlife.server.scenes.dao;

import com.hlife.server.scenes.model.MatchCustomFormAndTag;
import org.bson.Document;

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
     *
     * @param matchCustomFormAndTags 自定义表单 标签匹配 数据list
     */
    void saveMatchCustomFormAndTagBatch(List<MatchCustomFormAndTag> matchCustomFormAndTags);

    /**
     * 根据 标签id 查询匹配表单信息
     *
     * @param tagId
     * @return
     */
    List<MatchCustomFormAndTag> selectMatchCustomFormAndTagListByTagId(String tagId);

    /**
     * 根据 标签 id list 查询对应的表单list
     *
     * @param tagIdList 标签 id list
     * @return 标签表单 关联 list
     */
    List<MatchCustomFormAndTag> selectCustomFormsByTagIdList(List<String> tagIdList);

    /**
     * 给表单添加一个标签
     *
     * @param matchCustomFormAndTag 标签 表单 关联数据
     * @return 标签表单 关联  数据
     */
    MatchCustomFormAndTag addMatchCustomFormAndTag(MatchCustomFormAndTag matchCustomFormAndTag);

    /**
     * 根据表单id 查询对应的 标签
     *
     * @param formId 表单id
     * @return 标签 表单 关联 list
     */
    List<MatchCustomFormAndTag> getTagListByFormId(String formId);

    /**
     * 删除某个表单的某个标签
     *
     * @param queryDoc customFormId 表单id
     *                 tagId  标签 id
     * @return 删除的条数
     */
    Long deleteMatchCustomFormAndTag(Document queryDoc);

    /**
     * 判断 数据是否存在
     *
     * @param queryDoc 查询条件
     *                 customFormId 表单id <br/>
     *                 tagId 标签id
     * @return 数据是否存在
     */
    boolean isExists(Document queryDoc);

    /**
     * 根据表单id 删除对应的 标签
     *
     * @param formId 表单id
     * @return 影响的条数
     */
    Long deleteMatchCustomFormAndTagByFormId(String formId);
}
