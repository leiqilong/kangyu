package com.hlife.shilitianqi.service.impl;

import com.hlife.framework.util.StringUtil;
import com.hlife.shilitianqi.dao.MatchCustomFormAndTagMapper;
import com.hlife.shilitianqi.model.MatchCustomFormAndTag;
import com.hlife.shilitianqi.service.MatchCustomFormAndTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
