package com.hlife.shilitianqi.dao;

import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.shilitianqi.model.CustomFormTag;
import org.bson.Document;

import java.util.List;

/**
 * 自定义表单标签持久层接口
 */
public interface CustomFormTagMapper {
    /**
     * 新境自定义表单标签
     * @param customFormTag 自定义表单标签
     * @return 清加的标签信息
     */
    CustomFormTag addCustomFormTag(CustomFormTag customFormTag);

    /**
     * 查询自定义表单列表(分页)
     * @param queryDoc 查询条件
     * @param pageParam 分页条件
     * @return 自定义表单列表
     */
    PageResult<CustomFormTag> getCustomFormTagPageResult(Document queryDoc, PageParam pageParam);

    /**
     * 查询自定义表单列表(不分页)
     * @param queryDoc 查询条件
     * @return 自定义表单列表
     */
    List<CustomFormTag> getCustomFormTagList(Document queryDoc);

    /**
     * 查看当前页签是否存在
     * @param queryDoc 查询条伯
     * @return 是否存在
     */
    boolean isExists(Document queryDoc);

    /**
     * 根据id 删除标签
     * @param id id
     * @return 影响的行数
     */
    long deleteCustomFormTagById(String id);

    /**
     * 根所 id 查询标签信息
     * @param id id
     * @return 标签信息
     */
    CustomFormTag selectCustomFormTagById(String id);
}
