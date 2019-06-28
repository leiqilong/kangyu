package com.hlife.shilitianqi.controller;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageResult;
import com.hlife.framework.base.ResultVO;
import com.hlife.shilitianqi.model.CustomFormTag;
import com.hlife.shilitianqi.model.MatchCustomFormAndTag;
import com.hlife.shilitianqi.service.CustomFormTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 自定义表单标签控制器
 */
@RestController
@RequestMapping(value = "/customFormTag")
public class CustomFormTagController {

    @Autowired
    private CustomFormTagService customFormTagService;

    /**
     * 新增或修改自定义表单标签
     *
     * @param customFormTag 自定义表单标签 内容
     * @return resultVO.resultData 新增或修改成功的内容
     */
    @PostMapping(value = "/addOrEditCustomFormTag")
    public ResultVO<CustomFormTag> addOrEditCustomFormTag(@RequestBody CustomFormTag customFormTag) {
        return new ResultVO<>(customFormTagService.addOrEditCustomFormTag(customFormTag));
    }

    /**
     * 获取自定义表单标签列表（分页查询）
     *
     * @param jsonObject 查询条件 <br/>
     *                    tagName 标签名称 <br/>
     *                   pageSize [必传] 每页条数 <br/>
     *                   pageNum [必传] 当前页 <br/>
     * @return resultVO.resultData 自定义表单标签列表
     */
    @PostMapping(value = "/getCustomFormTagPageResult")
    public ResultVO<PageResult<CustomFormTag>> getCustomFormTagPageResult(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(customFormTagService.getCustomFormTagPageResult(jsonObject));
    }

    /**
     * 获取自定义表单标签列表（不分页）
     *
     * @param jsonObject 查询条件 <br/>
     *                     tagType 标签类别 <br/>
     *                     tagTypeList 标签类别列表 <br/>
     * @return resultVO.resultData 自定义表单标签列表
     */
    @PostMapping(value = "/getCustomFormTagList")
    public ResultVO<List<CustomFormTag>> getCustomFormTagList(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(customFormTagService.getCustomFormTagList(jsonObject));
    }

    /**
     * 获取自定义表单标签树
     *
     * @param jsonObject 查询条件 <br/>
     *                     tagType 标签类别 <br/>
     *                     tagTypeList 标签类别List
     * @return resultVO.resultData 自定义表单标签列表
     */
    @PostMapping(value = "/getCustomFormTagTree")
    public ResultVO<List<Map<String, Object>>> getCustomFormTagTree(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(this.customFormTagService.getCustomFormTagTree(jsonObject));
    }

    /**
     * 根据id 删除标签
     * @param id 标签 id
     * @return 删除是否成功
     */
    @DeleteMapping(value = "/deleteCustomFormTagById/{id}/{rand}")
    public ResultVO<String> deleteCustomFormTagById(@PathVariable("id") String id) {
        return new ResultVO<>(customFormTagService.deleteCustomFormTagById(id));
    }

    /**
     * 查询相关表单列表
     * @return 删除是否成功
     */
    @GetMapping(value = "/getCorrespondingFromList/{rand}")
    public ResultVO<List<JSONObject>> getCorrespondingFromList() {
        return new ResultVO<>(customFormTagService.getCorrespondingFromList());
    }

    /**
     * 给表单添加标签
     * @return 标签 表单 关联
     */
    @PostMapping(value = "/addMatchCustomFormAndTag}")
    public ResultVO<MatchCustomFormAndTag> addMatchCustomFormAndTag(@RequestBody MatchCustomFormAndTag matchCustomFormAndTag) {
        return new ResultVO<>(customFormTagService.addMatchCustomFormAndTag(matchCustomFormAndTag));
    }

    @GetMapping(value = "/getTagListByFormId/{formId}/{rand}")
    public ResultVO<List<MatchCustomFormAndTag>> getTagListByFormId(@PathVariable("formId") String formId) {
        return new ResultVO<>(customFormTagService.getTagListByFormId(formId));
    }

    @DeleteMapping(value = "/deleteMatchCustomFormAndTag/{formId}/{tagId}/{rand}")
    public ResultVO<Long> deleteMatchCustomFormAndTag(@PathVariable("formId") String formId, @PathVariable("tagId") String tagId) {
        return new ResultVO<>(customFormTagService.deleteMatchCustomFormAndTag(formId, tagId));
    }

}
