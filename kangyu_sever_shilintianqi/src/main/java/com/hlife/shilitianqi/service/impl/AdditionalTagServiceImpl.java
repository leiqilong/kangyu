package com.hlife.shilitianqi.service.impl;

import com.hlife.framework.util.StringUtil;
import com.hlife.shilitianqi.dao.AdditionalTagMapper;
import com.hlife.shilitianqi.model.AdditionalTag;
import com.hlife.shilitianqi.service.AdditionalTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 附加标签业务层实现
 */
@Service
public class AdditionalTagServiceImpl implements AdditionalTagService {

    @Autowired
    private AdditionalTagMapper additionalTagMapper;

    @Override
    public long deleteAdditionalTagByMainTagId(String mainTagId) {
        return additionalTagMapper.deleteAdditionalTagByMainTagId(mainTagId);
    }

    @Override
    public void saveAdditionalTagsBatch(List<String> additionalTags, String mainTagId) {
        if (additionalTags == null || additionalTags.isEmpty() || StringUtil.stringIsNull(mainTagId)) {
            return;
        }

        this.additionalTagMapper.saveAdditionalTagsBatch(
                additionalTags.stream()
                        .map(assistantTagId -> new AdditionalTag(mainTagId, assistantTagId))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public List<String> selectAssistantTagIdListByMainTagId(String mainTagId) {
        return this.selectAdditionalTagListByMainTagId(mainTagId).stream()
                .filter(additionalTag -> StringUtil.stringIsNotNull(additionalTag.getAssistantTagId()))
                .map(additionalTag -> additionalTag.getAssistantTagId())
                .collect(Collectors.toList());
    }

    @Override
    public List<AdditionalTag> selectAdditionalTagListByMainTagId(String mainTagId) {
        return this.additionalTagMapper.selectAdditionalTagListByMainTagId(mainTagId);
    }
}
