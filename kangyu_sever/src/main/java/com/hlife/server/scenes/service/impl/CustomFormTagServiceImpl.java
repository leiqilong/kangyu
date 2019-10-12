package com.hlife.server.scenes.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.framework.config.BusinessConfig;
import com.hlife.framework.util.GuidUtil;
import com.hlife.framework.util.HttpClientUtil;
import com.hlife.framework.util.StringUtil;
import com.hlife.framework.util.WeChatUtil;
import com.hlife.server.scenes.constant.ScenesConstant;
import com.hlife.server.scenes.dao.CustomFormTagMapper;
import com.hlife.server.scenes.handler.checkhandler.ICheckAdapter;
import com.hlife.server.scenes.handler.updatehandler.IUpdateAdapter;
import com.hlife.server.scenes.model.CustomFormTag;
import com.hlife.server.scenes.model.MatchCustomFormAndTag;
import com.hlife.server.scenes.service.AdditionalTagService;
import com.hlife.server.scenes.service.CustomFormTagService;
import com.hlife.server.scenes.service.MatchCustomFormAndTagService;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Autowired
    private BusinessConfig businessConfig;

    @Resource(name = "tagRemoveListeners")
    private List<ICheckAdapter> tagRemoveListeners;

    @Resource(name = "tagUpdateListeners")
    private List<IUpdateAdapter> tagUpdateListeners;

    @Override
    public CustomFormTag addOrEditCustomFormTag(CustomFormTag customFormTag) {
        checkTagNameAndTagCode(customFormTag);

        if (StringUtil.stringIsNotNull(customFormTag.getId())) {
            String id = customFormTag.getId();
            CustomFormTag record = this.customFormTagMapper.selectCustomFormTagById(id);
            this.deleteCustomFormTagMainById(id);

            JSONObject jsonObject =
                    new JSONObject().fluentPut("tagId", id)
                            .fluentPut("tagName", customFormTag.getTagName())
                            .fluentPut("tagValue", customFormTag.getTagValue());
            for (IUpdateAdapter iUpdateAdapter : tagUpdateListeners) {
                iUpdateAdapter.update(jsonObject);
            }
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
        //this.matchCustomFormAndTagService.saveMatchCustomFormAndTagBatch(correspondingForms, customFormTag.getId());
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

        setQueryDoc(jsonObject, queryDoc);

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

        setQueryDoc(jsonObject, queryDoc);

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

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tagId", id);

        for (ICheckAdapter iCheckAdapter : tagRemoveListeners) {
            if (!iCheckAdapter.check(jsonObject)) {
                throw new IllegalArgumentException("标签已被引用");
            }
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

        jsonObject.put(ScenesConstant.GROUP_LABEL, "量表");
        jsonObject.put(ScenesConstant.GROUP_OPTIONS, ScenesConstant.Scale.getScaleList());

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
    public List<String> selectCustomFormIdsForMassageContent(JSONObject jsonObject) {
        JSONArray jsonArray = new JSONArray(), tagIds = jsonObject.getJSONArray("tagIdList");
        String type = jsonObject.getString("type");

        jsonArray.addAll(tagIds);
        jsonArray.addAll(jsonObject.getJSONArray("baseTagIdList"));
        jsonArray.add(jsonObject.getString("scenesId"));

        List<String> tagIdList = new ArrayList<>();
        for (int i = 0, size = jsonArray.size(); i < size; i++) {
            tagIdList.add(jsonArray.getString(i));
        }

        List<MatchCustomFormAndTag> matchList = this.matchCustomFormAndTagService.selectCustomFormsByTagIdList(tagIdList);

        Map<String, List<MatchCustomFormAndTag>> formGroup = matchList.stream().collect(Collectors.groupingBy(MatchCustomFormAndTag::getCustomFormId));

        for (int i = tagIds.size() - 1; i >= 0; i--) {
            String tagId = tagIds.getString(i);
            List<String> formIdList = this.matchForm(formGroup, tagIdList, type);
            if (formIdList != null && formIdList.size() > 0) {
                return formIdList;
            }
            tagIdList.remove(tagId);
        }

        return Collections.emptyList();
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

    @Override
    public String notificationVisit(JSONObject jsonObject) {
        String weChatId = jsonObject.getString("weChatId");
        if (StringUtil.stringIsNull(weChatId)) {
            throw new IllegalArgumentException("传入微信id为空");
        }
        JSONArray tagList = jsonObject.getJSONArray("tagList");
        if (tagList == null || tagList.isEmpty()) {
            throw new IllegalArgumentException("传入标签list为空");
        }

        List<String> formIdList = this.matchCustomFormAndTagService.selectCustomFormIdsByTagIdList(this.selectTagIdListByJSONArray(tagList));

        for (ScenesConstant.RelatedFormType relatedFormType : ScenesConstant.RelatedFormType.values()) {
            this.pushMessage(weChatId, formIdList, relatedFormType);
        }

        return "下达表单成功";
    }

    @Override
    public List<String> selectTagIdListByJSONArray(JSONArray tagList) {
        return this.selectCustomFormTagListByJSONArray(tagList)
                .stream()
                .map(customFormTag -> customFormTag.getId())
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomFormTag> selectCustomFormTagListByJSONArray(JSONArray tagList) {
        return this.customFormTagMapper.selectCustomFormTagListByJSONArray(tagList);
    }

    @Override
    public String selectTagIdByTagName(JSONObject tagJSONObject) {
        Document queryDoc = new Document("tagName", tagJSONObject.getString("tagName"))
                .append("tagValue", tagJSONObject.getString("tagValue"));
        String tagId = this.customFormTagMapper.selectTagIdByTagName(queryDoc);
        if (StringUtil.stringIsNotNull(tagId)) {
            return tagId;
        }
        new Thread(() -> this.addOrEditCustomFormTag(tagJSONObject)).start();
        return null;
    }

    private void addOrEditCustomFormTag(JSONObject tagJSONObject) {
        this.addOrEditCustomFormTagSelf(
                new CustomFormTag()
                        .setTagName(tagJSONObject.getString("tagName"))
                        .setTagValue(tagJSONObject.getString("tagValue"))
                        .setTagType("04")
        );
    }

    @Override
    public List<String> selectCustomFormIdsByTagId(String tagId, String key) {
        return this.matchCustomFormAndTagService.selectCustomFormIdsByTagId(tagId)
                .stream()
                .filter(formId -> formId != null && formId.split(";").length == 2 && formId.split(";")[1].equals(key))
                .map(formId -> formId.split(";")[0])
                .collect(Collectors.toList());
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


    private List<String> matchForm(Map<String, List<MatchCustomFormAndTag>> formMap, List<String> tagIdList, String type) {
        if (tagIdList == null || tagIdList.size() < 2) {
            return Collections.emptyList();
        }

        List<String> formIdList = formMap.entrySet().stream()
                .filter(entry -> this.matchList(entry.getValue(), tagIdList)
                        && entry.getKey().split(";")[1].equals(type))
                .map(entry -> entry.getKey().split(";")[0])
                .collect(Collectors.toList());


        return formIdList;
    }

    private boolean matchList(List<MatchCustomFormAndTag> value, List<String> tagIdList) {
        return this.equalsList(value.stream().map(MatchCustomFormAndTag::getTagId).collect(Collectors.toList()), tagIdList);
    }

    /**
     * 判断 表单 是否拥有所有传入的标签
     *
     * @param value     表单相关标签
     * @param tagIdList 传入的标签
     * @return true/false
     */
    private boolean equalsList(List<String> value, List<String> tagIdList) {
        for (String tagId : tagIdList) {
            if (!value.contains(tagId)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证标签名+值， 标签代码是否重复
     *
     * @param customFormTag 标签信息
     */
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

    /**
     * 推送消息
     *
     * @param weChatId        微信id
     * @param formIdList      表单id list
     * @param relatedFormType 表单类
     */
    private void pushMessage(String weChatId, List<String> formIdList, ScenesConstant.RelatedFormType relatedFormType) {
        List<String> dataGuidList = formIdList.stream()
                .filter(formId -> formId.split(";")[1].equals(relatedFormType.getKey()))
                .map(formId -> formId.split(";")[0])
                .collect(Collectors.toList());

        if (dataGuidList == null || dataGuidList.isEmpty() || StringUtil.stringIsNull(weChatId)) {
            return;
        }

        JSONObject paramObject = new JSONObject();
        paramObject.put("type", relatedFormType.getPushType());

        JSONObject data = new JSONObject();
        data.put("dataGuidList", dataGuidList);
        data.put("title", relatedFormType.getTitle());
        data.put("content", relatedFormType.getContent());

        String url = String.format(
                HttpClientUtil.HTTP_URL_FORMAT,
                businessConfig.getMsgPublishUrl(),
                businessConfig.getMsgPublishPort(),
                "wpa/msg/sendPubMsg"
        );
        WeChatUtil.pushMassage("", weChatId, url, paramObject, data);
    }

    /**
     * 修改时删除标签主信息
     *
     * @param id 标签
     * @return
     */
    private void deleteCustomFormTagMainById(String id) {
        Document queryDoc = new Document("id", id);
        if (!this.customFormTagMapper.isExists(queryDoc)) {
            throw new RuntimeException("该数据不存在");
        }

        // 删除附加标签信息
        this.additionalTagService.deleteAdditionalTagByMainTagId(id);

        // 删除主信息
        if (this.customFormTagMapper.deleteCustomFormTagById(id) < 1) {
            throw new RuntimeException("操作失败");
        }
    }


    /**
     * 设置查询条件
     *
     * @param jsonObject
     * @param queryDoc
     */
    private void setQueryDoc(JSONObject jsonObject, Document queryDoc) {
        String tagName = jsonObject.getString("tagName");
        if (StringUtil.stringIsNotNull(tagName)) {
            queryDoc.append("tagName", Pattern.compile("^.*" + tagName + ".*$", Pattern.CASE_INSENSITIVE));
        }

        String tagValue = jsonObject.getString("tagValue");
        if (StringUtil.stringIsNotNull(tagValue)) {
            queryDoc.append("tagValue", Pattern.compile("^.*" + tagValue + ".*$", Pattern.CASE_INSENSITIVE));
        }

        String tagType = jsonObject.getString("tagType");
        if (StringUtil.stringIsNotNull(tagType)) {
            queryDoc.append("tagType", tagType);
        }

        String node = jsonObject.getString("node");
        if (StringUtil.stringIsNotNull(node)) {
            List<Document> nodeList = new ArrayList<>();
            nodeList.add(new Document("node", Integer.valueOf(node)));
            nodeList.add(new Document("node", null));
            queryDoc.append("$or", nodeList);
        }
    }
}
