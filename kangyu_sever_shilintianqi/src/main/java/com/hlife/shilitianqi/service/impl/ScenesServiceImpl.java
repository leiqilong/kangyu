package com.hlife.shilitianqi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.framework.util.GuidUtil;
import com.hlife.framework.util.HttpClientUtil;
import com.hlife.framework.util.StringUtil;
import com.hlife.framework.util.WeChatUtil;
import com.hlife.shilitianqi.business_config.BusinessConfig;
import com.hlife.shilitianqi.constant.Constant;
import com.hlife.shilitianqi.dao.ScenesMapper;
import com.hlife.shilitianqi.model.*;
import com.hlife.shilitianqi.service.CustomFormTagService;
import com.hlife.shilitianqi.service.DeviceOfScenesService;
import com.hlife.shilitianqi.service.JudgeStandardService;
import com.hlife.shilitianqi.service.ScenesService;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiFunction;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 场景服务层实现
 */
@Slf4j
@Service
public class ScenesServiceImpl implements ScenesService {

    @Autowired
    private ScenesMapper scenesMapper;
    @Autowired
    private DeviceOfScenesService deviceOfScenesService;
    @Autowired
    private JudgeStandardService judgeStandardService;
    @Autowired
    private CustomFormTagService customFormTagService;

    @Autowired
    private BusinessConfig businessConfig;

    @Autowired
    private Map<String, BiFunction<JSONObject, List<JudgeStandard>, DeviceResult>> deviceResultFunMap;

    @Override
    public Scenes saveOrEditScenes(Scenes scenes) {
        String scenesId = scenes.getScenesId();
        if (StringUtil.stringIsNull(scenesId)) {
            String guid = GuidUtil.generateGuid();
            scenes.setScenesId(guid);
            customFormTagService.addOrEditCustomFormTagSelf(
                    new CustomFormTag()
                            .setId(guid)
                            .setTagName("场景")
                            .setTagValue(scenes.getScenesName())
                            .setTagType("05")
                            .setCreateTime(new Date())
            );
            return this.scenesMapper.saveScenes(scenes);
        }

        if (!this.scenesMapper.isExists(new Document("scenesId", scenesId))) {
            throw new RuntimeException("该数据不存在！");
        }

        customFormTagService.addOrEditCustomFormTagSelf(
                new CustomFormTag()
                        .setId(scenes.getScenesId())
                        .setTagName("场景")
                        .setTagValue(scenes.getScenesName())
                        .setTagType("05")
                        .setCreateTime(new Date())
        );

        return this.scenesMapper.updateScenes(scenes);
    }

    @Override
    public Long deleteScenesById(String scenesId) {
        if (!this.scenesMapper.isExists(new Document("scenesId", scenesId))) {
            throw new RuntimeException("数据不存在");
        }

        this.deviceOfScenesService.deleteByScenesId(scenesId);

        this.customFormTagService.deleteCustomFormTagById(scenesId);

        return this.scenesMapper.deleteScenesById(scenesId);
    }

    @Override
    public List<Scenes> searchScenesListAll() {
        return this.scenesMapper.searchScenesListAll();
    }

    @Override
    public PageResult<Scenes> searchScenesListByParams(JSONObject jsonObject) {
        Document queryDoc = new Document();

        String scenesName = jsonObject.getString("scenesName");
        if (StringUtil.stringIsNotNull(scenesName)) {
            queryDoc.append("scenesName", Pattern.compile("^.*" + scenesName + ".*$", Pattern.CASE_INSENSITIVE));
        }

        PageParam pageParam = new PageParam(jsonObject.getInteger(PageParam.PAGE_SIZE),
                jsonObject.getInteger(PageParam.PAGE_NUM));
        return this.scenesMapper.searchScenesListByParams(queryDoc, pageParam);
    }

    @Override
    public DeviceOfScenes saveOrEditDeviceOfScenes(DeviceOfScenes deviceOfScenes) {
        return this.deviceOfScenesService.saveOrEditDeviceOfScenes(deviceOfScenes);
    }

    @Override
    public List<DeviceOfScenes> searchDeviceOfScenesList(String scenesId) {
        return this.deviceOfScenesService.searchDeviceOfScenesList(scenesId);
    }

    @Override
    public Long deleteDeviceOfScenes(String deviceOfScenesId) {
        return this.deviceOfScenesService.deleteByDeviceOfScenesId(deviceOfScenesId);
    }

    @Override
    public JudgeStandard saveOrEditJudgeStandard(JudgeStandard judgeStandard) {
        return this.judgeStandardService.saveOrEditJudgeStandard(judgeStandard);
    }

    @Override
    public List<JudgeStandard> searchJudgeStandardList(String deviceOfScenesId) {
        return this.judgeStandardService.searchJudgeStandardList(deviceOfScenesId);
    }

    @Override
    public Long deleteJudgeStandard(String judgeStandardId) {
        return this.judgeStandardService.deleteJudgeStandardByJudgeStandardId(judgeStandardId);
    }

    @Override
    public Map<String, Object> getTagAndScore(String guid, String scenesId) {
        log.info("userDataUrl ==> {}, userDataPort ==> {}", businessConfig.getUserDataUrl(), businessConfig.getUserDataPort());

        // 远程获取当前人所额设备数据
        String userDataStr =
                HttpClientUtil.doPost(
                        String.format(
                                HttpClientUtil.HTTP_URL_FORMAT,
                                businessConfig.getUserDataUrl(),
                                businessConfig.getUserDataPort(),
                                "api/LatestData/All"
                        ),
                        JSON.toJSONString(new JSONObject().fluentPut("queryID", guid))
                );

        JSONArray jsonArray = new JSONArray();

        String weChatID = ""; // 微信id

        if (StringUtil.stringIsNotNull(userDataStr)) {
            log.info("userData ==> {}", userDataStr);

            JSONObject jsonObject = JSON.parseObject(userDataStr);

            jsonArray = jsonObject.getJSONArray("datas");
            if (jsonArray != null && jsonArray.size() > 0) {
                weChatID = jsonArray.getJSONObject(0).getString("weChatID");
            }
        }

        Map<String, Object> resultMap = this.getStringObjectMap(scenesId, jsonArray);
        resultMap.put("userId", guid);
        resultMap.put("weChatID", weChatID);

        return resultMap;
    }

    @Override
    public List<String> getMission(String guid, String scenesId) {
        List<String> tagIdList = getTagIdList(guid, scenesId);

        return getMassageContent(tagIdList, Constant.RelatedFormType.MISSION.getKey());
    }

    @Override
    public List<String> getSurvey(String guid, String scenesId) {
        List<String> tagIdList = getTagIdList(guid, scenesId);

        return getMassageContent(tagIdList, Constant.RelatedFormType.CUSTOM_FORM.getKey());
    }


    @Override
    public List<CustomFormTag> getCustomFormTagList(JSONObject jsonObject) {
        return customFormTagService.getCustomFormTagList(jsonObject);
    }

    @Override
    public Map<String, Object> getTagAndScoreTwice(JSONObject jsonObject) {
        String scenesId = jsonObject.getString("scenesId");
        if (StringUtil.stringIsNull(scenesId)) {
            throw new IllegalArgumentException("传入场景scenesId为空");
        }
        JSONArray userArray = jsonObject.getJSONArray("userArray");
        return this.getStringObjectMap(scenesId, userArray);
    }

    @Override
    public List<String> getMissionTwice(JSONObject jsonObject) {
        return getMassageContent(getTagIdListTwice(jsonObject), Constant.RelatedFormType.MISSION.getKey());
    }

    @Override
    public List<String> getSurveyTwice(JSONObject jsonObject) {
        return getMassageContent(getTagIdListTwice(jsonObject), Constant.RelatedFormType.CUSTOM_FORM.getKey());
    }

    @Override
    public String publishMission(JSONObject jsonObject) {
        log.info("msgPublishUrl ==> {}, msgPublishPort ==> {}, jsonObject==>{}",
                businessConfig.getMsgPublishUrl(),
                businessConfig.getMsgPublishPort(),
                jsonObject
        );

        String guid = jsonObject.getString("guid");

        String scenesId = jsonObject.getString("sceneid");

        JSONObject param = new JSONObject();

        List missionList = this.getMission(guid, scenesId, param);

        if (missionList == null || missionList.isEmpty()) {
            throw new RuntimeException("没有查到相应的宣教内容");
        }

        JSONObject paramObject = new JSONObject();
        paramObject.put("type", Constant.RelatedFormType.MISSION.getPushType());

        JSONObject data = new JSONObject();
        data.put("dataGuidList", missionList);
        data.put("title", Constant.RelatedFormType.MISSION.getTitle());
        data.put("content", Constant.RelatedFormType.MISSION.getContent());

        this.pushMassage(guid, param.getString("weChatID"), paramObject, data);

        return "推送宣教成功";
    }

    @Override
    public String publishSurvey(JSONObject jsonObject) {
        log.info("msgPublishUrl ==> {}, msgPublishPort ==> {}, jsonObject==>{}",
                businessConfig.getMsgPublishUrl(),
                businessConfig.getMsgPublishPort(),
                jsonObject
        );

        String guid = jsonObject.getString("guid");
        String scenesId = jsonObject.getString("sceneid");

        JSONObject param = new JSONObject();
        List surveyList = this.getSurvey(guid, scenesId, param);

        if (surveyList == null || surveyList.isEmpty()) {
            throw new RuntimeException("没有查到相应的调查问卷");
        }

        JSONObject paramObject = new JSONObject();
        paramObject.put("type", Constant.RelatedFormType.CUSTOM_FORM.getPushType());

        JSONObject data = new JSONObject();
        data.put("dataGuidList", surveyList);
        data.put("title", Constant.RelatedFormType.CUSTOM_FORM.getTitle());
        data.put("content", Constant.RelatedFormType.CUSTOM_FORM.getContent());

        this.pushMassage(guid, param.getString("weChatID"), paramObject, data);

        return "推送调查问卷成功";
    }

    /**
     * 获取 某设备 计算结果
     *
     * @param resultList 结果 list
     * @param jsonArray  患者数据
     * @param device     某设备
     * @return 设备得分
     */
    private double getScore(List<DeviceResult> resultList, JSONArray jsonArray, DeviceOfScenes device) {
        String deviceCode = device.getDeviceCode();
        String deviceType = deviceCode.split("-")[0];

        BiFunction<JSONObject, List<JudgeStandard>, DeviceResult> fun = deviceResultFunMap.get(deviceCode);

        JSONObject datas = new JSONObject();

        for (int i = 0, size = jsonArray.size(); i < size; i++) {
            JSONObject data = jsonArray.getJSONObject(i);
            String dataType = data.getString("dataType");
            String dataType0 = dataType.split("-")[0];
            log.info("dataType ==> {}", dataType);

            if (deviceCode.equals(dataType)) {
                datas = data.getJSONObject("datas");
                break;
            }

            if (deviceType.equals(dataType)) {
                datas = data.getJSONObject("datas");
                break;
            }

            if (deviceType.equals(dataType0)) {
                datas = data.getJSONObject("datas");
            }
        }

        if (fun == null) {
            resultList.add(new DeviceResult().setDataType(deviceCode));
            return 100d * device.getWeights();
        }

        DeviceResult deviceResult = fun.apply(datas, device.getJudgeStandardList());
        if (deviceResult != null) {
            resultList.add(deviceResult.setDataType(deviceCode));
            return deviceResult.getScore() * device.getWeights();
        }
        resultList.add(deviceResult.setDataType(deviceCode));
        return deviceResult.getScore() * device.getWeights();
    }



    /**
     * 根据场景 id 患都数据获取返回结果
     *
     * @param scenesId  场景id
     * @param jsonArray 患都数据
     * @return 计算结果
     */
    private Map<String, Object> getStringObjectMap(String scenesId, JSONArray jsonArray) {
        List<DeviceOfScenes> deviceList = getDeviceOfScenes(scenesId);

        Map<String, Object> resultMap = new HashMap<>();

        List<DeviceResult> resultList = new ArrayList<>();

        double score = 0d;

        for (DeviceOfScenes device : deviceList) {
            String deviceCode = device.getDeviceCode();
            if (StringUtil.stringIsNull(deviceCode)) {
                continue;
            }
            score += getScore(resultList, jsonArray, device);
        }

        if (resultList.isEmpty()) {
            throw new RuntimeException("暂无数据");
        }

        List<DeviceResult> tagList = new ArrayList<>();

        int resultListSize = resultList.size();
        if (resultListSize < 4) {
            tagList = resultList;
        } else {
            for (int i = 0; i < 3; i++) {
                tagList.add(resultList.get(i));
            }
        }

        for (DeviceResult deviceResult : tagList) {
            if (deviceResult != null && StringUtil.stringIsNotNull(deviceResult.getTagId())) {
                deviceResult.setTagRemark(
                        this.customFormTagService.selectCustomFormTagById(deviceResult.getTagId())
                                .getTagRemark()
                );
            }

        }

        resultMap.put("resultList", tagList);
        resultMap.put("allResultList", resultList);
        resultMap.put("score", score);

        return resultMap;
    }

    /**
     * 获取排序后的 场景设备列表
     *
     * @param scenesId 场景 id
     * @return 场景设备列表
     */
    private List<DeviceOfScenes> getDeviceOfScenes(String scenesId) {
        // 获取场景设备规则
        List<DeviceOfScenes> deviceList = this.deviceOfScenesService.searchDeviceOfScenesWithJudgeStandardList(scenesId);

        if (deviceList == null || deviceList.isEmpty()) {
            throw new RuntimeException("请正确维护场景信息！");
        }

        log.info("deviceList ==> {}", JSON.toJSONString(deviceList));

        // 设备排序
        deviceList.sort((device1, device2) -> {
            if (device1.getWeights().compareTo(device2.getWeights()) != 0) {
                return device2.getWeights().compareTo(device1.getWeights());
            }
            return device1.getPriority().compareTo(device2.getPriority());
        });

        log.info("deviceListSort ==> {}", JSON.toJSONString(deviceList));
        return deviceList;
    }

    /**
     * 获取宣教list 并给微信id 赋值
     *
     * @param guid     并给微信id 赋值
     * @param scenesId 场景id
     * @param param    微信id
     * @return 宣教list
     */
    private List<String> getMission(String guid, String scenesId, JSONObject param) {
        List<String> tagIdList = getTagIdList(guid, scenesId, param);

        if (StringUtil.stringIsNull(param.getString("weChatID"))) {
            throw new RuntimeException("该用户没有绑定微信");
        }

        return getMassageContent(tagIdList, Constant.RelatedFormType.MISSION.getKey());
    }

    /**
     * 获取调查问卷list 并给微信id 赋值
     *
     * @param guid     并给微信id 赋值
     * @param scenesId 场景id
     * @param param    微信id
     * @return 调查问卷list
     */
    private List getSurvey(String guid, String scenesId, JSONObject param) {
        List<String> tagIdList = getTagIdList(guid, scenesId, param);

        if (StringUtil.stringIsNull(param.getString("weChatID"))) {
            throw new RuntimeException("该用户没有绑定微信");
        }

        return getMassageContent(tagIdList, Constant.RelatedFormType.CUSTOM_FORM.getKey());
    }

    /**
     * 获取标签 id List
     *
     * @param guid     患者id
     * @param scenesId 场景id
     * @return 标签 id List
     */
    private List<String> getTagIdList(String guid, String scenesId) {
        return this.getTagIdList(guid, scenesId, new JSONObject());
    }

    /**
     * 获取标签 id List 重载方法
     *
     * @param guid     患者id
     * @param scenesId 场景id
     * @return 标签 id List
     */
    private List<String> getTagIdList(String guid, String scenesId, JSONObject param) {
        Map<String, Object> resultMap = getTagAndScore(guid, scenesId);
        List<DeviceResult> resultList = (List<DeviceResult>) resultMap.get("resultList");

        param.put("weChatID", resultMap.get("weChatID"));

        List<String> tagIdList = new ArrayList<>();
        tagIdList.add(scenesId);
        for (DeviceResult deviceResult : resultList) {
            tagIdList.add(deviceResult.getTagId());
        }
        return tagIdList;
    }

    /**
     * 根据前台传来的数据获取取标签列表
     *
     * @param jsonObject 前台数据
     *                   scenesId 场景id
     *                   userArray 前台传来的 患者数据
     * @return
     */
    private List<String> getTagIdListTwice(JSONObject jsonObject) {
        Map<String, Object> twiceResultMap = this.getTagAndScoreTwice(jsonObject);

        List<DeviceResult> resultList = (List<DeviceResult>) twiceResultMap.get("resultList");

        List<String> tagIdList = new ArrayList<>();
        tagIdList.add(jsonObject.getString("scenesId"));
        for (DeviceResult deviceResult : resultList) {
            tagIdList.add(deviceResult.getTagId());
        }
        return tagIdList;
    }

    /**
     * 根据场景计算结果摧送消息
     *
     * @param tagIdList 计算结果
     */
    private List<String> getMassageContent(List<String> tagIdList, String type) {
        List<String> formIdList = this.customFormTagService.selectCustomFormIdsByTagIdList(tagIdList, type);
        log.info("formIdList:{}", formIdList);

        if (formIdList == null || formIdList.isEmpty()) {
            return Collections.emptyList();
        }

        return formIdList;
    }

    /**
     * 推送模版消息
     *
     * @param guid        患者guid
     * @param weChatID    患者微信id
     * @param paramObject 消息 jsonObject
     * @param data        消息具体信息
     */
    private void pushMassage(String guid, String weChatID, JSONObject paramObject, JSONObject data) {
        String url = String.format(
                HttpClientUtil.HTTP_URL_FORMAT,
                businessConfig.getMsgPublishUrl(),
                businessConfig.getMsgPublishPort(),
                "wpa/msg/sendPubMsg"
        );

        if (!WeChatUtil.pushMassage(guid, weChatID, url, paramObject, data)) {
            throw new RuntimeException("推送消息失败");
        }
    }
}
