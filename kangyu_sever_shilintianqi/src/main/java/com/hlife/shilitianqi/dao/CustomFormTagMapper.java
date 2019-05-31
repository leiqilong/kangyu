package com.hlife.shilitianqi.dao;

import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.shilitianqi.model.CustomFormTag;
import org.bson.Document;

public interface CustomFormTagMapper {
    CustomFormTag addCustomFormTag(CustomFormTag customFormTag);

    PageResult<CustomFormTag> getCustomFormTagList(Document queryDoc, PageParam pageParam);
}
