package com.hlife.server.scenes.service;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageResult;
import com.hlife.server.scenes.model.CustomFormTag;
import com.hlife.server.scenes.model.DeviceOfScenes;
import com.hlife.server.scenes.model.JudgeStandard;
import com.hlife.server.scenes.model.Scenes;

import java.util.List;
import java.util.Map;

/**
 * 场景服务层接口
 */
public interface ScenesService {

    /**
     * 查询场景列表
     *
     * @return 所有场景列表
     */
    List<Scenes> searchScenesListAll(int node);

    /**
     * 分页查询场景列表
     *
     * @param jsonObject 查询参数 <br/>
     *                   scenesName 场景名称
     *                   pageSize 每页条数
     *                   pageNum 页数
     * @return 场景列表 分页数据
     */
    PageResult<Scenes> searchScenesListByParams(JSONObject jsonObject);

    /**
     * 新境或修改场景基本信息
     *
     * @param scenes 场景 基本信息
     * @return 场景 基本信息
     */
    Scenes saveOrEditScenes(Scenes scenes);

    /**
     * 删除场景
     *
     * @param scenesId 场景id
     * @return 删除的条数
     */
    Long deleteScenesById(String scenesId);

    /**
     * 新境或修改场景设备信息
     *
     * @param deviceOfScenes 场景设备信息
     * @return 场景设备信息
     */
    DeviceOfScenes saveOrEditDeviceOfScenes(DeviceOfScenes deviceOfScenes);

    /**
     * 根据场景id 查询对应的设备列表
     *
     * @param scenesId 场景id
     * @return 场景设备列表
     */
    List<DeviceOfScenes> searchDeviceOfScenesList(String scenesId);

    /**
     * 根据场景id 查询对应的设备列表分页
     *
     * @param jsonObject
     * @return
     */
    PageResult<DeviceOfScenes> searchDeviceOfScenesListByParam(JSONObject jsonObject);

    /**
     * 根据设备id 删除设备
     *
     * @param deviceOfScenesId 设备id
     * @return 影戏的行数
     */
    Long deleteDeviceOfScenes(String deviceOfScenesId);

    /**
     * 新增或修改场景设备规则信息
     *
     * @param judgeStandard 场景设备规则信息
     * @return 场景设备规则信息
     */
    JudgeStandard saveOrEditJudgeStandard(JudgeStandard judgeStandard);

    /**
     * 根据 场景设备id 查询规则信息
     *
     * @param deviceOfScenesId 场景设备 id
     * @return 规则信息列表
     */
    List<JudgeStandard> searchJudgeStandardList(String deviceOfScenesId);

    /**
     * 根据规则主键删除规则
     *
     * @param judgeStandardId 规则
     * @return 影响的行数
     */
    Long deleteJudgeStandard(String judgeStandardId);

    /**
     * 查询患者某场景下得分与标签数据
     *
     * @param guid     患者id
     * @param scenesId 场景 id
     * @return 得分情况与标签数据
     */
    Map<String, Object> getTagAndScore(String guid, String scenesId);

    /**
     * 获取标签列表
     *
     * @param jsonObject 查询条件 <br/>
     *                   tagType 标签类别 <br/>
     * @return 自定义表单标签列表
     */
    List<CustomFormTag> getCustomFormTagList(JSONObject jsonObject);

    /**
     * 获取宣教list
     *
     * @param guid     患者id
     * @param scenesId 场景 id
     * @return 宣教list
     */
    List<String> getMission(String guid, String scenesId);

    /**
     * 获取调查问卷表单list
     *
     * @param guid     患者guid
     * @param scenesId 场景id
     * @return 调查问卷表单list
     */
    List<String> getSurvey(String guid, String scenesId);



    /**
     * 根据前台数据获取某场景下得分与标签数据
     *
     * @param jsonObject <br/>
     *                   scenesId 场景id <br>
     *                   userArray 用户
     * @return 得分与标签数据
     */
    Map<String, Object> getTagAndScoreTwice(JSONObject jsonObject);


    /**
     * 根据前台的数据， 获取宣教表单list
     *
     * @param jsonObject <br/>
     *                   scenesId 场景id <br/>
     *                   userArray 前台传来的 患者数据
     * @return 宣教表单list
     */
    List<String> getMissionTwice(JSONObject jsonObject);

    /**
     * 根据前台的数据， 获取调查问卷表单list
     *
     * @param jsonObject <br/>
     *                   scenesId 场景id <br/>
     *                   userArray 前台传来的 患者数据
     * @return 调查问卷表单list
     */
    List<String> getSurveyTwice(JSONObject jsonObject);

    /**
     * 根据前台的数据， 获取处方list
     *
     * @param jsonObject <br/>
     *                   scenesId 场景id <br/>
     *                   userArray 前台传来的 患者数据
     * @return 处方list
     */
    List<String> getPrescriptionTwice(JSONObject jsonObject);

    /**
     * 摧送宣教list
     *
     * @param jsonObject <br/>
     *                   guid患者guid <br>
     *                   scenesId 场景id
     * @return 推送成功
     */
    String publishMission(JSONObject jsonObject);

    /**
     * 摧送调查问卷list
     *
     * @param jsonObject <br/>
     *                   guid患者guid <br>
     *                   scenesId 场景id
     * @return 推送成功
     */
    String publishSurvey(JSONObject jsonObject);

    /**
     * 查询数据库中的 设备列表
     *
     * @return 设备列表
     */
    List<DeviceOfScenes> createDeviceOfScenesListByDevice();


}
