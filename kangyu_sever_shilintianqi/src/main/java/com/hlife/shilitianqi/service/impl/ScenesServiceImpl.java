package com.hlife.shilitianqi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.framework.util.GuidUtil;
import com.hlife.framework.util.HttpClientUtil;
import com.hlife.framework.util.StringUtil;
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
    }};

    @Value("${user-data.url}")
    private String url;
    @Value("${user-data.port}")
    private String port;

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
                            .setTagName(scenes.getScenesName())
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
                        //.setTagCode(scenes.getScenesCode())
                        .setTagName(scenes.getScenesName())
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
        log.info("url ==> {}, port ==> {}", url, port);

        Map<String, Object> resultMap = new HashMap<>();

        List<DeviceResult> resultList = new ArrayList<>();

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

        double score = 0d;

        // 远程获取当前人所额设备数据
        String userDataStr =
                HttpClientUtil.doPost(
                        String.format(HttpClientUtil.HTTP_URL_FORMAT, url, port, "api/LatestData/All"),
                        JSON.toJSONString(new JSONObject().fluentPut("queryID", guid))
                );

        if (StringUtil.stringIsNull(userDataStr)) {
            throw new RuntimeException("暂无数据！");
        }

        log.info("userData ==> {}", userDataStr);

        JSONObject jsonObject = JSON.parseObject(userDataStr);

        JSONArray jsonArray = jsonObject.getJSONArray("datas");

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

        int resultListSize = resultList.size();
        if (resultListSize < 3) {
            resultMap.put("resultList", resultList);
        } else {
            resultMap.put("resultList", new DeviceResult[]{resultList.get(0), resultList.get(1), resultList.get(2)});
        }

        resultMap.put("score", score);

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

        tagIdList.add("1143454297266610176"); // 类型宣教

        return pushMassage(tagIdList);
    }

    @Override
    public List<CustomFormTag> getCustomFormTagList(JSONObject jsonObject) {
        return customFormTagService.getCustomFormTagList(jsonObject);
    }

    private List<String> getTagIdList(String guid, String scenesId) {
        Map<String, Object> resultMap = getTagAndScore(guid, scenesId);
        List<DeviceResult> resultList = (List<DeviceResult>) resultMap.get("resultList");

        List<String> tagIdList = new ArrayList<>();
        tagIdList.add(scenesId);
        for (DeviceResult deviceResult : resultList) {
            tagIdList.add(deviceResult.getTagId());
        }
        return tagIdList;
    }

    private double getScore(List<DeviceResult> resultList, JSONArray jsonArray, DeviceOfScenes device) {
        String deviceCode = device.getDeviceCode();
        String deviceType = deviceCode.split("-")[0];
        for (int i = 0, size = jsonArray.size(); i < size; i++) {
            JSONObject data = jsonArray.getJSONObject(i);
            log.info("dataType ==> {}", data.getString("dataType"));

            if (deviceType.equals(data.getString("dataType"))) {
                BiFunction<JSONObject, List<JudgeStandard>, DeviceResult> fun = deviceResultFunMap.get(deviceCode);
                if (fun == null) {
                    return 0d;
                }
                DeviceResult deviceResult = fun.apply(data.getJSONObject("datas"), device.getJudgeStandardList());
                if (deviceResult != null) {
                    resultList.add(deviceResult.setCode(deviceCode));
                    return deviceResult.getScore() * device.getWeights();
                }
            }
        }

        return 0d;
    }


    /**
     * 根据场景计算结果摧送消息
     *
     * @param tagIdList 计算结果
     */
    private List<String> pushMassage(List<String> tagIdList) {


        /*List<String> tags = new ArrayList<String>() {{
            this.add("1143067481694822400");
            this.add("1143068096294576128");
            this.add("1143066995155558400");
            this.add("1143068018372796416");
        }};*/

        List<String> formIdList = this.customFormTagService.selectCustomFormIdsByTagIdList(tagIdList);
        log.info("formIdList:{}", formIdList);
        return formIdList;
    }
}
