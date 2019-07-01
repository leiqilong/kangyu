package com.hlife.shilitianqi.service.impl;

import com.hlife.framework.util.StringUtil;
import com.hlife.shilitianqi.dao.MatchCustomFormAndTagMapper;
import com.hlife.shilitianqi.model.MatchCustomFormAndTag;
import com.hlife.shilitianqi.service.MatchCustomFormAndTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义表单 标签匹配 服务层实现
 */
@Service
public class MatchCustomFormAndTagServiceImpl implements MatchCustomFormAndTagService {
    @Autowired
    private MatchCustomFormAndTagMapper matchCustomFormAndTagMapper;

    @Override
    public long deleteMatchCustomFormAndTagByTagId(String tagId) {
        return matchCustomFormAndTagMapper.deleteMatchCustomFormAndTagByTagId(tagId);
    }

    @Override
    public void saveMatchCustomFormAndTagBatch(List<String> correspondingForms, String tagId) {
        if (correspondingForms == null || correspondingForms.isEmpty() || StringUtil.stringIsNull(tagId)) {
            return;
        }

        this.matchCustomFormAndTagMapper.saveMatchCustomFormAndTagBatch(
                correspondingForms.stream()
                        .map(formId -> new MatchCustomFormAndTag(formId, tagId))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public List<String> selectCustomFormIdsByTagId(String tagId) {
        return this.selectMatchCustomFormAndTagListByTagId(tagId).stream()
                .map(matchCustomFormAndTag -> matchCustomFormAndTag.getCustomFormId())
                .collect(Collectors.toList());
    }

    @Override
    public List<MatchCustomFormAndTag> selectMatchCustomFormAndTagListByTagId(String tagId) {
        return this.matchCustomFormAndTagMapper.selectMatchCustomFormAndTagListByTagId(tagId);
    }

    @Override
    public List<String> selectCustomFormIdsByTagIdList(List<String> tagIdList) {
        if (tagIdList == null || tagIdList.isEmpty()) {
            return Collections.emptyList();
        }
        return this.selectCustomFormsByTagIdList(tagIdList)
                .stream()
                .map(tag -> tag.getCustomFormId())
                .collect(Collectors.toList());
    }


    @Override
    public List<MatchCustomFormAndTag> selectCustomFormsByTagIdList(List<String> tagIdList) {
        return this.matchCustomFormAndTagMapper.selectCustomFormsByTagIdList(tagIdList);
    }

    @Override
    public MatchCustomFormAndTag addMatchCustomFormAndTag(MatchCustomFormAndTag matchCustomFormAndTag) {
        return this.matchCustomFormAndTagMapper.addMatchCustomFormAndTag(matchCustomFormAndTag);
    }

    @Override
    public List<MatchCustomFormAndTag> getTagListByFormId(String formId) {
        return this.matchCustomFormAndTagMapper.getTagListByFormId(formId);
    }

    @Override
    public Long deleteMatchCustomFormAndTag(String formId, String tagId) {
        return this.matchCustomFormAndTagMapper.deleteMatchCustomFormAndTag(formId, tagId);
    }
}
