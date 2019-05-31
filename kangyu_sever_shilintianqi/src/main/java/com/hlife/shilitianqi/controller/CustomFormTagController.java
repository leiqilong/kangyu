package com.hlife.shilitianqi.controller;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageResult;
import com.hlife.framework.base.ResultVO;
import com.hlife.shilitianqi.model.CustomFormTag;
import com.hlife.shilitianqi.service.CustomFormTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @return
     */
    @PostMapping(value = "/addOrEditCustomFormTag")
    public ResultVO<CustomFormTag> addOrEditCustomFormTag(@RequestBody CustomFormTag customFormTag) {
        return new ResultVO<>(customFormTagService.addOrEditCustomFormTag(customFormTag));
    }

    /**
     * 获取
     *
     * @param jsonObject
     * @return
     */
    @PostMapping(value = "/getCustomFormTagList")
    public ResultVO<PageResult<CustomFormTag>> getCustomFormTagList(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(customFormTagService.getCustomFormTagList(jsonObject));
    }
}
