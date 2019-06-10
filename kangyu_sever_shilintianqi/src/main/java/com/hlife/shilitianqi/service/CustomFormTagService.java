package com.hlife.shilitianqi.service;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageResult;
import com.hlife.shilitianqi.model.CustomFormTag;

import java.util.List;

/**
 * 自定义表单标签服务层接口
 */
public interface CustomFormTagService {

    /**
     * 新增（修改自定义表单标签）
     * @param customFormTag
     * @return
     */
    CustomFormTag addOrEditCustomFormTag(CustomFormTag customFormTag);

    /**
     * 查询自定义表单列表(分页)
     * @param jsonObject 查询条件 <br/>
     *                  tagName 标签名称 <br/>
     *                   pageSize [必传] 每页条数 <br/>
     *                   pageNum [必传] 当前页 <br/>
     * @return
     */
    PageResult<CustomFormTag> getCustomFormTagPageResult(JSONObject jsonObject);

    /**
     * 获取自定义表单标签列表（不分页）
     *
     * @param jsonObject 查询条件 <br/>
     *      *               tagType 标签类别 <br/>
     * @return 自定义表单标签列表
     */
    List<CustomFormTag> getCustomFormTagList(JSONObject jsonObject);

    /**
     * 根据id 删除 自定义表单标签
     * @param id
     * @return
     */
    String deleteCustomFormTagById(String id);
}
