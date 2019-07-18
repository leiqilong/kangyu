package com.hlife.shilitianqi.controller;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageResult;
import com.hlife.framework.base.ResultVO;
import com.hlife.shilitianqi.model.CustomFormTag;
import com.hlife.shilitianqi.model.DeviceOfScenes;
import com.hlife.shilitianqi.model.JudgeStandard;
import com.hlife.shilitianqi.model.Scenes;
import com.hlife.shilitianqi.service.ScenesService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 场景 控制器
 */

@RestController
@RequestMapping(value = "/scenes")
public class ScenesController {

    @Autowired
    private ScenesService scenesService;

    /**
     * 查询所有场景列表信息
     *
     * @return 所有场景列表信息
     */
    @GetMapping(value = "/searchScenesList/{rand}")
    public ResultVO<List<Scenes>> searchScenesList() {
        return new ResultVO<>(this.scenesService.searchScenesListAll());
    }

    /**
     * 分页查询所有场景列表信息
     *
     * @return 所有场景列表信息
     */
    @PostMapping(value = "/searchScenesListByParams")
    public ResultVO<PageResult<Scenes>> searchScenesListByParams(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(this.scenesService.searchScenesListByParams(jsonObject));
    }

    /**
     * 保存/修改 场景基本信息
     *
     * @param scenes 场景基本信息
     * @return resultVO.resultData
     */
    @PostMapping(value = "/saveOrEditScenes")
    public ResultVO<Scenes> saveOrEditScenes(@RequestBody Scenes scenes) {
        return new ResultVO<>(this.scenesService.saveOrEditScenes(scenes));
    }

    /**
     * 删除场景信息
     *
     * @param scenesId 场景设备 id
     * @return resultVO.resultData 影响的条数
     */
    @DeleteMapping(value = "/deleteScenesById/{scenesId}/{rand}")
    public ResultVO<Long> deleteScenesById(@PathVariable("scenesId") String scenesId) {
        return new ResultVO<>(this.scenesService.deleteScenesById(scenesId));
    }

    /**
     * 保存/修改 场景设备
     *
     * @param deviceOfScenes 场景设备信息
     * @return resultVO.resultData 场景设备信息
     */
    @PostMapping(value = "/saveOrEditDeviceOfScenes")
    public ResultVO<DeviceOfScenes> saveOrEditDeviceOfScenes(@RequestBody DeviceOfScenes deviceOfScenes) {
        return new ResultVO<>(this.scenesService.saveOrEditDeviceOfScenes(deviceOfScenes));
    }

    /**
     * 根据场景id 查询对应的设备列表
     *
     * @param scenesId 场景id
     * @return resultVO.resultData 场景设备列表
     */
    @GetMapping(value = "/searchDeviceOfScenesList/{scenesId}/{rand}")
    public ResultVO<List<DeviceOfScenes>> searchDeviceOfScenesList(@PathVariable("scenesId") String scenesId) {
        return new ResultVO<>(this.scenesService.searchDeviceOfScenesList(scenesId));
    }

    /**
     * 根据场景id 查询对应的设备列表 （分页）
     *
     * @param jsonObject 场景id
     * @return resultVO.resultData 场景设备列表
     */
    @PostMapping(value = "/searchDeviceOfScenesListByParam")
    public ResultVO<PageResult<DeviceOfScenes>> searchDeviceOfScenesListByParam(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(this.scenesService.searchDeviceOfScenesListByParam(jsonObject));
    }

    /**
     * 保存/修改 场景设备规则
     *
     * @param judgeStandard 场景设备规则信息
     * @return resultVO.resultData 场景设备规则信息
     */
    @PostMapping(value = "/saveOrEditJudgeStandard")
    public ResultVO<JudgeStandard> saveOrEditJudgeStandard(@RequestBody JudgeStandard judgeStandard) {
        return new ResultVO<>(this.scenesService.saveOrEditJudgeStandard(judgeStandard));
    }

    /**
     * 删除场景设备信息
     *
     * @param deviceOfScenesId 场景设备 id
     * @return resultVO.resultData 影响的条数
     */
    @DeleteMapping(value = "/deleteDeviceOfScenes/{deviceOfScenesId}/{rand}")
    public ResultVO<Long> deleteDeviceOfScenes(@PathVariable("deviceOfScenesId") String deviceOfScenesId) {
        return new ResultVO<>(this.scenesService.deleteDeviceOfScenes(deviceOfScenesId));
    }

    /**
     * 根据场景设备id 查询 场景设备规则列表信息
     *
     * @param deviceOfScenesId 场景设备id
     * @return resultVO.resultData 场景设备规则列表信息
     */
    @GetMapping(value = "/searchJudgeStandardList/{deviceOfScenesId}/{rand}")
    public ResultVO<List<JudgeStandard>> searchJudgeStandardList(@PathVariable("deviceOfScenesId") String deviceOfScenesId) {
        return new ResultVO<>(this.scenesService.searchJudgeStandardList(deviceOfScenesId));
    }

    /**
     * 根据 id 删除场景设备规则
     *
     * @param judgeStandardId 场景设备规则 id
     * @return resultVO.resultData 影响的行数
     */
    @DeleteMapping(value = "/deleteJudgeStandard/{judgeStandardId}/{rand}")
    public ResultVO<Long> deleteJudgeStandard(@PathVariable("judgeStandardId") String judgeStandardId) {
        return new ResultVO<>(this.scenesService.deleteJudgeStandard(judgeStandardId));
    }


    /**
     * 获取自定义表单标签列表（不分页）
     *
     * @param jsonObject 查询条件 <br/>
     *                   tagType 标签类别 <br/>
     *                   tagTypeList 标签类别List
     * @return resultVO.resultData 自定义表单标签列表
     */
    @PostMapping(value = "/getCustomFormTagList")
    public ResultVO<List<CustomFormTag>> getCustomFormTagList(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(this.scenesService.getCustomFormTagList(jsonObject));
    }


    /**
     * 获取场景标签和得分
     *
     * @param guid     患者guid
     * @param scenesId 场景id
     * @return 得分情况
     */
    @ApiOperation("获取场景标签和得分")
    @GetMapping("/getTagAndScore/{guid}/{scenesId}/{rand}")
    public ResultVO<Map<String, Object>> getTagAndScore(@PathVariable("guid") String guid, @PathVariable("scenesId") String scenesId) {
        return new ResultVO<>(this.scenesService.getTagAndScore(guid, scenesId));
    }

    /**
     * 获取宣教list
     *
     * @param guid     患者guid
     * @param scenesId 场景id
     * @return 宣教list
     */
    @ApiOperation("获取宣教表单list")
    @GetMapping("/getMission/{guid}/{scenesId}/{rand}")
    public ResultVO<List<String>> getMission(@PathVariable("guid") String guid, @PathVariable("scenesId") String scenesId) {
        return new ResultVO<>(this.scenesService.getMission(guid, scenesId));
    }

    /**
     * 获取调查问卷表单list
     *
     * @param guid     患者guid
     * @param scenesId 场景id
     * @return 查问卷表单list
     */
    @ApiOperation("获取调查问卷表单list")
    @GetMapping("/getSurvey/{guid}/{scenesId}/{rand}")
    public ResultVO<List<String>> getSurvey(@PathVariable("guid") String guid, @PathVariable("scenesId") String scenesId) {
        return new ResultVO<>(this.scenesService.getSurvey(guid, scenesId));
    }

    /**
     * 根据前台的数据 获取场景标签和得分
     *
     * @param jsonObject scenesId 场景id <br>
     *                   userArray 用户信息
     * @return 得分情况
     */
    @ApiOperation("根据前台的数据， 获取场景标签和得分")
    @PostMapping("/getTagAndScoreTwice")
    public ResultVO<Map<String, Object>> getTagAndScoreTwice(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(this.scenesService.getTagAndScoreTwice(jsonObject));
    }


    /**
     * 根据前台的数据， 获取宣教表单list
     *
     * @param jsonObject <br/>
     *                   scenesId 场景id <br/>
     *                   userArray 前台传来的 患者数据
     * @return 宣教表单list
     */
    @ApiOperation("根据前台的数据， 获取宣教表单list")
    @PostMapping("/getMissionTwice")
    public ResultVO<List<String>> getMissionTwice(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(this.scenesService.getMissionTwice(jsonObject));
    }

    /**
     * 根据前台的数据， 获取调查问卷表单list
     *
     * @param jsonObject <br/>
     *                   scenesId 场景id <br/>
     *                   userArray 前台传来的 患者数据
     * @return 调查问卷表单list
     */
    @ApiOperation("根据前台的数据， 获取查问卷表单list")
    @PostMapping("/getSurveyTwice")
    public ResultVO<List<String>> getSurveyTwice(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(this.scenesService.getSurveyTwice(jsonObject));
    }

    /**
     * 推送宣教list
     *
     * @param jsonObject <br>
     *                   guid     患者guid <br>
     *                   scenesId 场景id
     * @return 成功消息
     */
    @ApiOperation("推送宣教list")
    @PostMapping("/publishMission")
    public ResultVO<String> publishMission(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(this.scenesService.publishMission(jsonObject));
    }

    /**
     * 推送调查问卷list
     *
     * @param jsonObject <br>
     *                   guid     患者guid <br>
     *                   scenesId 场景id
     * @return 成功消息
     */
    @ApiOperation("推送调查问卷list")
    @PostMapping("/publishSurvey")
    public ResultVO<String> publishSurvey(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(this.scenesService.publishSurvey(jsonObject));
    }

    /**
     * 查询数据库中的设备列表
     *
     * @return 设备列表
     */
    @ApiOperation("查询数据库中的设备列表")
    @GetMapping("/createDeviceOfScenesListByDevice/{rand}")
    public ResultVO<List<DeviceOfScenes>> createDeviceOfScenesListByDevice() {
        return new ResultVO<>(this.scenesService.createDeviceOfScenesListByDevice());
    }
}
