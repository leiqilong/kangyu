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

        // 当前患者没有 运动数据
        if (jsonObject.isEmpty()) {
            return getDeviceResult(judgeStandards, "0")
                    .setDatas(new JSONObject() {{
                        this.put("data.dayTotals.step", "0");
                        this.put(CHINESE_DESCRIPTION, "手环-运动-步数");
                    }});
        }

        // 页面修改后的数据
        String stepTwice = jsonObject.getString("data.dayTotals.step");
        if (StringUtil.stringIsNotNull(stepTwice)) {
            return getDeviceResult(judgeStandards, stepTwice).setDatas(jsonObject);
        }


        // 原数据
        JSONObject dayTotals = jsonObject.getJSONObject("data").getJSONObject("dayTotals");
        String step = dayTotals.getString("step");

        return getDeviceResult(judgeStandards, step)
                .setDatas(new JSONObject() {{
                    this.put("data.dayTotals.step", step);
                    this.put(CHINESE_DESCRIPTION, "手环-运动-步数");
                }});
    }

    /**
     * 手环-血压
     */
    public static DeviceResult bandBloodpressureDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        log.info("jsonObject==>{}", jsonObject);

        // 无数据
        if (jsonObject.isEmpty()) {
            JSONObject data0 = new JSONObject();
            data0.put("sbp", "0");
            data0.put("dbp", "0");
            return getDeviceResult(judgeStandards, "0", "0")
                    .setDatas(new JSONObject() {{
                        this.put("data[0]", data0);
                        this.put(CHINESE_DESCRIPTION, "手环-血压-收缩压/舒张压");
                    }});
        }

        // 页面修改后的数据
        JSONObject sbpAndDbpTwice = jsonObject.getJSONObject("data[0]");
        if (sbpAndDbpTwice != null) {
            String sbp = sbpAndDbpTwice.getString("sbp");
            String dbp = sbpAndDbpTwice.getString("dbp");
            return getDeviceResult(judgeStandards, sbp, dbp).setDatas(jsonObject);
        }

        // 原数据
        JSONObject bloodPressureData = jsonObject.getJSONArray("data").getJSONObject(0);

        String sbp = bloodPressureData.getString("sbp");
        String dbp = bloodPressureData.getString("dbp");

        JSONObject data0 = new JSONObject();
        data0.put("sbp", sbp);
        data0.put("dbp", dbp);

        return getDeviceResult(judgeStandards, sbp, dbp)
                .setDatas(new JSONObject() {{
                    this.put("data[0]", data0);
                    this.put(CHINESE_DESCRIPTION, "手环-血压-收缩压/舒张压");
                }});
    }

    /**
     * 手环-血氧
     */
    public static DeviceResult bandBloodoxygenDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        // 无数据
        if (jsonObject.isEmpty()) {
            return getDeviceResult(judgeStandards, "0")
                    .setDatas(new JSONObject() {{
                        this.put("data[0].so2", "0");
                        this.put(CHINESE_DESCRIPTION, "手环-血氧-so2");
                    }});
        }

        // 前台处理后的数据
        String xygenTwice = jsonObject.getString("data[0].so2");
        if (StringUtil.stringIsNotNull(xygenTwice)) {
            return getDeviceResult(judgeStandards, xygenTwice).setDatas(jsonObject);
        }

        // 原数据
        JSONObject bloodoxygenData = jsonObject.getJSONArray("data").getJSONObject(0);

        String xygen = bloodoxygenData.getString("so2");

        return getDeviceResult(judgeStandards, xygen)
                .setDatas(new JSONObject() {{
                    this.put("data[0].so2", xygen);
                    this.put(CHINESE_DESCRIPTION, "手环-血氧-so2");
                }});
    }

    /**
     * 手环-心率
     */
    public static DeviceResult bandheartRateDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        // 无数据
        if (jsonObject.isEmpty()) {
            return getDeviceResult(judgeStandards, "0")
                    .setDatas(new JSONObject() {{
                        this.put("data[0].bpm", "0");
                        this.put(CHINESE_DESCRIPTION, "手环-心率-bpm");
                    }});
        }

        // 前台处理后的数据
        String xygenTwice = jsonObject.getString("data[0].bpm");
        if (StringUtil.stringIsNotNull(xygenTwice)) {
            return getDeviceResult(judgeStandards, xygenTwice).setDatas(jsonObject);
        }

        // 原数据
        JSONObject heartRateData = jsonObject.getJSONArray("data").getJSONObject(0);

        String bpm = heartRateData.getString("bpm");

        return getDeviceResult(judgeStandards, bpm)
                .setDatas(new JSONObject() {{
                    this.put("data[0].bpm", bpm);
                    this.put(CHINESE_DESCRIPTION, "手环-心率-bpm");
                }});
    }

    /**
     * 手环-睡眠
     */
    public static DeviceResult bandSleepDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        // 无数据
        if (jsonObject.isEmpty()) {
            JSONObject data = new JSONObject();
            data.put("ss", "0");
            data.put("qs", "0");
            return getDeviceResult(judgeStandards, "0")
                    .setDatas(new JSONObject() {{
                        this.put("sleepTwice", data);
                        this.put(CHINESE_DESCRIPTION, "手环-睡眠-深睡/浅睡");
                    }});
        }
        // 页面修改后的数据
        JSONObject sleepTwice = jsonObject.getJSONObject("sleepTwice");
        if (sleepTwice != null) {
            int ss = sleepTwice.getInteger("ss");
            int qs = sleepTwice.getInteger("qs");
            return getDeviceResult(judgeStandards, String.valueOf((ss + qs) / 60)).setDatas(jsonObject);
        }

        // 原数据
        JSONObject data = new JSONObject();
        int ss = jsonObject.getInteger("ss");
        int qs = jsonObject.getInteger("qs");
        data.put("ss", ss);
        data.put("qs", qs);

        return getDeviceResult(judgeStandards, String.valueOf((ss + qs) / 60))
                .setDatas(new JSONObject() {{
                    this.put("sleepTwice", data);
                    this.put(CHINESE_DESCRIPTION, "手环-睡眠-深睡/浅睡");
                }});
    }

    /**
     * 报告单体成分
     */
    public static DeviceResult bgdTcfDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        // 无数据
        if (jsonObject.isEmpty()) {
            return getDeviceResult(judgeStandards, "0")
                    .setDatas(new JSONObject() {{
                        this.put("hdcl[0].DataItem.Tzhl", "0");
                        this.put(CHINESE_DESCRIPTION, "报告单-体成分-体脂含量");
                    }});
        }

        // 前台处理后的数据
        String tzhlTwice = jsonObject.getString("hdcl[0].DataItem.Tzhl");
        if (StringUtil.stringIsNotNull(tzhlTwice)) {
            return getDeviceResult(judgeStandards, tzhlTwice).setDatas(jsonObject);
        }

        // 原数据
        JSONObject dataItem = jsonObject.getJSONArray("hdcl").getJSONObject(0).getJSONObject("DataItem");
        String tzhl = dataItem.getString("Tzhl");

        return getDeviceResult(judgeStandards, tzhl)
                .setDatas(new JSONObject() {{
                    this.put("hdcl[0].DataItem.Tzhl", tzhl);
                    this.put(CHINESE_DESCRIPTION, "报告单-体成分-体脂含量");
                }});
    }

    /**
     * 水杯数据
     */
    public static DeviceResult waterDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        // 无数据
        if (jsonObject.isEmpty()) {
            return getDeviceResult(judgeStandards, "0")
                    .setDatas(new JSONObject() {{
                        this.put("waterValueTwice", "0");
                        this.put(CHINESE_DESCRIPTION, "水杯-饮水量");
                    }});
        }

        // 前台处理后的数据
        String waterValueTwice = jsonObject.getString("waterValueTwice");
        if (StringUtil.stringIsNotNull(waterValueTwice)) {
            return getDeviceResult(judgeStandards, waterValueTwice).setDatas(jsonObject);
        }

        // 原数据
        String waterValue = jsonObject.getString("waterValue") == null ? "0" : jsonObject.getString("waterValue");
        return getDeviceResult(judgeStandards, waterValue)
                .setDatas(new JSONObject() {{
                    this.put("waterValueTwice", waterValue);
                    this.put(CHINESE_DESCRIPTION, "水杯-饮水量");
                }});
    }

    /**
     * 量表-睡眠时间
     */
    public static DeviceResult diagnose_sleepTimeDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        // 无数据
        if (jsonObject.isEmpty()) {
            return getDiagnoseDeviceResult(judgeStandards, "<9h")
                    .setDatas(new JSONObject() {{
                        this.put("sleepTimeTwice", "<9h");
                        this.put(CHINESE_DESCRIPTION, "量表-睡眠时间");
                    }});
        }

        // 前台处理后的数据
        String sleepTimeTwice = jsonObject.getString("sleepTimeTwice");
        if (StringUtil.stringIsNotNull(sleepTimeTwice)) {
            return getDiagnoseDeviceResult(judgeStandards, sleepTimeTwice).setDatas(jsonObject);
        }

        // 原数据
        String sleepTime = jsonObject.getString("sleepTime");
        return getDiagnoseDeviceResult(judgeStandards, sleepTime)
                .setDatas(new JSONObject() {{
                    this.put("sleepTimeTwice", sleepTime);
                    this.put(CHINESE_DESCRIPTION, "量表-睡眠时间");
                }});
    }

    /**
     * 档案-身高体重
     */
    public static DeviceResult diagnose_whDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        // 无数据
        if (jsonObject.isEmpty()) {
            JSONObject fileBMI = new JSONObject();
            fileBMI.put("height", 0);
            fileBMI.put("weight", 0);
            return getDeviceResult(judgeStandards, "0")
                    .setDatas(new JSONObject() {{
                        this.put("fileBMITwice", fileBMI);
                        this.put(CHINESE_DESCRIPTION, "量表-档案-身高体重");
                    }});
        }

        // 前台二次处理的数据
        JSONObject fileBMITwice = jsonObject.getJSONObject("fileBMITwice");
        if (fileBMITwice != null) {
            double height = fileBMITwice.getInteger("height");
            double weight = fileBMITwice.getInteger("weight") == 0 ? 1 : fileBMITwice.getInteger("weight");
            return getDeviceResult(judgeStandards, String.valueOf(height / weight)).setDatas(jsonObject);
        }

        // 原数据
        JSONObject fileBMI = new JSONObject();
        double height = jsonObject.getInteger("height");
        double weight = jsonObject.getInteger("weight") == 0 ? 1 : jsonObject.getInteger("weight");
        fileBMI.put("height", height);
        fileBMI.put("weight", weight);

        return getDeviceResult(judgeStandards, String.valueOf(height / weight))
                .setDatas(new JSONObject() {{
                    this.put("fileBMITwice", fileBMI);
                    this.put(CHINESE_DESCRIPTION, "量表-档案-身高体重");
                }});
    }

    /**
     * 量表-户外运动
     */
    public static DeviceResult diagnose_outdoorDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        // 无数据
        if (jsonObject.isEmpty()) {
            return getDiagnoseDeviceResult(judgeStandards, "＜60min/天")
                    .setDatas(new JSONObject() {{
                        this.put("outdoorTwice", "＜60min/天");
                        this.put(CHINESE_DESCRIPTION, "量表-户外运动");
                    }});
        }

        // 前台处理后的数据
        String outdoorTwice = jsonObject.getString("outdoorTwice");
        if (StringUtil.stringIsNotNull(outdoorTwice)) {
            return getDiagnoseDeviceResult(judgeStandards, outdoorTwice).setDatas(jsonObject);
        }

        // 原数据
        String outdoor = jsonObject.getString("outdoor");
        return getDiagnoseDeviceResult(judgeStandards, outdoor)
                .setDatas(new JSONObject() {{
                    this.put("outdoorTwice", outdoor);
                    this.put(CHINESE_DESCRIPTION, "量表-户外运动");
                }});
    }

    /**
     * 量表-心率
     */
    public static DeviceResult diagnose_heartDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        // 无数据
        if (jsonObject.isEmpty()) {
            return getDiagnoseDeviceResult(judgeStandards, "＜80次/分钟")
                    .setDatas(new JSONObject() {{
                        this.put("heartTwice", "＜80次/分钟");
                        this.put(CHINESE_DESCRIPTION, "量表-心率");
                    }});
        }

        // 前台处理后的数据
        String heartTwice = jsonObject.getString("heartTwice");
        if (StringUtil.stringIsNotNull(heartTwice)) {
            return getDeviceResult(judgeStandards, heartTwice).setDatas(jsonObject);
        }

        // 原数据
        String heart = jsonObject.getString("heart");
        return getDiagnoseDeviceResult(judgeStandards, heart)
                .setDatas(new JSONObject() {{
                    this.put("heartTwice", heart);
                    this.put(CHINESE_DESCRIPTION, "量表-心率");
                }});
    }

    /**
     * 膳食营养-能量
     */
    public static DeviceResult cfTjspLd_energDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        // 无数据
        if (jsonObject.isEmpty()) {
            JSONObject energyT = new JSONObject();
            energyT.put("Actual", 0);
            energyT.put("Recommend", 0);
            return getDeviceResult(judgeStandards, "0")
                    .setDatas(new JSONObject() {{
                        this.put("energyTwice", energyT);
                        this.put(CHINESE_DESCRIPTION, "膳食营养-能量");
                    }});
        }

        // 前台二次处理的数据
        JSONObject energyTwice = jsonObject.getJSONObject("energyTwice");
        if (energyTwice != null) {
            double actual = energyTwice.getDouble("Actual");
            double recommend = energyTwice.getDouble("Recommend") == 0 ? 1 : energyTwice.getDouble("Recommend");
            return getDeviceResult(judgeStandards, String.valueOf(actual / recommend)).setDatas(jsonObject);
        }

        // 原数据
        JSONObject energy = jsonObject.getJSONObject("Energy");
        double actual = energy.getDouble("Actual");
        double recommend = energy.getDouble("Recommend") == 0 ? 1 : energy.getDouble("Recommend");

        JSONObject energyT = new JSONObject();
        energyT.put("Actual", actual);
        energyT.put("Recommend", recommend);

        return getDeviceResult(judgeStandards, String.valueOf(actual / recommend))
                .setDatas(new JSONObject() {{
                    this.put("energyTwice", energyT);
                    this.put(CHINESE_DESCRIPTION, "膳食营养-能量");
                }});
    }

    /**
     * 膳食营养-蛋白质
     */
    public static DeviceResult cfTjspLd_proteinDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        // 无数据
        if (jsonObject.isEmpty()) {
            JSONObject proteinT = new JSONObject();
            proteinT.put("Actual", 0);
            proteinT.put("Recommend", 0);
            return getDeviceResult(judgeStandards, "0")
                    .setDatas(new JSONObject() {{
                        this.put("proteinTwice", proteinT);
                        this.put(CHINESE_DESCRIPTION, "膳食营养-蛋白质");
                    }});
        }

        // 前台二次处理的数据
        JSONObject proteinTwice = jsonObject.getJSONObject("proteinTwice");
        if (proteinTwice != null) {
            double actual = proteinTwice.getDouble("Actual");
            double recommend = proteinTwice.getDouble("Recommend") == 0 ? 1 : proteinTwice.getDouble("Recommend");
            return getDeviceResult(judgeStandards, String.valueOf(actual / recommend)).setDatas(jsonObject);
        }

        // 原数据
        JSONObject protein = jsonObject.getJSONObject("Protein");
        double actual = protein.getDouble("Actual");
        double recommend = protein.getDouble("Recommend");

        JSONObject proteinT = new JSONObject();
        proteinT.put("Actual", actual);
        proteinT.put("Recommend", recommend);

        return getDeviceResult(judgeStandards, String.valueOf(actual / recommend))
                .setDatas(new JSONObject() {{
                    this.put("proteinTwice", proteinT);
                    this.put(CHINESE_DESCRIPTION, "膳食营养-蛋白质");
                }});
    }

    /**
     * 膳食营养-脂肪
     */
    public static DeviceResult cfTjspLd_fatDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        // 无数据
        if (jsonObject.isEmpty()) {
            JSONObject fatT = new JSONObject();
            fatT.put("Actual", 0);
            fatT.put("Recommend", 0);
            return getDeviceResult(judgeStandards, "0")
                    .setDatas(new JSONObject() {{
                        this.put("fatTwice", fatT);
                        this.put(CHINESE_DESCRIPTION, "膳食营养-脂肪");
                    }});
        }

        // 前台二次处理的数据
        JSONObject fatTwice = jsonObject.getJSONObject("fatTwice");
        if (fatTwice != null) {
            double actual = fatTwice.getDouble("Actual");
            double recommend = fatTwice.getDouble("Recommend") == 0 ? 1 : fatTwice.getDouble("Recommend");
            return getDeviceResult(judgeStandards, String.valueOf(actual / recommend)).setDatas(jsonObject);
        }

        JSONObject fat = jsonObject.getJSONObject("Fat");
        double actual = fat.getDouble("Actual");
        double recommend = fat.getDouble("Recommend") == 0 ? 1 : fat.getDouble("Recommend");

        JSONObject fatT = new JSONObject();
        fatT.put("Actual", actual);
        fatT.put("Recommend", recommend);

        return getDeviceResult(judgeStandards, String.valueOf(actual / recommend))
                .setDatas(new JSONObject() {{
                    this.put("fatTwice", fatT);
                    this.put(CHINESE_DESCRIPTION, "膳食营养-蛋白质");
                }});
    }

    /**
     * 膳食营养-碳水化合物
     */
    public static DeviceResult cfTjspLd_choDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        // 无数据
        if (jsonObject.isEmpty()) {
            JSONObject choT = new JSONObject();
            choT.put("Actual", 0);
            choT.put("Recommend", 0);
            return getDeviceResult(judgeStandards, "0")
                    .setDatas(new JSONObject() {{
                        this.put("choTwice", choT);
                        this.put(CHINESE_DESCRIPTION, "膳食营养-碳水化合物");
                    }});
        }

        // 前台二次处理的数据
        JSONObject choTwice = jsonObject.getJSONObject("choTwice");
        if (choTwice != null) {
            double actual = choTwice.getDouble("Actual");
            double recommend = choTwice.getDouble("Recommend") == 0 ? 1 : choTwice.getDouble("Recommend");
            return getDeviceResult(judgeStandards, String.valueOf(actual / recommend)).setDatas(jsonObject);
        }


        JSONObject cho = jsonObject.getJSONObject("Cho");
        double actual = cho.getDouble("Actual");
        double recommend = cho.getDouble("Recommend") == 0 ? 1 : cho.getDouble("Recommend");

        JSONObject choT = new JSONObject();
        choT.put("Actual", actual);
        choT.put("Recommend", recommend);

        return getDeviceResult(judgeStandards, String.valueOf(actual / recommend))
                .setDatas(new JSONObject() {{
                    this.put("choTwice", choT);
                    this.put(CHINESE_DESCRIPTION, "膳食营养-碳水化合物");
                }});
    }

    /**
     * 量表计算结果
     *
     * @return
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
     */
    public static DeviceResult getDeviceResult(List<JudgeStandard> judgeStandards, String param) {
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
