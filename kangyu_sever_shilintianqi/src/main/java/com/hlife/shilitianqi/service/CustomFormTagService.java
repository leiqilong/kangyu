package com.hlife.shilitianqi.service;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageResult;
import com.hlife.shilitianqi.model.CustomFormTag;

public interface CustomFormTagService {

    CustomFormTag addOrEditCustomFormTag(CustomFormTag customFormTag);

    PageResult<CustomFormTag> getCustomFormTagList(JSONObject jsonObject);
}
