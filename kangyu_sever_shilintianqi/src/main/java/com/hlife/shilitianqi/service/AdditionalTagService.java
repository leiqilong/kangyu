package com.hlife.shilitianqi.service;

import com.hlife.shilitianqi.model.AdditionalTag;

import java.util.List;

/**
 * 附加标签业务层接口
 */
public interface AdditionalTagService {

    /**
     * 根据主标签id 删除 附加标签数据
     *
     * @param mainTagId 主标签id
     * @return 影响的行数
     */
    long deleteAdditionalTagByMainTagId(String mainTagId);

    /**
     * 批量保存附加标签数据
     *
     * @param additionalTags 附加标签信息list
     * @param mainTagId 主标签
     */
    void saveAdditionalTagsBatch(List<String> additionalTags, String mainTagId);

    /**
     * 根据主标签id 查询相应的附加标签id
     *
     * @param mainTagId 主标签id
     * @return 附加标签id list
     */
    List<String> selectAssistantTagIdListByMainTagId(String mainTagId);

    /**
     * 根据主 标签id 查询相应的附加标签信息
     *
     * @param mainTagId 主标签id
     * @return 附加标签 list
     */
    List<AdditionalTag> selectAdditionalTagListByMainTagId(String mainTagId);
}
