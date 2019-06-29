package com.hlife.shilitianqi.handler.devicehandler;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.util.StringUtil;
import com.hlife.shilitianqi.model.DeviceResult;
import com.hlife.shilitianqi.model.JudgeStandard;
import lombok.extern.slf4j.Slf4j;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;

/**
 * 设备算法分支管理器
 */
@Slf4j
public class DeviceHandler {

    private static ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("js");

    private static final String PATH = "path";
    private static final String TWICE_VALUE = "twiceValue";
    private static final String CHINESE_DESCRIPTION = "chineseDescription";

    /**
     * 手环-运动
     *
     * @param jsonObject     运动数据
     * @param judgeStandards 判别标准
     * @return 结果
     */
    public static DeviceResult bandStepDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        log.info("jsonObject==>{}", jsonObject);

        String path = "datas.data.dayTotals.step";
        String defaultTwiceValue = "0";
        String chineseDescription = "手环-运动-步数";

        // 当前患者没有 运动数据
        if (jsonObject.isEmpty()) {
            return getDeviceResult(judgeStandards, defaultTwiceValue)
                    .setDatas(new JSONObject() {{
                        this.put(PATH, path);
                        this.put(TWICE_VALUE, defaultTwiceValue);
                        this.put(CHINESE_DESCRIPTION, chineseDescription);
                    }});
        }

        // 页面修改后的数据
        String stepTwice = jsonObject.getString(TWICE_VALUE);
        if (StringUtil.stringIsNotNull(stepTwice)) {
            return getDeviceResult(judgeStandards, stepTwice).setDatas(jsonObject);
        }


        // 原数据
        JSONObject dayTotals = jsonObject.getJSONObject("data").getJSONObject("dayTotals");
        String step = dayTotals.getString("step");

        return getDeviceResult(judgeStandards, step)
                .setDatas(new JSONObject() {{
                    this.put(PATH, path);
                    this.put(TWICE_VALUE, step);
                    this.put(CHINESE_DESCRIPTION, chineseDescription);
                }});
    }

    /**
     * 手环-血压
     */
    public static DeviceResult bandBloodpressureDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        log.info("jsonObject==>{}", jsonObject);
        String path = "datas.data[0].sbp/dbp";
        String defaultTwiceValue = "0/0";
        String chineseDescription = "手环-血压-收缩压/舒张压";


        // 无数据
        if (jsonObject.isEmpty()) {
            return getDeviceResult(judgeStandards, "0", "0")
                    .setDatas(new JSONObject() {{
                        this.put(PATH, path);
                        this.put(TWICE_VALUE, defaultTwiceValue);
                        this.put(CHINESE_DESCRIPTION, chineseDescription);
                    }});
        }

        // 页面修改后的数据
        String sbpAndDbpTwice = jsonObject.getString(TWICE_VALUE);
        if (StringUtil.stringIsNotNull(sbpAndDbpTwice)) {
            String[] sbpAndDbpTwiceArr = sbpAndDbpTwice.split("/");
            return getDeviceResult(judgeStandards, sbpAndDbpTwiceArr[0], sbpAndDbpTwiceArr[1]).setDatas(jsonObject);
        }

        // 原数据
        JSONObject bloodPressureData = jsonObject.getJSONArray("data").getJSONObject(0);

        String sbp = bloodPressureData.getString("sbp");
        String dbp = bloodPressureData.getString("dbp");

        return getDeviceResult(judgeStandards, sbp, dbp)
                .setDatas(new JSONObject() {{
                    this.put(PATH, path);
                    this.put(TWICE_VALUE, sbp + "/" + dbp);
                    this.put(CHINESE_DESCRIPTION, chineseDescription);
                }});
    }

    /**
     * 手环-血氧
     */
    public static DeviceResult bandBloodoxygenDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        String path = "datas.data[0].so2";
        String defaultTwiceValue = "0";
        String chineseDescription = "手环-血氧-so2";

        // 无数据
        if (jsonObject.isEmpty()) {
            return getDeviceResult(judgeStandards, defaultTwiceValue)
                    .setDatas(new JSONObject() {{
                        this.put(PATH, path);
                        this.put(TWICE_VALUE, defaultTwiceValue);
                        this.put(CHINESE_DESCRIPTION, chineseDescription);
                    }});
        }

        // 前台处理后的数据
        String xygenTwice = jsonObject.getString(TWICE_VALUE);
        if (StringUtil.stringIsNotNull(xygenTwice)) {
            return getDeviceResult(judgeStandards, xygenTwice).setDatas(jsonObject);
        }

        // 原数据
        JSONObject bloodoxygenData = jsonObject.getJSONArray("data").getJSONObject(0);

        String xygen = bloodoxygenData.getString("so2");

        return getDeviceResult(judgeStandards, xygen)
                .setDatas(new JSONObject() {{
                    this.put(PATH, path);
                    this.put(TWICE_VALUE, xygen);
                    this.put(CHINESE_DESCRIPTION, chineseDescription);
                }});
    }

    /**
     * 手环-心率
     */
    public static DeviceResult bandheartRateDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        String path = "datas.data[0].bpm";
        String defaultTwiceValue = "0";
        String chineseDescription = "手环-心率-bpm";

        // 无数据
        if (jsonObject.isEmpty()) {
            return getDeviceResult(judgeStandards, defaultTwiceValue)
                    .setDatas(new JSONObject() {{
                        this.put(PATH, path);
                        this.put(TWICE_VALUE, defaultTwiceValue);
                        this.put(CHINESE_DESCRIPTION, chineseDescription);
                    }});
        }

        // 前台处理后的数据
        String xygenTwice = jsonObject.getString(TWICE_VALUE);
        if (StringUtil.stringIsNotNull(xygenTwice)) {
            return getDeviceResult(judgeStandards, xygenTwice).setDatas(jsonObject);
        }

        // 原数据
        JSONObject heartRateData = jsonObject.getJSONArray("data").getJSONObject(0);

        String bpm = heartRateData.getString("bpm");

        return getDeviceResult(judgeStandards, bpm)
                .setDatas(new JSONObject() {{
                    this.put(PATH, path);
                    this.put(TWICE_VALUE, bpm);
                    this.put(CHINESE_DESCRIPTION, chineseDescription);
                }});
    }

    /**
     * 手环-睡眠
     */
    public static DeviceResult bandSleepDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        String path = "datas.ss/qs";
        String chineseDescription = "手环-睡眠-深睡/浅睡";

        // 无数据
        if (jsonObject.isEmpty()) {
            return getDeviceResult(judgeStandards, "0")
                    .setDatas(new JSONObject() {{
                        this.put(PATH, path);
                        this.put(TWICE_VALUE, "0/0");
                        this.put(CHINESE_DESCRIPTION, chineseDescription);
                    }});
        }
        // 页面修改后的数据
        String sleepTwice = jsonObject.getString(TWICE_VALUE);
        if (StringUtil.stringIsNotNull(sleepTwice)) {
            int ss = Integer.valueOf(sleepTwice.split("/")[0]);
            int qs = Integer.valueOf(sleepTwice.split("/")[1]);
            return getDeviceResult(judgeStandards, String.valueOf((ss + qs) / 60)).setDatas(jsonObject);
        }

        // 原数据
        int ss = jsonObject.getInteger("ss");
        int qs = jsonObject.getInteger("qs");

        return getDeviceResult(judgeStandards, String.valueOf((ss + qs) / 60))
                .setDatas(new JSONObject() {{
                    this.put(PATH, path);
                    this.put(TWICE_VALUE, ss + "/" + qs);
                    this.put(CHINESE_DESCRIPTION, chineseDescription);
                }});
    }

    /**
     * 报告单体成分
     */
    public static DeviceResult bgdTcfDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        String path = "datas.hdcl[0].DataItem.Tzhl";
        String defaultTwiceValue = "0";
        String chineseDescription = "报告单-体成分-体脂含量";

        // 无数据
        if (jsonObject.isEmpty()) {
            return getDeviceResult(judgeStandards, defaultTwiceValue)
                    .setDatas(new JSONObject() {{
                        this.put(PATH, path);
                        this.put(TWICE_VALUE, defaultTwiceValue);
                        this.put(CHINESE_DESCRIPTION, chineseDescription);
                    }});
        }

        // 前台处理后的数据
        String tzhlTwice = jsonObject.getString(TWICE_VALUE);
        if (StringUtil.stringIsNotNull(tzhlTwice)) {
            return getDeviceResult(judgeStandards, tzhlTwice).setDatas(jsonObject);
        }

        // 原数据
        JSONObject dataItem = jsonObject.getJSONArray("hdcl").getJSONObject(0).getJSONObject("DataItem");
        String tzhl = dataItem.getString("Tzhl");

        return getDeviceResult(judgeStandards, tzhl)
                .setDatas(new JSONObject() {{
                    this.put(PATH, path);
                    this.put(TWICE_VALUE, tzhl);
                    this.put(CHINESE_DESCRIPTION, chineseDescription);
                }});
    }

    /**
     * 水杯数据
     */
    public static DeviceResult waterDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        String path = "datas.waterValue";
        String defalutTwiceValue = "0";
        String chineseDescription = "水杯-饮水量";

        // 无数据
        if (jsonObject.isEmpty()) {
            return getDeviceResult(judgeStandards, defalutTwiceValue)
                    .setDatas(new JSONObject() {{
                        this.put(PATH, path);
                        this.put(TWICE_VALUE, defalutTwiceValue);
                        this.put(CHINESE_DESCRIPTION, chineseDescription);
                    }});
        }

        // 前台处理后的数据
        String waterValueTwice = jsonObject.getString(TWICE_VALUE);
        if (StringUtil.stringIsNotNull(waterValueTwice)) {
            return getDeviceResult(judgeStandards, waterValueTwice).setDatas(jsonObject);
        }

        // 原数据
        String waterValue = jsonObject.getString("waterValue") == null ? "0" : jsonObject.getString("waterValue");
        return getDeviceResult(judgeStandards, waterValue)
                .setDatas(new JSONObject() {{
                    this.put(PATH, path);
                    this.put(TWICE_VALUE, waterValue);
                    this.put(CHINESE_DESCRIPTION, chineseDescription);
                }});
    }

    /**
     * 量表-睡眠时间
     */
    public static DeviceResult diagnose_sleepTimeDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        String path = "datas.sleepTime";
        String defalutTwiceValue = "<9h";
        String chineseDescription = "量表-睡眠时间";

        // 无数据
        if (jsonObject.isEmpty()) {
            return getDiagnoseDeviceResult(judgeStandards, defalutTwiceValue)
                    .setDatas(new JSONObject() {{
                        this.put(PATH, path);
                        this.put(TWICE_VALUE, defalutTwiceValue);
                        this.put(CHINESE_DESCRIPTION, chineseDescription);
                    }});
        }

        // 前台处理后的数据
        String sleepTimeTwice = jsonObject.getString(TWICE_VALUE);
        if (StringUtil.stringIsNotNull(sleepTimeTwice)) {
            return getDiagnoseDeviceResult(judgeStandards, sleepTimeTwice).setDatas(jsonObject);
        }

        // 原数据
        String sleepTime = jsonObject.getString("sleepTime");
        return getDiagnoseDeviceResult(judgeStandards, sleepTime)
                .setDatas(new JSONObject() {{
                    this.put(PATH, path);
                    this.put(TWICE_VALUE, sleepTime);
                    this.put(CHINESE_DESCRIPTION, chineseDescription);
                }});
    }

    /**
     * 档案-身高体重
     */
    public static DeviceResult diagnose_whDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        String path = "datas.height/weight";
        String defalutTwiceValue = "0/1";
        String chineseDescription = "量表-档案-身高/体重";

        // 无数据
        if (jsonObject.isEmpty()) {
            JSONObject fileBMI = new JSONObject();
            fileBMI.put("height", 0);
            fileBMI.put("weight", 0);
            return getDeviceResult(judgeStandards, defalutTwiceValue)
                    .setDatas(new JSONObject() {{
                        this.put(PATH, path);
                        this.put(TWICE_VALUE, defalutTwiceValue);
                        this.put(CHINESE_DESCRIPTION, chineseDescription);
                    }});
        }

        // 前台二次处理的数据
        String BMITwice = jsonObject.getString(TWICE_VALUE);
        if (StringUtil.stringIsNotNull(BMITwice)) {
            return getDeviceResult(judgeStandards, BMITwice).setDatas(jsonObject);
        }

        // 原数据
        JSONObject fileBMI = new JSONObject();
        double height = jsonObject.getInteger("height");
        double weight = jsonObject.getInteger("weight") == 0 ? 1 : jsonObject.getInteger("weight");
        fileBMI.put("height", height);
        fileBMI.put("weight", weight);

        return getDeviceResult(judgeStandards, String.valueOf(height / weight))
                .setDatas(new JSONObject() {{
                    this.put(PATH, path);
                    this.put(TWICE_VALUE, height + "/" + weight);
                    this.put(CHINESE_DESCRIPTION, chineseDescription);
                }});
    }

    /**
     * 量表-户外运动
     */
    public static DeviceResult diagnose_outdoorDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        String path = "datas.outdoor";
        String defalutTwiceValue = "＜60min/天";
        String chineseDescription = "量表-户外运动";

        // 无数据
        if (jsonObject.isEmpty()) {
            return getDiagnoseDeviceResult(judgeStandards, defalutTwiceValue)
                    .setDatas(new JSONObject() {{
                        this.put(PATH, path);
                        this.put(TWICE_VALUE, defalutTwiceValue);
                        this.put(CHINESE_DESCRIPTION, chineseDescription);
                    }});
        }

        // 前台处理后的数据
        String outdoorTwice = jsonObject.getString(TWICE_VALUE);
        if (StringUtil.stringIsNotNull(outdoorTwice)) {
            return getDiagnoseDeviceResult(judgeStandards, outdoorTwice).setDatas(jsonObject);
        }

        // 原数据
        String outdoor = jsonObject.getString("outdoor");
        return getDiagnoseDeviceResult(judgeStandards, outdoor)
                .setDatas(new JSONObject() {{
                    this.put(PATH, path);
                    this.put(TWICE_VALUE, outdoor);
                    this.put(CHINESE_DESCRIPTION, chineseDescription);
                }});
    }

    /**
     * 量表-心率
     */
    public static DeviceResult diagnose_heartDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        String path = "datas.heart";
        String defalutTwiceValue = "＜80次/分钟";
        String chineseDescription = "量表-心率";

        // 无数据
        if (jsonObject.isEmpty()) {
            return getDiagnoseDeviceResult(judgeStandards, defalutTwiceValue)
                    .setDatas(new JSONObject() {{
                        this.put(PATH, path);
                        this.put(TWICE_VALUE, defalutTwiceValue);
                        this.put(CHINESE_DESCRIPTION, chineseDescription);
                    }});
        }

        // 前台处理后的数据
        String heartTwice = jsonObject.getString(TWICE_VALUE);
        if (StringUtil.stringIsNotNull(heartTwice)) {
            return getDiagnoseDeviceResult(judgeStandards, heartTwice).setDatas(jsonObject);
        }

        // 原数据
        String heart = jsonObject.getString("heart");
        return getDiagnoseDeviceResult(judgeStandards, heart)
                .setDatas(new JSONObject() {{
                    this.put(PATH, path);
                    this.put(TWICE_VALUE, heart);
                    this.put(CHINESE_DESCRIPTION, chineseDescription);
                }});
    }

    /**
     * 膳食营养-能量
     */
    public static DeviceResult cfTjspLd_energDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        String path = "datas.Energy.Actual/Recommend";
        String defalutTwiceValue = "0/1";
        String chineseDescription = "膳食营养-能量-实际值/推荐值";

        // 无数据
        if (jsonObject.isEmpty()) {
            return getDeviceResult(judgeStandards, defalutTwiceValue)
                    .setDatas(new JSONObject() {{
                        this.put(PATH, path);
                        this.put(TWICE_VALUE, defalutTwiceValue);
                        this.put(CHINESE_DESCRIPTION, chineseDescription);
                    }});
        }

        // 前台二次处理的数据
        String energyTwice = jsonObject.getString(TWICE_VALUE);
        if (StringUtil.stringIsNotNull(energyTwice)) {
            return getDeviceResult(judgeStandards, energyTwice).setDatas(jsonObject);
        }

        // 原数据
        JSONObject energy = jsonObject.getJSONObject("Energy");
        double actual = energy.getDouble("Actual");
        double recommend = energy.getDouble("Recommend") == 0 ? 1 : energy.getDouble("Recommend");

        return getDeviceResult(judgeStandards, String.valueOf(actual / recommend))
                .setDatas(new JSONObject() {{
                    this.put(PATH, path);
                    this.put(TWICE_VALUE, actual + "/" + recommend);
                    this.put(CHINESE_DESCRIPTION, chineseDescription);
                }});
    }

    /**
     * 膳食营养-蛋白质
     */
    public static DeviceResult cfTjspLd_proteinDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        String path = "datas.Protein.Actual/Recommend";
        String defalutTwiceValue = "0/1";
        String chineseDescription = "膳食营养-蛋白质-实际值/推荐值";

        // 无数据
        if (jsonObject.isEmpty()) {
            return getDeviceResult(judgeStandards, "0")
                    .setDatas(new JSONObject() {{
                        this.put(PATH, path);
                        this.put(TWICE_VALUE, defalutTwiceValue);
                        this.put(CHINESE_DESCRIPTION, chineseDescription);
                    }});
        }

        // 前台二次处理的数据
        String proteinTwice = jsonObject.getString(TWICE_VALUE);
        if (StringUtil.stringIsNotNull(proteinTwice)) {
            return getDeviceResult(judgeStandards, proteinTwice).setDatas(jsonObject);
        }

        // 原数据
        JSONObject protein = jsonObject.getJSONObject("Protein");
        double actual = protein.getDouble("Actual");
        double recommend = protein.getDouble("Recommend") == 0 ? 1 : protein.getDouble("Recommend");

        return getDeviceResult(judgeStandards, String.valueOf(actual / recommend))
                .setDatas(new JSONObject() {{
                    this.put(PATH, path);
                    this.put(TWICE_VALUE, actual + "/" + recommend);
                    this.put(CHINESE_DESCRIPTION, chineseDescription);
                }});
    }

    /**
     * 膳食营养-脂肪
     */
    public static DeviceResult cfTjspLd_fatDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        String path = "datas.Fat.Actual/Recommend";
        String defalutTwiceValue = "0/1";
        String chineseDescription = "膳食营养-脂肪-实际值/推荐值";

        // 无数据
        if (jsonObject.isEmpty()) {
            return getDeviceResult(judgeStandards, defalutTwiceValue)
                    .setDatas(new JSONObject() {{
                        this.put(PATH, path);
                        this.put(TWICE_VALUE, defalutTwiceValue);
                        this.put(CHINESE_DESCRIPTION, chineseDescription);
                    }});
        }

        // 前台二次处理的数据
        // 前台二次处理的数据
        String fatTwice = jsonObject.getString(TWICE_VALUE);
        if (StringUtil.stringIsNotNull(fatTwice)) {
            return getDeviceResult(judgeStandards, fatTwice).setDatas(jsonObject);
        }

        JSONObject fat = jsonObject.getJSONObject("Fat");
        double actual = fat.getDouble("Actual");
        double recommend = fat.getDouble("Recommend") == 0 ? 1 : fat.getDouble("Recommend");


        return getDeviceResult(judgeStandards, String.valueOf(actual / recommend))
                .setDatas(new JSONObject() {{
                    this.put(PATH, path);
                    this.put(TWICE_VALUE, actual + "/" + recommend);
                    this.put(CHINESE_DESCRIPTION, chineseDescription);
                }});
    }

    /**
     * 膳食营养-碳水化合物
     */
    public static DeviceResult cfTjspLd_choDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        String path = "datas.Fat.Actual/Recommend";
        String defalutTwiceValue = "0/1";
        String chineseDescription = "膳食营养-碳水化合物-实际值/推荐值";

        // 无数据
        if (jsonObject.isEmpty()) {
            return getDeviceResult(judgeStandards, defalutTwiceValue)
                    .setDatas(new JSONObject() {{
                        this.put(PATH, path);
                        this.put(TWICE_VALUE, defalutTwiceValue);
                        this.put(CHINESE_DESCRIPTION, chineseDescription);
                    }});
        }
        // 前台二次处理的数据
        String choTwice = jsonObject.getString(TWICE_VALUE);
        if (StringUtil.stringIsNotNull(choTwice)) {
            return getDeviceResult(judgeStandards, choTwice).setDatas(jsonObject);
        }


        JSONObject cho = jsonObject.getJSONObject("Cho");
        double actual = cho.getDouble("Actual");
        double recommend = cho.getDouble("Recommend") == 0 ? 1 : cho.getDouble("Recommend");

        return getDeviceResult(judgeStandards, String.valueOf(actual / recommend))
                .setDatas(new JSONObject() {{
                    this.put(PATH, path);
                    this.put(TWICE_VALUE, actual + "/" + recommend);
                    this.put(CHINESE_DESCRIPTION, chineseDescription);
                }});
    }


    /**
     * 儿童健康检查-运动
     */
    public static DeviceResult form_childrenHeathTyhd(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        String path = "datas.jk_tyhd";
        String defalutTwiceValue = "<60min/天";
        String chineseDescription = "儿童健康检查-运动";

        // 无数据
        if (jsonObject.isEmpty()) {
            return getDiagnoseDeviceResult(judgeStandards, defalutTwiceValue)
                    .setDatas(new JSONObject() {{
                        this.put(PATH, path);
                        this.put(TWICE_VALUE, defalutTwiceValue);
                        this.put(CHINESE_DESCRIPTION, chineseDescription);
                    }});
        }

        // 前台处理后的数据
        String jkTyhdTwice = jsonObject.getString(TWICE_VALUE);
        if (StringUtil.stringIsNotNull(jkTyhdTwice)) {
            return getDiagnoseDeviceResult(judgeStandards, jkTyhdTwice).setDatas(jsonObject);
        }

        // 原数据
        String jkTyhd = jsonObject.getString("jk_tyhd");
        return getDiagnoseDeviceResult(judgeStandards, jkTyhd)
                .setDatas(new JSONObject() {{
                    this.put(PATH, path);
                    this.put(TWICE_VALUE, jkTyhd);
                    this.put(CHINESE_DESCRIPTION, chineseDescription);
                }});
    }

    /**
     * 儿童健康检查-饮水量
     */
    public static DeviceResult form_childrenHeathYsl(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        String path = "datas.jk_ysl";
        String defalutTwiceValue = "<1000ml";
        String chineseDescription = "儿童健康检查-饮水量";

        // 无数据
        if (jsonObject.isEmpty()) {
            return getDiagnoseDeviceResult(judgeStandards, defalutTwiceValue)
                    .setDatas(new JSONObject() {{
                        this.put(PATH, path);
                        this.put(TWICE_VALUE, defalutTwiceValue);
                        this.put(CHINESE_DESCRIPTION, chineseDescription);
                    }});
        }

        // 前台处理后的数据
        String jkSylTwice = jsonObject.getString(TWICE_VALUE);
        if (StringUtil.stringIsNotNull(jkSylTwice)) {
            return getDiagnoseDeviceResult(judgeStandards, jkSylTwice).setDatas(jsonObject);
        }

        // 原数据
        String jkSyl = jsonObject.getString("jk_ysl");
        return getDiagnoseDeviceResult(judgeStandards, jkSyl)
                .setDatas(new JSONObject() {{
                    this.put(PATH, path);
                    this.put(TWICE_VALUE, jkSyl);
                    this.put(CHINESE_DESCRIPTION, chineseDescription);
                }});
    }

    /**
     * 儿童健康检查-睡眠
     */
    public static DeviceResult form_childrenHeathSmsj(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        String path = "datas.jk_smsj";
        String defalutTwiceValue = "<9h";
        String chineseDescription = "儿童健康检查-睡眠";

        // 无数据
        if (jsonObject.isEmpty()) {
            return getDiagnoseDeviceResult(judgeStandards, defalutTwiceValue)
                    .setDatas(new JSONObject() {{
                        this.put(PATH, path);
                        this.put(TWICE_VALUE, defalutTwiceValue);
                        this.put(CHINESE_DESCRIPTION, chineseDescription);
                    }});
        }

        // 前台处理后的数据
        String jkSmsjTwice = jsonObject.getString(TWICE_VALUE);
        if (StringUtil.stringIsNotNull(jkSmsjTwice)) {
            return getDiagnoseDeviceResult(judgeStandards, jkSmsjTwice).setDatas(jsonObject);
        }

        // 原数据
        String jkSmsj = jsonObject.getString("jk_smsj");
        return getDiagnoseDeviceResult(judgeStandards, jkSmsj)
                .setDatas(new JSONObject() {{
                    this.put(PATH, path);
                    this.put(TWICE_VALUE, jkSmsj);
                    this.put(CHINESE_DESCRIPTION, chineseDescription);
                }});
    }

    /**
     * 儿童健康检查-心率
     */
    public static DeviceResult form_childrenHeathXl(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        String path = "datas.jk_xl";
        String defalutTwiceValue = "<80次/分钟";
        String chineseDescription = "儿童健康检查-心率";

        // 无数据
        if (jsonObject.isEmpty()) {
            return getDiagnoseDeviceResult(judgeStandards, defalutTwiceValue)
                    .setDatas(new JSONObject() {{
                        this.put(PATH, path);
                        this.put(TWICE_VALUE, defalutTwiceValue);
                        this.put(CHINESE_DESCRIPTION, chineseDescription);
                    }});
        }

        // 前台处理后的数据
        String jkXlTwice = jsonObject.getString(TWICE_VALUE);
        if (StringUtil.stringIsNotNull(jkXlTwice)) {
            return getDiagnoseDeviceResult(judgeStandards, jkXlTwice).setDatas(jsonObject);
        }

        // 原数据
        String jkXl = jsonObject.getString("jk_xl");
        return getDiagnoseDeviceResult(judgeStandards, jkXl)
                .setDatas(new JSONObject() {{
                    this.put(PATH, path);
                    this.put(TWICE_VALUE, jkXl);
                    this.put(CHINESE_DESCRIPTION, chineseDescription);
                }});
    }

    /**
     * 量表/自定义表单计算结果
     *
     * 数据为字符串,采用 equals 对比
     */
    private static DeviceResult getDiagnoseDeviceResult(List<JudgeStandard> judgeStandards, String param) {
        if (StringUtil.stringIsNull(param)) {
            return new DeviceResult().setScore(100d);
        }
        param = param.trim();
        for (JudgeStandard judgeStandard : judgeStandards) {
            String ruler = judgeStandard.getRuler();

            if (StringUtil.stringIsNull(ruler)) {
                continue;
            }

            boolean match = ruler.trim().equals(param);

            log.info("ruler:{}, param:{}, match:{}", ruler, param, match);

            if (match) {
                return new DeviceResult()
                        .setTagName(judgeStandard.getTagName())
                        .setTagValue(judgeStandard.getTagValue())
                        .setTagId(judgeStandard.getTagId())
                        .setScore(judgeStandard.getScore());
            }
        }

        return new DeviceResult().setScore(100d);
    }

    /**
     * 其它计算结果
     *
     * 公式 算法 用数据替换公式中的 X 然后用 eval
     *
     */
    private static DeviceResult getDeviceResult(List<JudgeStandard> judgeStandards, String param) {
        boolean match = false;
        for (JudgeStandard judgeStandard : judgeStandards) {
            try {
                match = (boolean) scriptEngine.eval(judgeStandard.getRuler().toUpperCase().replaceAll("X", param));
            } catch (ScriptException e) {
                e.printStackTrace();
            }

            if (match) {
                return new DeviceResult()
                        .setTagName(judgeStandard.getTagName())
                        .setTagValue(judgeStandard.getTagValue())
                        .setTagId(judgeStandard.getTagId())
                        .setScore(judgeStandard.getScore());
            }
        }

        return new DeviceResult().setScore(100d);
    }

    /**
     * 手环-血压 计算
     *
     *  公式 算法 用数据 替换 公式中 X Y 参数
     *
     *  血压 X sbp 收缩压（高压）
     *  血压 Y dbp 舒张压（低压）
     */
    private static DeviceResult getDeviceResult(List<JudgeStandard> judgeStandards, String sbp, String dbp) {
        boolean match = false;
        for (JudgeStandard judgeStandard : judgeStandards) {
            try {
                match = (boolean) scriptEngine.eval(judgeStandard.getRuler().replaceAll("X", sbp).replaceAll("Y", dbp));
            } catch (ScriptException e) {
                e.printStackTrace();
            }

            if (match) {
                return new DeviceResult()
                        .setTagName(judgeStandard.getTagName())
                        .setTagValue(judgeStandard.getTagValue())
                        .setTagId(judgeStandard.getTagId())
                        .setScore(judgeStandard.getScore());
            }
        }
        return new DeviceResult().setScore(100d);
    }

}
