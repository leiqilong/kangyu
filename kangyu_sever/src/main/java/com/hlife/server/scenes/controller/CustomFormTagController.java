package com.hlife.server.scenes.controller;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageResult;
import com.hlife.framework.base.ResultVO;
import com.hlife.server.scenes.model.CustomFormTag;
import com.hlife.server.scenes.model.MatchCustomFormAndTag;
import com.hlife.server.scenes.service.CustomFormTagService;
import io.swagger.annotations.ApiOperation;
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
     *                   tagName 标签名称 <br/>
     *                   node 角色
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
     *                   tagType 标签类别 <br/>
     *                   tagTypeList 标签类别列表 <br/>
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
     *                   tagType 标签类别 <br/>
     *                   tagTypeList 标签类别List
     * @return resultVO.resultData 自定义表单标签列表
     */
    @PostMapping(value = "/getCustomFormTagTree")
    public ResultVO<List<Map<String, Object>>> getCustomFormTagTree(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(this.customFormTagService.getCustomFormTagTree(jsonObject));
    }

    /**
     * 根据id 删除标签
     *
     * @param id 标签 id
     * @return 删除是否成功
     */
    @DeleteMapping(value = "/deleteCustomFormTagById/{id}/{rand}")
    public ResultVO<String> deleteCustomFormTagById(@PathVariable("id") String id) {
        return new ResultVO<>(customFormTagService.deleteCustomFormTagById(id));
    }

    /**
     * 查询相关表单列表
     *
     * @return 删除是否成功
     */
    @GetMapping(value = "/getCorrespondingFromList/{rand}")
    public ResultVO<List<JSONObject>> getCorrespondingFromList() {
        return new ResultVO<>(customFormTagService.getCorrespondingFromList());
    }

    /**
     * 给表单添加标签
     *
     * @param matchCustomFormAndTag 表单标签信息
     * @return 标签 表单 关联
     */
    @PostMapping(value = "/addMatchCustomFormAndTag")
    public ResultVO<MatchCustomFormAndTag> addMatchCustomFormAndTag(@RequestBody MatchCustomFormAndTag matchCustomFormAndTag) {
        return new ResultVO<>(customFormTagService.addMatchCustomFormAndTag(matchCustomFormAndTag));
    }

    /**
     * 根据表单id 获取对应的 标签
     *
     * @param jsonObject <br/>
     *                   formId 表单id
     * @return 标签list
     */
    @PostMapping(value = "/getTagListByFormId")
    public ResultVO<List<String>> getTagListByFormId(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(customFormTagService.getTagListByFormId(jsonObject.getString("formId")));
    }

    /**
     * 删除表单标签
     *
     * @param jsonObject <br/>
     *                   formId 表单id <br/>
     *                   tagId  标签 id <br/>
     *                   formIdList 表单id List
     * @return resultVO.resultData 删除的行数
     */
    @PostMapping(value = "/deleteMatchCustomFormAndTag")
    public ResultVO<Long> deleteMatchCustomFormAndTag(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(customFormTagService.deleteMatchCustomFormAndTag(jsonObject));
    }

    /**
     * 给表单添加标签
     *
     * @param jsonObject 表单标签信息 <br/>
     *                   tagIdList： 标签 list <br/>
     *                   customFormId: 表单id
     * @return 标签 表单 关联
     */
    @PostMapping(value = "/addMatchCustomFormAndTagList")
    public ResultVO<String> addMatchCustomFormAndTagList(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(customFormTagService.addMatchCustomFormAndTagList(jsonObject));
    }

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
    @ApiOperation("商城购买服务，通知就诊")
    @PostMapping(value = "/notificationVisit")
    public ResultVO<String> notificationVisit(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(this.customFormTagService.notificationVisit(jsonObject));
    }
}
