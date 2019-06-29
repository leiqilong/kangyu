package com.hlife.shilitianqi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.framework.util.GuidUtil;
import com.hlife.framework.util.HttpClientUtil;
import com.hlife.framework.util.StringUtil;
import com.hlife.shilitianqi.business_config.BusinessConfig;
import com.hlife.shilitianqi.dao.ScenesMapper;
import com.hlife.shilitianqi.handler.devicehandler.DeviceHandler;
import com.hlife.shilitianqi.model.*;
import com.hlife.shilitianqi.service.CustomFormTagService;
import com.hlife.shilitianqi.service.DeviceOfScenesService;
import com.hlife.shilitianqi.service.JudgeStandardService;
import com.hlife.shilitianqi.service.ScenesService;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private final Map<String, BiFunction<JSONObject, List<JudgeStandard>, DeviceResult>> deviceResultFunMap
            = new HashMap<String, BiFunction<JSONObject, List<JudgeStandard>, DeviceResult>>() {{
        // 手环-运动 （step）
        this.put("ky.stl.Band.steps", DeviceHandler::bandStepDispart);
        // 手环-血压
        this.put("ky.stl.Band.bloodpressure", DeviceHandler::bandBloodpressureDispart);
        // 手环-血氧
        this.put("ky.stl.Band.bloodoxygen", DeviceHandler::bandBloodoxygenDispart);
        // 手环-心率
        this.put("ky.stl.Band.heartRate", DeviceHandler::bandheartRateDispart);
        // 手环-睡眠
        this.put("ky.stl.Band.sleep", DeviceHandler::bandSleepDispart);
        // 报告单-体成分
        this.put("ky.stl.bgd.tcf", DeviceHandler::bgdTcfDispart);
        // 水杯
        this.put("ky.stl.Water", DeviceHandler::waterDispart);
        // 量表-(睡眼)
        this.put("ky.stl.diagnose-sleepTime", DeviceHandler::diagnose_sleepTimeDispart);
        // 档案-bmi (量表-体重/身高）
        this.put("ky.stl.diagnose-wh", DeviceHandler::diagnose_whDispart);
        // 量表-（运动）
        this.put("ky.stl.diagnose-outdoor", DeviceHandler::diagnose_outdoorDispart);
        // 量表-（心率）
        this.put("ky.stl.diagnose-heart", DeviceHandler::diagnose_heartDispart);
        // 膳食营养-（能量）
        this.put("ky.stl.cf.tjsp.ld-Energy", DeviceHandler::cfTjspLd_energDispart);
        // 膳食营养-（蛋白质）
        this.put("ky.stl.cf.tjsp.ld-Protein", DeviceHandler::cfTjspLd_proteinDispart);
        // 膳食营养-（脂肪）
        this.put("ky.stl.cf.tjsp.ld-Fat", DeviceHandler::cfTjspLd_fatDispart);
        // 膳食营养-（碳水化合物）
        this.put("ky.stl.cf.tjsp.ld-Cho", DeviceHandler::cfTjspLd_choDispart);
        // 儿童健康检查-运动
        this.put("ky.stl.form.Y3D5YA9ZUQNW11IXEALFHQ2JKL6ERH1C-jk_tyhd", DeviceHandler::form_childrenHeathTyhd);
        // 儿童健康检查-饮水量
        this.put("ky.stl.form.Y3D5YA9ZUQNW11IXEALFHQ2JKL6ERH1C-jk_ysl", DeviceHandler::form_childrenHeathYsl);
        // 儿童健康检查-睡眠
        this.put("ky.stl.form.Y3D5YA9ZUQNW11IXEALFHQ2JKL6ERH1C-jk_smsj", DeviceHandler::form_childrenHeathSmsj);
        // 儿童健康检查-心率
        this.put("ky.stl.form.Y3D5YA9ZUQNW11IXEALFHQ2JKL6ERH1C-jk_xl", DeviceHandler::form_childrenHeathXl);
    }};

   /* @Value("${user-data.url}")
    private String url;
    @Value("${user-data.port}")
    private String port;*/

    @Override
    public Scenes saveOrEditScenes(Scenes scenes) {
        String scenesId = scenes.getScenesId();
        if (StringUtil.stringIsNull(scenesId)) {
            String guid = GuidUtil.generateGuid();
            scenes.setScenesId(guid);
            customFormTagService.addOrEditCustomFormTagSelf(
                    new CustomFormTag()
                            .setId(guid)
                            //.setTagCode(scenes.getScenesCode())
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
            weChatID = jsonObject.getString("weChatID");
        }

        Map<String, Object> resultMap = this.getStringObjectMap(scenesId, jsonArray);
        resultMap.put("userId", guid);
        resultMap.put("weChatID", weChatID);

        return resultMap;
    }

    @Override
    public List<String> getMission(String guid, String scenesId) {
        List<String> tagIdList = getTagIdList(guid, scenesId);

        tagIdList.add("1143454014872510464"); // 类型宣教

        return pushMassage(tagIdList);
    }

    @Override
    public List<String> getSurvey(String guid, String scenesId) {

        List<String> tagIdList = getTagIdList(guid, scenesId);

        tagIdList.add("1143454297266610176"); // 类型调查问卷

        return pushMassage(tagIdList);
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
    public String publishMission(JSONObject jsonObject) {
        log.info("msgPublishUrl ==> {}, msgPublishPort ==> {}", businessConfig.getMsgPublishUrl(), businessConfig.getMsgPublishPort());

        String guid = jsonObject.getString("guid");

        String scenesId = jsonObject.getString("sceneId");

        Map<String, Object> resultMap = getTagAndScore(guid, scenesId);

        String weChatID = (String) resultMap.get("weChatID");

        if (StringUtil.stringIsNull(weChatID)) {
            throw new RuntimeException("用户没有绑定微信");
        }

        List<DeviceResult> resultList = (List<DeviceResult>) resultMap.get("resultList");

        List<String> tagIdList = new ArrayList<>();
        tagIdList.add(scenesId);
        for (DeviceResult deviceResult : resultList) {
            tagIdList.add(deviceResult.getTagId());
        }

        /*String weChatID = "";

        List<String> tagIdList = this.getTagIdList(guid, scenesId, weChatID);

        if (StringUtil.stringIsNull(weChatID)) {
            throw new RuntimeException("用户没有绑定微信");
        }*/

        tagIdList.add("1143454014872510464"); // 类型调查问卷

        List missionList = this.pushMassage(tagIdList);

        if (missionList == null || missionList.isEmpty()) {
            throw new RuntimeException("没有查到相应的调查问卷");
        }

        JSONObject paramObject = new JSONObject();
        paramObject.put("type", "pdAndEduTagPush");

        JSONObject data = new JSONObject();
        data.put("dataGuidList", missionList);
        data.put("title", "宣教");
        data.put("content", "请点击详情完成宣教");

        JSONObject userObject = new JSONObject();
        userObject.put("Weixinid", weChatID);
        userObject.put("guid", guid);

        JSONArray userList = new JSONArray();
        userList.add(userObject);

        data.put("userList", userList);

        paramObject.put("data", data);

        String res =
                HttpClientUtil.doPost(
                        String.format(
                                HttpClientUtil.HTTP_URL_FORMAT,
                                businessConfig.getMsgPublishUrl(),
                                businessConfig.getMsgPublishPort(),
                                "wpa/msg/sendPubMsg"
                        ),
                        JSON.toJSONString(paramObject)
                );

        log.info("res ==> {}", res);

        return "推送宣教成功";
    }

    @Override
    public String publishSurvey(JSONObject jsonObject) {
        log.info("msgPublishUrl ==> {}, msgPublishPort ==> {}", businessConfig.getMsgPublishUrl(), businessConfig.getMsgPublishPort());

        String guid = jsonObject.getString("guid");
        String scenesId = jsonObject.getString("sceneId");

        Map<String, Object> resultMap = getTagAndScore(guid, scenesId);

        String weChatID = (String) resultMap.get("weChatID");

        if (StringUtil.stringIsNull(weChatID)) {
            throw new RuntimeException("用户没有绑定微信");
        }

        List<DeviceResult> resultList = (List<DeviceResult>) resultMap.get("resultList");

        List<String> tagIdList = new ArrayList<>();
        tagIdList.add(scenesId);
        for (DeviceResult deviceResult : resultList) {
            tagIdList.add(deviceResult.getTagId());
        }

        /*String weChatID = "";

        List<String> tagIdList = this.getTagIdList(guid, scenesId, weChatID);

        if (StringUtil.stringIsNull(weChatID)) {
            throw new RuntimeException("用户没有绑定微信");
        }*/

        tagIdList.add("1143454297266610176"); // 类型调查问卷

        List surveyList = this.pushMassage(tagIdList);

        if (surveyList == null || surveyList.isEmpty()) {
            throw new RuntimeException("没有查到相应的调查问卷");
        }

        JSONObject paramObject = new JSONObject();
        paramObject.put("type", "formTagPush");

        JSONObject data = new JSONObject();
        data.put("dataGuidList", surveyList);
        data.put("title", "调查问卷");
        data.put("content", "请点击详情完成调查问卷");

        JSONObject userObject = new JSONObject();
        userObject.put("Weixinid", weChatID);
        userObject.put("guid", guid);

        JSONArray userList = new JSONArray();
        userList.add(userObject);

        data.put("userList", userList);

        paramObject.put("data", data);

        String res =
                HttpClientUtil.doPost(
                        String.format(
                                HttpClientUtil.HTTP_URL_FORMAT,
                                businessConfig.getMsgPublishUrl(),
                                businessConfig.getMsgPublishPort(),
                                "wpa/msg/sendPubMsg"
                        ),
                        JSON.toJSONString(paramObject)
                );

        log.info("res ==> {}", res);

        return "推送调查问卷成功";
    }

    /**
     * 获取标签 id List
     *
     * @param guid     患者id
     * @param scenesId 场景id
     * @return 标签 id List
     */
    private List<String> getTagIdList(String guid, String scenesId) {
        /*Map<String, Object> resultMap = getTagAndScore(guid, scenesId);
        List<DeviceResult> resultList = (List<DeviceResult>) resultMap.get("resultList");

        List<String> tagIdList = new ArrayList<>();
        tagIdList.add(scenesId);
        for (DeviceResult deviceResult : resultList) {
            tagIdList.add(deviceResult.getTagId());
        }*/
        return this.getTagIdList(guid, scenesId, "");
    }

    /**
     * 获取标签 id List 重载方法
     *
     * @param guid     患者id
     * @param scenesId 场景id
     * @return 标签 id List
     */
    private List<String> getTagIdList(String guid, String scenesId, String weChatID) {
        Map<String, Object> resultMap = getTagAndScore(guid, scenesId);
        List<DeviceResult> resultList = (List<DeviceResult>) resultMap.get("resultList");

        weChatID = (String) resultMap.get("weChatID");

        List<String> tagIdList = new ArrayList<>();
        tagIdList.add(scenesId);
        for (DeviceResult deviceResult : resultList) {
            tagIdList.add(deviceResult.getTagId());
        }
        return tagIdList;
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
     * 根据场景计算结果摧送消息
     *
     * @param tagIdList 计算结果
     */
    private List<String> pushMassage(List<String> tagIdList) {
        List<String> formIdList = this.customFormTagService.selectCustomFormIdsByTagIdList(tagIdList);
        log.info("formIdList:{}", formIdList);

        if (formIdList == null || formIdList.isEmpty()) {
            return Collections.emptyList();
        }

        return formIdList.stream().map(id -> id.split(";")[0]).collect(Collectors.toList());
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

        List<DeviceResult> tagList = new ArrayList();

        int resultListSize = resultList.size();
        if (resultListSize < 4) {
            tagList = resultList;
        } else {
            for (int i = 0; i < 3; i++) {
                tagList.add(resultList.get(i));
            }

        }

        for (DeviceResult deviceResult: tagList) {
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
     * @param guid 患者 guid
     * @param scenesId 场景id
     * @param weChatId 微信id
     * @return 宣教list
     */
    private List<String> getMission(String guid, String scenesId, String weChatId) {
        List<String> tagIdList = getTagIdList(guid, scenesId, weChatId);

        tagIdList.add("1143454014872510464"); // 类型宣教

        return pushMassage(tagIdList);
    }
}
