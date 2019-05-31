package com.hlife.shilitianqi.controller;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageResult;
import com.hlife.framework.base.ResultVO;
import com.hlife.shilitianqi.model.CustomFormTag;
import com.hlife.shilitianqi.service.CustomFormTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 获取自定义表单标签列表
     *
     * @param jsonObject 查询条件 <br/>
     *               tagName 标签名称
     * @return resultVO.resultData 自定义表单标签列表
     */
    @PostMapping(value = "/getCustomFormTagList")
    public ResultVO<PageResult<CustomFormTag>> getCustomFormTagList(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(customFormTagService.getCustomFormTagList(jsonObject));
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
}
