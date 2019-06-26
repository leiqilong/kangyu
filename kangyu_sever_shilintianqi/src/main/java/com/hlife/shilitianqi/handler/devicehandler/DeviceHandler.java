package com.hlife.shilitianqi.handler.devicehandler;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.util.StringUtil;
import com.hlife.shilitianqi.model.DeviceResult;
import com.hlife.shilitianqi.model.JudgeStandard;
import lombok.extern.slf4j.Slf4j;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 设备算法分支管理器
 */
@Slf4j
public class DeviceHandler {

    private static ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("js");

    /**
     * 手环-运动
     *
     * @param jsonObject     运动数据
     * @param judgeStandards 判别标准
     * @return 结果
     */
    public static DeviceResult bandStepDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        log.info("jsonObject==>{}", jsonObject);
        JSONObject dayTotals = jsonObject.getJSONObject("data").getJSONObject("dayTotals");
        String step = dayTotals.getString("step");

        return getDeviceResult(judgeStandards, step);
    }

    /**
     * 手环-血压
     */
    public static DeviceResult bandBloodpressureDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        JSONObject bloodPressureData = jsonObject.getJSONArray("data").getJSONObject(0);

        String sbp = bloodPressureData.getString("sbp");
        String dbp = bloodPressureData.getString("dbp");

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
        return null;
    }

    /**
     * 手环-血氧
     */
    public static DeviceResult bandBloodoxygenDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        //JSONObject data = jsonObject.getJSONObject("data");
        JSONObject bloodoxygenData = jsonObject.getJSONArray("data").getJSONObject(0);

        String xygen = bloodoxygenData.getString("so2");

        return getDeviceResult(judgeStandards, xygen);
    }

    /**
     * 手环-心率
     */
    public static DeviceResult bandheartRateDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        //JSONObject data = jsonObject.getJSONObject("data");
        JSONObject heartRateData = jsonObject.getJSONArray("data").getJSONObject(0);

        String bpm = heartRateData.getString("bpm");
        return getDeviceResult(judgeStandards, bpm);
    }

    /**
     * 手环-睡眠
     */
    public static DeviceResult bandSleepDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        //JSONObject data = jsonObject.getJSONObject("data");
        int ss = jsonObject.getInteger("ss");
        int qs = jsonObject.getInteger("qs");

        return getDeviceResult(judgeStandards, String.valueOf((ss + qs) / 60));
    }

    /**
     * 报告单体成分
     */
    public static DeviceResult bgdTcfDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        JSONObject dataItem = jsonObject.getJSONArray("hdcl").getJSONObject(0).getJSONObject("DataItem");

        return getDeviceResult(judgeStandards, dataItem.getString("Tzhl"));
    }

    /**
     * 水杯数据
     */
    public static DeviceResult waterDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        return getDeviceResult(judgeStandards, jsonObject.getString("dayNumber"));
    }

    /**
     * 量表-睡眠时间
     */
    public static DeviceResult diagnose_sleepTimeDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        return getDiagnoseDeviceResult(judgeStandards, jsonObject.getString("sleepTime"));
    }

    /**
     * 档案-身高体重
     */
    public static DeviceResult diagnose_whDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        int height = jsonObject.getInteger("height");
        int weight = jsonObject.getInteger("weight");
        return getDeviceResult(judgeStandards, String.valueOf(height / weight));
    }

    /**
     * 量表-户外运动
     */
    public static DeviceResult diagnose_outdoorDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        return getDiagnoseDeviceResult(judgeStandards, jsonObject.getString("outdoor"));
    }

    /**
     * 量表-心率
     */
    public static DeviceResult diagnose_heartDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        return getDiagnoseDeviceResult(judgeStandards, jsonObject.getString("heart"));
    }

    /**
     * 膳食营养-能量
     */
    public static DeviceResult cfTjspLd_energDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        JSONObject energy = jsonObject.getJSONObject("Energy");
        double actual = energy.getDouble("Actual");
        double recommend = energy.getDouble("Recommend");
        return getDeviceResult(judgeStandards, String.valueOf(actual / recommend));
    }

    /**
     * 膳食营养-蛋白质
     */
    public static DeviceResult cfTjspLd_proteinDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        JSONObject protein = jsonObject.getJSONObject("Protein");
        double actual = protein.getDouble("Actual");
        double recommend = protein.getDouble("Recommend");
        return getDeviceResult(judgeStandards, String.valueOf(actual / recommend));
    }

    /**
     * 膳食营养-脂肪
     */
    public static DeviceResult cfTjspLd_fatDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        JSONObject fat = jsonObject.getJSONObject("Fat");
        double actual = fat.getDouble("Actual");
        double recommend = fat.getDouble("Recommend");
        return getDeviceResult(judgeStandards, String.valueOf(actual / recommend));
    }

    /**
     * 膳食营养-碳水化合物
     */
    public static DeviceResult cfTjspLd_choDispart(JSONObject jsonObject, List<JudgeStandard> judgeStandards) {
        JSONObject cho = jsonObject.getJSONObject("Cho");
        double actual = cho.getDouble("Actual");
        double recommend = cho.getDouble("Recommend");
        return getDeviceResult(judgeStandards, String.valueOf(actual / recommend));
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

        return null;
    }

    /**
     * 其它计算结果
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

        return null;
    }
}
