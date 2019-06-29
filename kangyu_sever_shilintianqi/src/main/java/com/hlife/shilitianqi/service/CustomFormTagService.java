package com.hlife.shilitianqi.service;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageResult;
import com.hlife.shilitianqi.model.CustomFormTag;
import com.hlife.shilitianqi.model.MatchCustomFormAndTag;

import java.util.List;
import java.util.Map;

/**
 * 自定义表单标签服务层接口
 */
public interface CustomFormTagService {

    /**
     * 新增/修改自定义表单标签
     * @param customFormTag 自定义表单标签 信息
     * @return 自定义表单标签 信息
     */
    CustomFormTag addOrEditCustomFormTag(CustomFormTag customFormTag);

    /**
     *
     * @param customFormTag
     * @return
     */
    CustomFormTag addOrEditCustomFormTagSelf(CustomFormTag customFormTag);

    /**
     * 查询自定义表单列表(分页)
     * @param jsonObject 查询条件 <br/>
     *                  tagName 标签名称 <br/>
     *                   pageSize [必传] 每页条数 <br/>
     *                   pageNum [必传] 当前页 <br/>
     * @return 自定义表单列表(分页)
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
     * 获取标签树
     * @param jsonObject 查询条件 <br/>
     *                   tagType 标签类别
     * @return 标签树
     */
    List<Map<String, Object>> getCustomFormTagTree(JSONObject jsonObject);

    /**
     * 根据id 删除 自定义表单标签
     * @param id
     * @return
     */
    String deleteCustomFormTagById(String id);

    /**
     * 查询相关表单列表
     *
     * @return 相关表单列表
     */
    List<JSONObject> getCorrespondingFromList();

    /**
     * 根据 标签id list 查询对应的 表单id list
     * @param tagIdList 标签id list
     * @return 对应的 表单id list
     */
    List<String> selectCustomFormIdsByTagIdList(List<String> tagIdList);

    /**
     * 给表单添加标签
     *
     * @param matchCustomFormAndTag 标签 表单关联数据
     * @return 标签 表单关联数据
     */
    MatchCustomFormAndTag addMatchCustomFormAndTag(MatchCustomFormAndTag matchCustomFormAndTag);

    /**
     * 根据表单id 获取对应的 标签
     *
     * @param formId 表单id
     * @return 标签表单对应数据
     */
    List<MatchCustomFormAndTag> getTagListByFormId(String formId);

    /**
     * 删除某表单的某个标签
     * @param formId 表单id
     * @param tagId 标签id
     * @return
     */
    Long deleteMatchCustomFormAndTag(String formId, String tagId);

    /**
     * 根据 id 查询对应的标签
     *
     * @param tagId 标签 id
     * @return 标签 数据
     */
    CustomFormTag selectCustomFormTagById(String tagId);
}
