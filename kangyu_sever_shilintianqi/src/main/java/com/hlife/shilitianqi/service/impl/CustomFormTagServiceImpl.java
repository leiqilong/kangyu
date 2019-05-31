package com.hlife.shilitianqi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.framework.util.GuidUtil;
import com.hlife.framework.util.StringUtil;
import com.hlife.shilitianqi.dao.CustomFormTagMapper;
import com.hlife.shilitianqi.model.CustomFormTag;
import com.hlife.shilitianqi.service.CustomFormTagService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.regex.Pattern;

@Service
public class CustomFormTagServiceImpl implements CustomFormTagService {

    @Autowired
    private CustomFormTagMapper customFormTagMapper;

    @Override
    public CustomFormTag addOrEditCustomFormTag(CustomFormTag customFormTag) {
        if (StringUtil.stringIsNotNull(customFormTag.getId())) {
            String id = customFormTag.getId();
            CustomFormTag record = this.customFormTagMapper.selectCustomFormTagById(id);
            this.deleteCustomFormTagById(id);
            customFormTag.setCreateTime(record.getCreateTime());
        } else {
            customFormTag.setId(GuidUtil.generateGuid())
                    .setCreateTime(new Date());
        }
        CustomFormTag newCustomFormTag = this.customFormTagMapper.addCustomFormTag(customFormTag);

        return customFormTagMapper.addCustomFormTag(customFormTag);
    }

    @Override
    public PageResult<CustomFormTag> getCustomFormTagList(JSONObject jsonObject) {
        Document queryDoc = new Document();

        String tagName = jsonObject.getString("tagName");
        if (StringUtil.stringIsNotNull(tagName)) {
            queryDoc.append("tagName", Pattern.compile("^.*" + tagName + ".*$", Pattern.CASE_INSENSITIVE));
        }
        PageParam pageParam = new PageParam(jsonObject.getInteger(PageParam.PAGE_SIZE),
                jsonObject.getInteger(PageParam.PAGE_NUM));
        return customFormTagMapper.getCustomFormTagList(queryDoc, pageParam);
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
        if (this.customFormTagMapper.deleteCustomFormTagById(id) < 1) {
            throw new RuntimeException("操作失败");
        }
        return "操作成功";
    }
}
