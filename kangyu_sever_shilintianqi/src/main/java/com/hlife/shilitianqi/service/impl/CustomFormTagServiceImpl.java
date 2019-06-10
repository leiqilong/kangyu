package com.hlife.shilitianqi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.framework.util.GuidUtil;
import com.hlife.framework.util.StringUtil;
import com.hlife.shilitianqi.dao.CustomFormTagMapper;
import com.hlife.shilitianqi.model.CustomFormTag;
import com.hlife.shilitianqi.service.AdditionalTagService;
import com.hlife.shilitianqi.service.CustomFormTagService;
import com.hlife.shilitianqi.service.MatchCustomFormAndTagService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 自定义表单标签服务层实现
 */
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
        return customFormTagMapper.getCustomFormTagList(queryDoc);
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

        if (this.customFormTagMapper.isExists(queryDoc)) {
            throw new RuntimeException("标签名称重复");
        }
    }
}
