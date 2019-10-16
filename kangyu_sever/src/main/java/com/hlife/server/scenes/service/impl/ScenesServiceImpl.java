package com.hlife.server.scenes.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.framework.config.BusinessConfig;
import com.hlife.framework.util.GuidUtil;
import com.hlife.framework.util.HttpClientUtil;
import com.hlife.framework.util.StringUtil;
import com.hlife.framework.util.WeChatUtil;
import com.hlife.server.program.model.MyFile;
import com.hlife.server.program.service.PrescriptionService;
import com.hlife.server.rpc.service.RpcService;
import com.hlife.server.scenes.constant.ScenesConstant;
import com.hlife.server.scenes.dao.ScenesMapper;
import com.hlife.server.scenes.model.*;
import com.hlife.server.scenes.service.CustomFormTagService;
import com.hlife.server.scenes.service.DeviceOfScenesService;
import com.hlife.server.scenes.service.JudgeStandardService;
import com.hlife.server.scenes.service.ScenesService;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

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
    private PrescriptionService prescriptionService;
    @Autowired
    private RpcService rpcService;

    @Autowired
    private BusinessConfig businessConfig;

    @Override
    public Scenes saveOrEditScenes(Scenes scenes) {
        checkRepeat(scenes);

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
                            .setNode(scenes.getNode())
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
                        .setNode(scenes.getNode())
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
    public List<Scenes> searchScenesListAll(int node) {
        return this.scenesMapper.searchScenesListAll(node);
    }

    @Override
    public PageResult<Scenes> searchScenesListByParams(JSONObject jsonObject) {
        Document queryDoc = new Document();

        String scenesName = jsonObject.getString("scenesName");
        if (StringUtil.stringIsNotNull(scenesName)) {
            queryDoc.append("scenesName", Pattern.compile("^.*" + scenesName + ".*$", Pattern.CASE_INSENSITIVE));
        }

        String node = jsonObject.getString("node");
        if (StringUtil.stringIsNotNull(node)) {
            queryDoc.append("node", Integer.valueOf(node));
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
    public PageResult<DeviceOfScenes> searchDeviceOfScenesListByParam(JSONObject jsonObject) {
        return this.deviceOfScenesService.searchDeviceOfScenesListByParam(jsonObject);
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
        log.debug("userDataUrl ==> {}, userDataPort ==> {}", businessConfig.getUserDataUrl(), businessConfig.getUserDataPort());

        long time = System.currentTimeMillis();
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

        log.debug("time1==>{}", System.currentTimeMillis() - time);

        JSONArray jsonArray = new JSONArray();

        String weChatID = ""; // 微信id

        if (StringUtil.stringIsNotNull(userDataStr)) {
            log.debug("userData ==> {}", userDataStr);

            JSONObject jsonObject = JSON.parseObject(userDataStr);

            jsonArray = jsonObject.getJSONArray("datas");
            if (jsonArray != null && jsonArray.size() > 0) {
                weChatID = jsonArray.getJSONObject(0).getString("weChatID");
            }
        }

        Map<String, Object> resultMap = this.getStringObjectMap(scenesId, jsonArray);
        resultMap.put("userId", guid);
        resultMap.put("weChatID", weChatID);

        log.debug("time5==>{}", System.currentTimeMillis() - time);
        return resultMap;
    }

    @Override
    public List<String> getMission(String guid, String scenesId) {
        List<String> tagIdList = getTagIdList(guid, scenesId);

        return getMassageContent(tagIdList, ScenesConstant.RelatedFormType.MISSION.getKey());
    }

    @Override
    public List<String> getSurvey(String guid, String scenesId) {
        List<String> tagIdList = getTagIdList(guid, scenesId);

        return getMassageContent(tagIdList, ScenesConstant.RelatedFormType.CUSTOM_FORM.getKey());
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
        return getMassageContent(getTagIdObject(jsonObject).fluentPut("type", ScenesConstant.RelatedFormType.MISSION.getKey()));
    }

    @Override
    public List<String> getSurveyTwice(JSONObject jsonObject) {
        return getMassageContent(getTagIdObject(jsonObject).fluentPut("type", ScenesConstant.RelatedFormType.CUSTOM_FORM.getKey()));
    }

    @Override
    public List<MyFile> getPrescriptionTwice(JSONObject jsonObject) {
        List<String> prescriptionIds = this.getMassageContent(getTagIdObject(jsonObject).fluentPut("type", ScenesConstant.RelatedFormType.PRESCRIPTION.getKey()));
        log.debug("prescription==>{}", prescriptionIds);
        if (prescriptionIds.isEmpty()) {
            throw new RuntimeException("没有匹配到处方！");
        }
        return prescriptionService.findPrescriptionPictureList(new JSONObject().fluentPut("idList", prescriptionIds));
    }

    @Override
    public String publishMission(JSONObject jsonObject) {
        log.debug("msgPublishUrl ==> {}, msgPublishPort ==> {}, jsonObject==> {}",
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
        paramObject.put("type", ScenesConstant.RelatedFormType.MISSION.getPushType());

        JSONObject data = new JSONObject();
        data.put("dataGuidList", missionList);
        data.put("title", ScenesConstant.RelatedFormType.MISSION.getTitle());
        data.put("content", ScenesConstant.RelatedFormType.MISSION.getContent());

        this.pushMassage(guid, param.getString("weChatID"), paramObject, data);

        return "推送宣教成功";
    }

    @Override
    public String publishSurvey(JSONObject jsonObject) {
        log.debug("msgPublishUrl ==> {}, msgPublishPort ==> {}, jsonObject==>{}",
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
        paramObject.put("type", ScenesConstant.RelatedFormType.CUSTOM_FORM.getPushType());

        JSONObject data = new JSONObject();
        data.put("dataGuidList", surveyList);
        data.put("title", ScenesConstant.RelatedFormType.CUSTOM_FORM.getTitle());
        data.put("content", ScenesConstant.RelatedFormType.CUSTOM_FORM.getContent());

        this.pushMassage(guid, param.getString("weChatID"), paramObject, data);

        return "推送调查问卷成功";
    }

    public List<DeviceOfScenes> createDeviceOfScenesListByDevice() {
        return deviceOfScenesService.createDeviceOfScenesListByDevice();
    }

    @Override
    public JSONArray getTjspByLabels(JSONObject jsonObject) {
        Map<String, Object> labels = this.getTagAndScoreTwice(jsonObject);
        log.debug("labels==>{}", labels);
        @SuppressWarnings("unchecked")
        List<DeviceResult> resultList = (List<DeviceResult>) labels.get("resultList");
        @SuppressWarnings("unchecked")
        List<DeviceResult> allResultList = (List<DeviceResult>) labels.get("allResultList");
        CustomFormTag customFormTag = this.customFormTagService.selectCustomFormTagById(jsonObject.getString("scenesId"));
        JSONArray jsonArray = new JSONArray()
                .fluentAdd(
                        new JSONObject()
                                .fluentPut("name", customFormTag.getTagName())
                                .fluentPut("value", customFormTag.getTagValue())
                                .fluentPut("essential", true)
                );

        for (DeviceResult deviceResult : allResultList) {
            if (this.filter(deviceResult) && deviceResult.getBase()) {
                jsonArray.add(
                        new JSONObject()
                                .fluentPut("name", customFormTag.getTagName())
                                .fluentPut("value", customFormTag.getTagValue())
                                .fluentPut("essential", true)
                );
            }
        }

        for (DeviceResult deviceResult : resultList) {
            if (this.filter(deviceResult)) {
                jsonArray.add(
                        new JSONObject()
                                .fluentPut("name", customFormTag.getTagName())
                                .fluentPut("value", customFormTag.getTagValue())
                );
            }
        }
        /*JSONArray jsonArray = new JSONArray()
                .fluentAdd(
                        new JSONObject().fluentPut("name", "a")
                                .fluentPut("value", "a")
                ).fluentAdd(
                        new JSONObject().fluentPut("name", "b")
                                .fluentPut("value", "b")
                ).fluentAdd(
                        new JSONObject().fluentPut("name", "c")
                                .fluentPut("value", "c")
                ).fluentAdd(
                        new JSONObject().fluentPut("name", "d")
                                .fluentPut("value", "d")
                );*/
        /*JSONArray jsonArray = new JSONArray()
                .fluentAdd(
                        new JSONObject().fluentPut("name", "绝对骨质")
                                .fluentPut("value", "正常")
                                .fluentPut("weight", "1")
                                .fluentPut("essential", true)
                );*/
        return rpcService.GetTjspByLabels(jsonArray);
    }

    private boolean filter(DeviceResult deviceResult) {
        return Objects.nonNull(deviceResult) && StringUtil.stringIsNotNull(deviceResult.getTagName())
                && StringUtil.stringIsNotNull(deviceResult.getTagValue());
    }

    /**
     * 获取 某设备 计算结果
     *
     * @param resultList 结果 list
     * @param jsonArray  患者数据
     * @param device     某设备
     * @return 设备得分
     */
    /*private double getScore(List<DeviceResult> resultList, JSONArray jsonArray, DeviceOfScenes device) {
        String deviceCode = device.getDeviceCode();
        String deviceType = deviceCode.split("-")[0];

        BiFunction<JSONObject, List<JudgeStandard>, DeviceResult> fun = deviceResultFunMap.get(deviceCode);

        JSONObject datas = new JSONObject();

        for (int i = 0, size = jsonArray.size(); i < size; i++) {
            JSONObject data = jsonArray.getJSONObject(i);
            String dataType = data.getString("dataType");
            String dataType0 = dataType.split("-")[0];
            log.debug("dataType ==> {}", dataType);

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
    }*/
    private double getScore(List<DeviceResult> resultList, JSONArray jsonArray, DeviceOfScenes device) {

        List<JudgeStandard> judgeStandardList = device.getJudgeStandardList();
        if (judgeStandardList == null || judgeStandardList.isEmpty()) {
            throw new RuntimeException("请正确维护场景-设备（" + device.getDeviceName() + "）-规则");
        }

        String deviceCode = device.getDeviceCode();
        String deviceType = deviceCode.split("-")[0];

        String deviceName = device.getDeviceName();
        boolean base = Optional.ofNullable(device.getBase()).orElse(false);

        String scenesFunKey = device.getScenesFunKey();
        scenesFunKey = StringUtil.stringIsNotNull(scenesFunKey)
                ? scenesFunKey : ScenesConstant.ScenesFun.COMMON_PARAMETER.getKey();
        ScenesConstant.ScenesFun scenesFun = ScenesConstant.ScenesFun.getInstance(scenesFunKey);

        JSONObject data = new JSONObject();

        JSONObject datas = new JSONObject();

        for (int i = 0, size = jsonArray.size(); i < size; i++) {
            data = jsonArray.getJSONObject(i);
            String dataType = data.getString("dataType");
            String dataType0 = dataType.split("-")[0];
            log.debug("dataType ==> {}", dataType);

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
                break;
            }
        }

        if (datas.isEmpty()) {
            /*JudgeStandard defaultJudgeStandard = judgeStandardList.stream()
                    .filter(judgeStandard -> judgeStandard.getScore() == 0d)
                    .findFirst().orElse(judgeStandardList.get(0));*/
            resultList.add(
                    new DeviceResult()
                            .setDataType(deviceCode)
                            .setBase(base)
                            /*.setTagId(defaultJudgeStandard.getTagId())
                            .setTagName(defaultJudgeStandard.getTagName())
                            .setTagValue(defaultJudgeStandard.getTagValue())*/
                            .setDatas(new DeviceResult.Record().setChineseDescription(deviceName))
            );
            return 0d;
        }

        DeviceResult.Record record;

        if (datas.containsKey("twiceValue")) {
            record = JSON.toJavaObject(datas, DeviceResult.Record.class);
            jsonArray.remove(data);
        } else {
            BiFunction<JSONObject, Device.FieldPath[], DeviceResult.Record> funFlattenedData = scenesFun.getFunFlattenedData();
            record = funFlattenedData
                    .apply(datas, device.getFieldPaths())
                    .setChineseDescription(deviceName);
        }

        BiFunction<List<JudgeStandard>, String, DeviceResult> funDeviceResult = scenesFun.getFunDeviceResult();

        DeviceResult deviceResult =
                funDeviceResult.apply(
                        judgeStandardList,
                        record.getTwiceValue()
                );

        if (deviceResult != null) {
            resultList.add(
                    deviceResult.setDatas(record)
                            .setBase(base)
                            .setDataType(deviceCode)
            );
            return deviceResult.getScore() * device.getWeights();
        }
        resultList.add(new DeviceResult());
        return 0;
    }

    /**
     * 根据场景 id 患者数据获取返回结果
     *
     * @param scenesId  场景id
     * @param jsonArray 患都数据
     * @return 计算结果
     */
    private Map<String, Object> getStringObjectMap(String scenesId, JSONArray jsonArray) {
        long time = System.currentTimeMillis();
        List<DeviceOfScenes> deviceList = this.getDeviceOfScenes(scenesId);
        log.debug("time2 ==> {}", System.currentTimeMillis() - time);

        Map<String, Object> resultMap = new HashMap<>();

        List<DeviceResult> resultList = new ArrayList<>();

        double score = 0d;

        long time2 = System.currentTimeMillis();
        for (DeviceOfScenes device : deviceList) {
            String deviceCode = device.getDeviceCode();
            if (StringUtil.stringIsNull(deviceCode)) {
                continue;
            }
            score += getScore(resultList, jsonArray, device);
        }

        log.debug("time3 ==> {}", System.currentTimeMillis() - time2);

        if (resultList.isEmpty()) {
            throw new RuntimeException("暂无数据");
        }

        long time3 = System.currentTimeMillis();
        List<DeviceResult> tagList = new ArrayList<>();

        /*int resultListSize = resultList.size();
        if (resultListSize < 4) {
            tagList = resultList;
        } else {
            for (int i = 0; i < 3; i++) {
                tagList.add(resultList.get(i));
            }
        }*/
        int y = 0;
        for (DeviceResult deviceResult : resultList) {
            if (deviceResult != null && deviceResult.isNotBase() && StringUtil.stringIsNotNull(deviceResult.getTagId())) {
                tagList.add(deviceResult);
                y++;
            }
            if (y >= 3) {
                break;
            }
        }

        if (tagList.size() > 0) {
            for (DeviceResult deviceResult : tagList) {
                if (deviceResult == null) {
                    continue;
                }

                deviceResult.setTagRemark(
                        this.customFormTagService.selectCustomFormTagById(deviceResult.getTagId())
                                .getTagRemark()
                );
            }
        }


        log.debug("time4 ==> {}", System.currentTimeMillis() - time3);

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

        log.debug("deviceList ==> {}", JSON.toJSONString(deviceList));

        // 设备排序
        deviceList.sort((device1, device2) -> {
            if (device1.getWeights().compareTo(device2.getWeights()) != 0) {
                return device2.getWeights().compareTo(device1.getWeights());
            }
            return device1.getPriority().compareTo(device2.getPriority());
        });

        log.debug("deviceListSort ==> {}", JSON.toJSONString(deviceList));
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
        //List<String> tagIdList = getTagIdList(guid, scenesId, param);
        JSONObject jsonObject = this.getTagIdObject(guid, scenesId, param);

        if (StringUtil.stringIsNull(param.getString("weChatID"))) {
            throw new RuntimeException("该用户没有绑定微信");
        }

        return getMassageContent(jsonObject.fluentPut("type", ScenesConstant.RelatedFormType.MISSION.getKey()));
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
        //List<String> tagIdList = getTagIdList(guid, scenesId, param);
        JSONObject jsonObject = this.getTagIdObject(guid, scenesId, param);

        if (StringUtil.stringIsNull(param.getString("weChatID"))) {
            throw new RuntimeException("该用户没有绑定微信");
        }

        // return getMassageContent(tagIdList, ScenesConstant.RelatedFormType.CUSTOM_FORM.getKey());
        return getMassageContent(jsonObject.fluentPut("type", ScenesConstant.RelatedFormType.CUSTOM_FORM.getKey()));
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

        @SuppressWarnings("unchecked")
        List<DeviceResult> resultList = (List<DeviceResult>) resultMap.get("resultList");
        /*@SuppressWarnings("unchecked")
        List<DeviceResult> allResultList = (List<DeviceResult>) resultMap.get("allResultList");*/

        param.put("weChatID", resultMap.get("weChatID"));

        List<String> tagIdList = new ArrayList<>();
        tagIdList.add(scenesId);
        /*for (DeviceResult deviceResult : allResultList) {
            if (deviceResult != null && deviceResult.getBase() && StringUtil.stringIsNotNull(deviceResult.getTagId())) {
                tagIdList.add(deviceResult.getTagId());
            }

        }*/

        for (DeviceResult deviceResult : resultList) {
            if (deviceResult != null && StringUtil.stringIsNotNull(deviceResult.getTagId())) {
                tagIdList.add(deviceResult.getTagId());
            }
        }
        return tagIdList;
    }

    private JSONObject getTagIdObject(String guid, String scenesId, JSONObject param) {
        Map<String, Object> resultMap = getTagAndScore(guid, scenesId);

        @SuppressWarnings("unchecked")
        List<DeviceResult> resultList = (List<DeviceResult>) resultMap.get("resultList");
        @SuppressWarnings("unchecked")
        List<DeviceResult> allResultList = (List<DeviceResult>) resultMap.get("allResultList");

        param.put("weChatID", resultMap.get("weChatID"));

        JSONArray tagIdList = new JSONArray(), baseTagIdList = new JSONArray();
        tagIdList.add(scenesId);

        for (DeviceResult deviceResult : resultList) {
            if (deviceResult != null && StringUtil.stringIsNotNull(deviceResult.getTagId())) {
                tagIdList.add(deviceResult.getTagId());
            }
        }

        for (DeviceResult deviceResult : allResultList) {
            if (deviceResult != null && StringUtil.stringIsNotNull(deviceResult.getTagId())) {
                baseTagIdList.add(deviceResult.getTagId());
            }
        }

        return new JSONObject().fluentPut("tagIdList", tagIdList)
                .fluentPut("baseTagIdList", baseTagIdList)
                .fluentPut("scenesId", scenesId);
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

        @SuppressWarnings("unchecked")
        List<DeviceResult> resultList = (List<DeviceResult>) twiceResultMap.get("resultList");

        List<String> tagIdList = new ArrayList<>();
        tagIdList.add(jsonObject.getString("scenesId"));
        for (DeviceResult deviceResult : resultList) {
            if (deviceResult != null && StringUtil.stringIsNotNull(deviceResult.getTagId())) {
                tagIdList.add(deviceResult.getTagId());
            }
        }
        return tagIdList;
    }

    private JSONObject getTagIdObject(JSONObject jsonObject) {
        Map<String, Object> twiceResultMap = this.getTagAndScoreTwice(jsonObject);

        @SuppressWarnings("unchecked")
        List<DeviceResult> resultList = (List<DeviceResult>) twiceResultMap.get("resultList");
        JSONArray tagIdList = new JSONArray();
        for (DeviceResult deviceResult : resultList) {
            if (deviceResult != null && StringUtil.stringIsNotNull(deviceResult.getTagId())) {
                tagIdList.add(deviceResult.getTagId());
            }
        }
        @SuppressWarnings("unchecked")
        List<DeviceResult> allResultList = (List<DeviceResult>) twiceResultMap.get("allResultList");
        JSONArray baseTagIdList = new JSONArray();
        for (DeviceResult deviceResult : allResultList) {
            if (deviceResult != null && deviceResult.getBase() && StringUtil.stringIsNotNull(deviceResult.getTagId())) {
                baseTagIdList.add(deviceResult.getTagId());
            }
        }

        return new JSONObject().fluentPut("tagIdList", tagIdList)
                .fluentPut("baseTagIdList", baseTagIdList)
                .fluentPut("scenesId", jsonObject.getString("scenesId"));
    }

    /**
     * 根据场景计算结果摧送消息
     *
     * @param tagIdList 计算结果
     */
    private List<String> getMassageContent(List<String> tagIdList, String type) {
        List<String> formIdList = this.customFormTagService.selectCustomFormIdsByTagIdList(tagIdList, type);
        log.debug("formIdList:{}", formIdList);

        if (formIdList == null || formIdList.isEmpty()) {
            return Collections.emptyList();
        }

        return formIdList;
    }

    /**
     * 根据场景计算结果摧送消息
     *
     * @param jsonObject 计算结果
     */
    private List<String> getMassageContent(JSONObject jsonObject) {
        List<String> formIdList = this.customFormTagService.selectCustomFormIdsForMassageContent(jsonObject);
        log.debug("formIdList:{}", formIdList);

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


    /**
     * 重复验证
     *
     * @param scenes
     */
    private void checkRepeat(Scenes scenes) {
        Document queryDoc = new Document();
        String scenesId = scenes.getScenesId();
        Integer node = scenes.getNode();

        if (node != null) {
            queryDoc.put("node", node);
        }

        if (StringUtil.stringIsNotNull(scenesId)) {
            queryDoc.put("scenesId", new Document("$ne", scenesId));
        }

        queryDoc.put("scenesName", scenes.getScenesName());
        if (this.scenesMapper.isExists(queryDoc)) {
            throw new RuntimeException("场景名称重复");
        }

        queryDoc.remove("scenesName");

        queryDoc.put("scenesCode", scenes.getScenesCode());
        if (this.scenesMapper.isExists(queryDoc)) {
            throw new RuntimeException("场景代码重复");
        }
    }
}
