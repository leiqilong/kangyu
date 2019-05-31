package com.hlife.shilitianqi.dao;

import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.shilitianqi.model.CustomFormTag;
import org.bson.Document;

public interface CustomFormTagMapper {
    /**
     * 新境自定义表单标签
     * @param customFormTag 自定义表单标签
     * @return
     */
    CustomFormTag addCustomFormTag(CustomFormTag customFormTag);

    /**
     * 查询自定义表单列表
     * @param queryDoc 查询条件
     * @param pageParam 分页条件
     * @return 自定义表单列表
     */
    PageResult<CustomFormTag> getCustomFormTagList(Document queryDoc, PageParam pageParam);

    /**
     * 查看当前页签是否存在
     * @param queryDoc
     * @return
     */
    boolean isExists(Document queryDoc);

    /**
     * 根据id 删除标签
     * @param id
     * @return
     */
    long deleteCustomFormTagById(String id);

    /**
     *
     * @param id
     * @return
     */
    CustomFormTag selectCustomFormTagById(String id);
}
