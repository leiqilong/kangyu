package com.hlife.server.scale.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.component.MessageComponent;
import com.hlife.framework.util.StringUtil;
import com.hlife.server.core.model.Record;
import com.hlife.server.scenes.constant.ScenesConstant;
import com.hlife.server.scenes.service.CustomFormTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WeChatComponent {

    @Autowired
    private CustomFormTagService customFormTagService;
    @Autowired
    private MessageComponent messageComponent;

    private static final String fmt = "标签名称：%s, 标签值：%s, 标签不存在!";

    void putMessage(Record record, JSONObject tagJSONObject) {
        String tagId = this.customFormTagService.selectTagIdByTagName(tagJSONObject);

        if (StringUtil.stringIsNull(tagId)) {
            String errorMsg = String.format(fmt, tagJSONObject.getString("tagName"), tagJSONObject.getString("tagValue"));
            throw new RuntimeException(errorMsg);
        }

        List<String> formIdList = this.customFormTagService.selectCustomFormIdsByTagId(tagId, ScenesConstant.RelatedFormType.MISSION.getKey());

        if (formIdList == null || formIdList.isEmpty()) {
            return;
        }

        JSONObject paramObject = new JSONObject();
        paramObject.put("type", ScenesConstant.RelatedFormType.MISSION.getPushType());

        JSONObject data = new JSONObject();
        data.put("dataGuidList", formIdList);
        data.put("title", ScenesConstant.RelatedFormType.MISSION.getTitle());
        data.put("content", ScenesConstant.RelatedFormType.MISSION.getContent());

        this.messageComponent.pushWeChatMassage(record.getGuid(), record.getWeixinid(), paramObject, data);
    }
}
