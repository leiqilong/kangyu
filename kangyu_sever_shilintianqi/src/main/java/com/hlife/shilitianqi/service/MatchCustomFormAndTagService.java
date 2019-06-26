package com.hlife.shilitianqi.service;

import com.hlife.shilitianqi.model.MatchCustomFormAndTag;

import java.util.List;

/**
 * 自定义表单 标签匹配 服务层接口
 */
public interface MatchCustomFormAndTagService {

    /**
     * 根据标签id 删除对应的自定义表单 标签匹配匹配数据
     *
     * @param tagId 标签id
     * @return 影响的行数
     */
    long deleteMatchCustomFormAndTagByTagId(String tagId);

    /**
     * 批量保存自定义表单 标签匹配数据
     *
     * @param correspondingForms 标签对应的自定义表单 list
     * @param tagId 标签 id
     */
    void saveMatchCustomFormAndTagBatch(List<String> correspondingForms, String tagId);

    /**
     * 根据 标签id 查询对应的 表单id
     * @param tagId 标签id
     * @return 对应的 表单id list
     */
    List<String> selectCustomFormIdsByTagId(String tagId);

    /**
     * 根据 标签id 查询对应的 相应表单
     * @param tagId  标签id
     * @return 相应表单 数据
     */
    List<MatchCustomFormAndTag> selectMatchCustomFormAndTagListByTagId(String tagId);

    /**
     * 根据 标签id list 查询对应的 表单id list
     * @param tagIdList 标签id list
     * @return 对应的 表单id list
     */
    List<String> selectCustomFormIdsByTagIdList(List<String> tagIdList);

    /**
     * 根据 标签 id list 查询对应的表单list
     *
     * @param tagIdList
     * @return
     */
    List<MatchCustomFormAndTag> selectCustomFormsByTagIdList(List<String> tagIdList);
}
