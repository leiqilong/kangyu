package com.hlife.server.scenes.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.util.StringUtil;
import com.hlife.server.scenes.dao.MatchCustomFormAndTagMapper;
import com.hlife.server.scenes.model.MatchCustomFormAndTag;
import com.hlife.server.scenes.service.MatchCustomFormAndTagService;
import org.bson.Document;
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
                .map(MatchCustomFormAndTag::getCustomFormId)
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
                .map(MatchCustomFormAndTag::getCustomFormId)
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
    public List<String> getTagListByFormId(String formId) {
        List<String> tagIdList = this.matchCustomFormAndTagMapper.getTagListByFormId(formId)
                .stream()
                .map(MatchCustomFormAndTag::getTagId)
                .collect(Collectors.toList());
        return tagIdList;
    }

    @Override
    public Long deleteMatchCustomFormAndTag(JSONObject jsonObject) {
        Document queryDoc = new Document();
        String customFormId = jsonObject.getString("formId");
        if (StringUtil.stringIsNotNull(customFormId)) {
            queryDoc.append("customFormId", customFormId);
        }

        String tagId = jsonObject.getString("tagId");
        if (StringUtil.stringIsNotNull(tagId)) {
            queryDoc.append("tagId", tagId);
        }

        @SuppressWarnings("unchecked")
        List<String> formIdList = (List<String>) jsonObject.get("formIdList");

        if (formIdList != null && formIdList.size() > 0) {
            queryDoc.append("customFormId", new Document("$in", formIdList));
        }
        return this.matchCustomFormAndTagMapper.deleteMatchCustomFormAndTag(queryDoc);
    }

    @Override
    public String addMatchCustomFormAndTagList(JSONObject jsonObject) {
        String customFormId = jsonObject.getString("customFormId");

        @SuppressWarnings("unchecked")
        List<String> tagIdList = (List<String>) jsonObject.get("tagIdList");
        if (tagIdList == null || StringUtil.stringIsNull(customFormId)) {
            return "传入参数为空";
        }

        if (this.matchCustomFormAndTagMapper.isExists(new Document("customFormId", customFormId))) {
            this.deleteMatchCustomFormAndTagListByFormId(customFormId);
        }

        if (tagIdList.isEmpty()) {
            return "添加成功";
        }
        this.matchCustomFormAndTagMapper.saveMatchCustomFormAndTagBatch(
                tagIdList.stream()
                        .map(tagId -> new MatchCustomFormAndTag(customFormId, tagId))
                        .collect(Collectors.toList())
        );
        return "添加成功";
    }

    @Override
    public Long deleteMatchCustomFormAndTagListByFormId(String formId) {
        return this.matchCustomFormAndTagMapper.deleteMatchCustomFormAndTagByFormId(formId);
    }
}
