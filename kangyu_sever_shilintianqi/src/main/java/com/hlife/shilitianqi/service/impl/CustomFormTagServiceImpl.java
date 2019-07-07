package com.hlife.shilitianqi.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.framework.util.GuidUtil;
import com.hlife.framework.util.StringUtil;
import com.hlife.shilitianqi.constant.Constant;
import com.hlife.shilitianqi.dao.CustomFormTagMapper;
import com.hlife.shilitianqi.model.CustomFormTag;
import com.hlife.shilitianqi.model.MatchCustomFormAndTag;
import com.hlife.shilitianqi.service.AdditionalTagService;
import com.hlife.shilitianqi.service.CustomFormTagService;
import com.hlife.shilitianqi.service.MatchCustomFormAndTagService;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 自定义表单标签服务层实现
 */
@Slf4j
@Service
public class CustomFormTagServiceImpl implements CustomFormTagService {

    @Autowired
    private CustomFormTagMapper customFormTagMapper;
    @Autowired
    private MatchCustomFormAndTagService matchCustomFormAndTagService;
    @Autowired
    private AdditionalTagService additionalTagService;

    @Override
    public CustomFormTag addOrEditCustomFormTag(CustomFormTag customFormTag) {
        checkTagNameAndTagCode(customFormTag);

        if (StringUtil.stringIsNotNull(customFormTag.getId())) {
            String id = customFormTag.getId();
            CustomFormTag record = this.customFormTagMapper.selectCustomFormTagById(id);
            this.deleteCustomFormTagById(id);
            customFormTag.setCreateTime(record.getCreateTime());
        } else {
            customFormTag.setId(GuidUtil.generateGuid())
                    .setCreateTime(new Date());
        }

        // 自定义表单匹配信息
        List<String> correspondingForms = customFormTag.getCorrespondingForms();
        // 附加标签信息
        List<String> additionalTags = customFormTag.getAdditionalTags();

        CustomFormTag newCustomFormTag =
                this.customFormTagMapper.addCustomFormTag(customFormTag)
                        .setCorrespondingForms(correspondingForms)
                        .setAdditionalTags(additionalTags);

        // 保存标签自定义表单匹配信息
        this.matchCustomFormAndTagService.saveMatchCustomFormAndTagBatch(correspondingForms, customFormTag.getId());
        // 保存附加标签信息
        this.additionalTagService.saveAdditionalTagsBatch(additionalTags, customFormTag.getId());

        return newCustomFormTag;
    }

    @Override
    public CustomFormTag addOrEditCustomFormTagSelf(CustomFormTag customFormTag) {
        if (StringUtil.stringIsNotNull(customFormTag.getId())) {
            this.customFormTagMapper.deleteCustomFormTagById(customFormTag.getId());
        } else {
            customFormTag.setId(GuidUtil.generateGuid());
        }
        return this.customFormTagMapper.addCustomFormTag(customFormTag);
    }

    @Override
    public PageResult<CustomFormTag> getCustomFormTagPageResult(JSONObject jsonObject) {
        Document queryDoc = new Document();

        String tagName = jsonObject.getString("tagName");
        if (StringUtil.stringIsNotNull(tagName)) {
            queryDoc.append("tagName", Pattern.compile("^.*" + tagName + ".*$", Pattern.CASE_INSENSITIVE));
        }

        String tagType = jsonObject.getString("tagType");
        if (StringUtil.stringIsNotNull(tagType)) {
            queryDoc.append("tagType", tagType);
        }

        PageParam pageParam = new PageParam(jsonObject.getInteger(PageParam.PAGE_SIZE),
                jsonObject.getInteger(PageParam.PAGE_NUM));
        PageResult<CustomFormTag> pageResult = customFormTagMapper.getCustomFormTagPageResult(queryDoc, pageParam);

        for (CustomFormTag customFormTag : pageResult.getList()) {
            customFormTag
                    .setCorrespondingForms(
                            this.matchCustomFormAndTagService.selectCustomFormIdsByTagId(customFormTag.getId())
                    )
                    .setAdditionalTags(
                            this.additionalTagService.selectAssistantTagIdListByMainTagId(customFormTag.getId())
                    );
        }
        return pageResult;
    }

    @Override
    public List<CustomFormTag> getCustomFormTagList(JSONObject jsonObject) {
        Document queryDoc = new Document();
        String tagType = jsonObject.getString("tagType");
        if (StringUtil.stringIsNotNull(tagType)) {
            queryDoc.append("tagType", tagType);
        }

        JSONArray tagTypeList = jsonObject.getJSONArray("tagTypeList");
        if (tagTypeList != null && tagTypeList.size() > 0) {
            queryDoc.append("tagType", new Document("$in", tagTypeList));
        }
        return customFormTagMapper.getCustomFormTagList(queryDoc);
    }

    @Override
    public List<Map<String, Object>> getCustomFormTagTree(JSONObject jsonObject) {
        List<CustomFormTag> tagList = this.getCustomFormTagList(jsonObject);

        List<String> tagNames = new ArrayList<>();
        for (CustomFormTag customFormTag : tagList) {
            String tagName = customFormTag.getTagName();
            if (!tagNames.contains(customFormTag.getTagName())) {
                tagNames.add(tagName);
            }
        }
        List<Map<String, Object>> tagTree = new ArrayList<>();
        for (String tagName : tagNames) {
            List<CustomFormTag> customFormTagGroup = new ArrayList<>();
            for (CustomFormTag customFormTag : tagList) {
                if (customFormTag.getTagName().equals(tagName)) {
                    customFormTagGroup.add(customFormTag);
                }
            }
            Map<String, Object> groupMap = new HashMap<>();
            groupMap.put("tagValue", tagName);
            groupMap.put("id", "");
            groupMap.put("children", customFormTagGroup);
            tagTree.add(groupMap);
        }

        return tagTree;
    }

    @Override
    public String deleteCustomFormTagById(String id) {
        if (StringUtil.stringIsNull(id)) {
            throw new IllegalArgumentException("传入标签id为空");
        }

        Document queryDoc = new Document("id", id);
        if (!this.customFormTagMapper.isExists(queryDoc)) {
            throw new RuntimeException("该数据不存在");
        }

        // 删除相关表单信息
        this.matchCustomFormAndTagService.deleteMatchCustomFormAndTagByTagId(id);

        // 删除附加标签信息
        this.additionalTagService.deleteAdditionalTagByMainTagId(id);

        // 删除主信息
        if (this.customFormTagMapper.deleteCustomFormTagById(id) < 1) {
            throw new RuntimeException("操作失败");
        }
        return "操作成功";
    }

    @Override
    public List<JSONObject> getCorrespondingFromList() {
        List<JSONObject> jsonObjectList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(Constant.GROUP_LABEL, "量表");
        jsonObject.put(Constant.GROUP_OPTIONS, Constant.Scale.getScaleList());

        jsonObjectList.add(jsonObject);
        return jsonObjectList;
    }

    @Override
    public List<String> selectCustomFormIdsByTagIdList(List<String> tagIdList, String type) {
        List<String> formIdList = new ArrayList<>();
        List<MatchCustomFormAndTag> matchList = this.matchCustomFormAndTagService.selectCustomFormsByTagIdList(tagIdList);
        for (MatchCustomFormAndTag match : matchList) {
            if (!formIdList.contains(match.getCustomFormId())) {
                formIdList.add(match.getCustomFormId());
            }
        }

        Map<String, List<String>> formMap = new HashMap<>();

        for (String formId : formIdList) {
            List<String> tagGroup = new ArrayList<>();
            for (MatchCustomFormAndTag match : matchList) {
                if (match.getCustomFormId().equals(formId)) {
                    tagGroup.add(match.getTagId());
                }
            }
            formMap.put(formId, tagGroup);
        }

        return this.findFormList(formMap, tagIdList, type);
    }

    @Override
    public MatchCustomFormAndTag addMatchCustomFormAndTag(MatchCustomFormAndTag matchCustomFormAndTag) {
        return this.matchCustomFormAndTagService.addMatchCustomFormAndTag(matchCustomFormAndTag);
    }

    @Override
    public List<String> getTagListByFormId(String formId) {
        return this.matchCustomFormAndTagService.getTagListByFormId(formId);
    }

    @Override
    public Long deleteMatchCustomFormAndTag(JSONObject jsonObject) {
        return this.matchCustomFormAndTagService.deleteMatchCustomFormAndTag(jsonObject);
    }

    @Override
    public CustomFormTag selectCustomFormTagById(String tagId) {
        return this.customFormTagMapper.selectCustomFormTagById(tagId);
    }

    @Override
    public String addMatchCustomFormAndTagList(JSONObject jsonObject) {
        return this.matchCustomFormAndTagService.addMatchCustomFormAndTagList(jsonObject);
    }

    /**
     * 根据标签 idlist 过滤 表单
     *
     * @param formMap   表单列表和其相关标签
     *                  kqy  表单id
     *                  value 表单上的标签
     * @param tagIdList 过滤用标签 list
     * @return 过滤后的表单
     */
    private List<String> findFormList(Map<String, List<String>> formMap, List<String> tagIdList, String type) {
        if (tagIdList == null || tagIdList.size() < 2) {
            return Collections.emptyList();
        }

        List<String> formIdList = formMap.entrySet().stream()
                .filter(entry -> this.equalsList(entry.getValue(), tagIdList)
                        && entry.getKey().split(";")[1].equals(type))
                .map(entry -> entry.getKey().split(";")[0])
                .collect(Collectors.toList());

        if (formIdList == null || formIdList.isEmpty()) {
            tagIdList.remove(tagIdList.size() - 1);
            return this.findFormList(formMap, tagIdList, type);
        }

        return formIdList;
    }

    private boolean equalsList(List<String> value, List<String> tagIdList) {
        for (String tagId : tagIdList) {
            if (!value.contains(tagId)) {
                return false;
            }
        }
        return true;
    }

    private void checkTagNameAndTagCode(CustomFormTag customFormTag) {
        Document queryDoc = new Document("tagCode", customFormTag.getTagCode());

        if (StringUtil.stringIsNotNull(customFormTag.getId())) {
            queryDoc.append("id", new Document("$ne", customFormTag.getId()));
        }

        if (this.customFormTagMapper.isExists(queryDoc)) {
            throw new RuntimeException("标签代码重复");
        }

        queryDoc.remove("tagCode");
        queryDoc.append("tagName", customFormTag.getTagName());
        queryDoc.append("tagValue", customFormTag.getTagValue());

        if (this.customFormTagMapper.isExists(queryDoc)) {
            throw new RuntimeException("标签名称+值重复");
        }
    }
}
