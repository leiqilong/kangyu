package com.hlife.shilitianqi.service;

import com.alibaba.fastjson.JSONArray;
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
     *
     * @param customFormTag 自定义表单标签 信息
     * @return 自定义表单标签 信息
     */
    CustomFormTag addOrEditCustomFormTag(CustomFormTag customFormTag);

    /**
     * @param customFormTag
     * @return
     */
    CustomFormTag addOrEditCustomFormTagSelf(CustomFormTag customFormTag);

    /**
     * 查询自定义表单列表(分页)
     *
     * @param jsonObject 查询条件 <br/>
     *                   tagName 标签名称 <br/>
     *                   pageSize [必传] 每页条数 <br/>
     *                   pageNum [必传] 当前页 <br/>
     * @return 自定义表单列表(分页)
     */
    PageResult<CustomFormTag> getCustomFormTagPageResult(JSONObject jsonObject);

    /**
     * 获取自定义表单标签列表（不分页）
     *
     * @param jsonObject 查询条件 <br/>
     *                   *               tagType 标签类别 <br/>
     * @return 自定义表单标签列表
     */
    List<CustomFormTag> getCustomFormTagList(JSONObject jsonObject);

    /**
     * 获取标签树
     *
     * @param jsonObject 查询条件 <br/>
     *                   tagType 标签类别
     * @return 标签树
     */
    List<Map<String, Object>> getCustomFormTagTree(JSONObject jsonObject);

    /**
     * 根据id 删除 自定义表单标签
     *
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
     *
     * @param tagIdList 标签id list
     * @return 对应的 表单id list
     */
    List<String> selectCustomFormIdsByTagIdList(List<String> tagIdList, String type);

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
    List<String> getTagListByFormId(String formId);


    /**
     * 删除某表单的某个标签
     *
     * @param jsonObject <br/>
     *                   formId 表单id <br/>
     *                   tagId  标签id
     * @return
     */
    Long deleteMatchCustomFormAndTag(JSONObject jsonObject);

    /**
     * 根据 id 查询对应的标签
     *
     * @param tagId 标签 id
     * @return 标签 数据
     */
    CustomFormTag selectCustomFormTagById(String tagId);

    /**
     * 给标单批量添加标签
     *
     * @param jsonObject <br/>
     *                   tagIdList： 标签 list <br/>
     *                   customFormId: 表单id
     * @return 添加成功
     */
    String addMatchCustomFormAndTagList(JSONObject jsonObject);

    /**
     * 商城购买服务，通知就诊
     * @param jsonObject 商场参数 <br/>
     *                   weChatId string 微信id <br/>
     *                   tagList JSONArray 标签列表 <br/>
     *                          { tagName string 标签名 ”运动“
     *                              tagValue string 标签值 "不足"
     *                          }
     *
     * @return  下达成功
     */
    String notificationVisit(JSONObject jsonObject);

    /**
     * 根据标签 list 查询对应的标签id list
     *
     * @param tagList 标签 jsonArray <br/>
     *                {tagName: 标签名 ”运动“
     *                tagValue: 标签值 "不足"}
     * @return 标签id list
     */
    List<String> selectTagIdListByJSONArray(JSONArray tagList);

    /**
     * 根据标签 list 查询对应的标签实体类 list
     *
     * @param tagList 标签 jsonArray <br/>
     *                {tagName: 标签名 ”运动“
     *                tagValue: 标签值 "不足"}
     * @return 标签实体类 list
     */
    List<CustomFormTag> selectCustomFormTagListByJSONArray(JSONArray tagList);
}
