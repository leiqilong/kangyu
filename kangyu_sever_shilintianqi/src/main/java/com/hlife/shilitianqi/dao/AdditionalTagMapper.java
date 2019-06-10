package com.hlife.shilitianqi.dao;

import com.hlife.shilitianqi.model.AdditionalTag;

import java.util.List;

/**
 * 附加标签持久层接口
 */
public interface AdditionalTagMapper {

    /**
     * 根据主标签id 删除 附加标签数据
     *
     * @param mainTagId 主标签id
     * @return 影响的行数
     */
    long deleteAdditionalTagByMainTagId(String mainTagId);

    /**
     * 批量保存附加标签信息
     *
     * @param additionalTagList 附加标签信息
     */
    void saveAdditionalTagsBatch(List<AdditionalTag> additionalTagList);

    /**
     * 根据 主 标签id 查询对应的 附加标签
     *
     * @param mainTagId 主tagId
     * @return
     */
    List<AdditionalTag> selectAdditionalTagListByMainTagId(String mainTagId);
}
