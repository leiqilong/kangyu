package com.hlife.server.scenes.business_config;

import com.alibaba.fastjson.JSONObject;
import com.hlife.server.scenes.handler.checkhandler.ICheckAdapter;
import com.hlife.server.scenes.handler.devicehandler.DeviceHandler;
import com.hlife.server.scenes.handler.updatehandler.IUpdateAdapter;
import com.hlife.server.scenes.model.DeviceResult;
import com.hlife.server.scenes.model.JudgeStandard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * springBean 配置
 */
@Configuration
public class BeanConfig {

    @Resource(name = "judgeStandardServiceImpl")
    private ICheckAdapter judgeStandardImpl;

    @Resource(name = "judgeStandardServiceImpl")
    private IUpdateAdapter judgeStandardImplU;

    /**
     * 场景设备计算分支 （不用了）
     *
     * @return 场景设备计算全部分支
     */
    @Bean
    public Map<String, BiFunction<JSONObject, List<JudgeStandard>, DeviceResult>> deviceResultFunMap() {
        Map<String, BiFunction<JSONObject, List<JudgeStandard>, DeviceResult>> deviceResultFunMap = new HashMap<>();
        // 手环-运动 （step）
        deviceResultFunMap.put("ky.stl.Band.steps", DeviceHandler::bandStepDispart);
        // 手环-血压
        deviceResultFunMap.put("ky.stl.Band.bloodpressure", DeviceHandler::bandBloodpressureDispart);
        // 手环-血氧
        deviceResultFunMap.put("ky.stl.Band.bloodoxygen", DeviceHandler::bandBloodoxygenDispart);
        // 手环-心率
        deviceResultFunMap.put("ky.stl.Band.heartRate", DeviceHandler::bandheartRateDispart);
        // 手环-睡眠
        deviceResultFunMap.put("ky.stl.Band.sleep", DeviceHandler::bandSleepDispart);
        // 报告单-体成分
        deviceResultFunMap.put("ky.stl.bgd.tcf", DeviceHandler::bgdTcfDispart);
        // 水杯
        deviceResultFunMap.put("ky.stl.Water", DeviceHandler::waterDispart);
        // 量表-(睡眼)
        deviceResultFunMap.put("ky.stl.diagnose-sleepTime", DeviceHandler::diagnose_sleepTimeDispart);
        // 档案-bmi (量表-体重/身高）
        deviceResultFunMap.put("ky.stl.diagnose-wh", DeviceHandler::diagnose_whDispart);
        // 量表-（运动）
        deviceResultFunMap.put("ky.stl.diagnose-outdoor", DeviceHandler::diagnose_outdoorDispart);
        // 量表-（心率）
        deviceResultFunMap.put("ky.stl.diagnose-heart", DeviceHandler::diagnose_heartDispart);
        // 膳食营养-（能量）
        deviceResultFunMap.put("ky.stl.cf.tjsp.ld-Energy", DeviceHandler::cfTjspLd_energDispart);
        // 膳食营养-（蛋白质）
        deviceResultFunMap.put("ky.stl.cf.tjsp.ld-Protein", DeviceHandler::cfTjspLd_proteinDispart);
        // 膳食营养-（脂肪）
        deviceResultFunMap.put("ky.stl.cf.tjsp.ld-Fat", DeviceHandler::cfTjspLd_fatDispart);
        // 膳食营养-（碳水化合物）
        deviceResultFunMap.put("ky.stl.cf.tjsp.ld-Cho", DeviceHandler::cfTjspLd_choDispart);
        // 儿童健康检查-运动
        deviceResultFunMap.put("ky.stl.form.Y3D5YA9ZUQNW11IXEALFHQ2JKL6ERH1C-jk_tyhd", DeviceHandler::form_childrenHeathTyhdDispart);
        // 儿童健康检查-饮水量
        deviceResultFunMap.put("ky.stl.form.Y3D5YA9ZUQNW11IXEALFHQ2JKL6ERH1C-jk_ysl", DeviceHandler::form_childrenHeathYslDispart);
        // 儿童健康检查-睡眠
        deviceResultFunMap.put("ky.stl.form.Y3D5YA9ZUQNW11IXEALFHQ2JKL6ERH1C-jk_smsj", DeviceHandler::form_childrenHeathSmsjDispart);
        // 儿童健康检查-心率
        deviceResultFunMap.put("ky.stl.form.Y3D5YA9ZUQNW11IXEALFHQ2JKL6ERH1C-jk_xl", DeviceHandler::form_childrenHeathXlDispart);
        // 儿童健康检查-BMI
        deviceResultFunMap.put("ky.stl.form.Y3D5YA9ZUQNW11IXEALFHQ2JKL6ERH1C-jk_bmi", DeviceHandler::form_childrenHeathBMIDispart);
        // 孕期基础档案信息表-体温
        deviceResultFunMap.put("ky.stl.form.I5KE2SNR9UVGI3C4J5ZGMFWN237PDNWY-yqjcdaxx_tw", DeviceHandler::form_yqjcdaxx_twDispart);
        // 孕期基础档案信息表-血压
        deviceResultFunMap.put("ky.stl.form.I5KE2SNR9UVGI3C4J5ZGMFWN237PDNWY-yqjcdaxx_xy", DeviceHandler::form_yqjcdaxx_xyDispart);
        // 孕期基础档案信息表-现空腹血糖
        deviceResultFunMap.put("ky.stl.form.I5KE2SNR9UVGI3C4J5ZGMFWN237PDNWY-yqjcdaxx_yhxt", DeviceHandler::form_yqjcdaxx_yhxtDispart);
        // 孕期基础档案信息表-运动
        deviceResultFunMap.put("ky.stl.form.I5KE2SNR9UVGI3C4J5ZGMFWN237PDNWY-yqjcdaxx_yd2", DeviceHandler::form_yqjcdaxx_yd2Dispart);
        // 孕期基础档案信息表-饮水
        deviceResultFunMap.put("ky.stl.form.I5KE2SNR9UVGI3C4J5ZGMFWN237PDNWY-yqjcdaxx_ys1", DeviceHandler::form_yqjcdaxx_ys1Dispart);
        // 孕期基础档案信息表-情绪
        deviceResultFunMap.put("ky.stl.form.I5KE2SNR9UVGI3C4J5ZGMFWN237PDNWY-yqjcdaxx_ybxl1", DeviceHandler::form_yqjcdaxx_ybxl1Dispart);
        // 孕期基础档案信息表-睡眠
        deviceResultFunMap.put("ky.stl.form.I5KE2SNR9UVGI3C4J5ZGMFWN237PDNWY-yqjcdaxx_sleep3", DeviceHandler::form_yqjcdaxx_sleep3Dispart);

        return deviceResultFunMap;
    }

    /**
     * 标签删除监听
     */
    @Bean
    public List<ICheckAdapter> tagRemoveListeners() {
        List<ICheckAdapter> tagRemoveListeners = new ArrayList<>();
        tagRemoveListeners.add(judgeStandardImpl);
        return tagRemoveListeners;
    }

    /**
     * 标签修改监听
     */
    @Bean
    public List<IUpdateAdapter> tagUpdateListeners() {
        List<IUpdateAdapter> tagUpdateListeners = new ArrayList<>();
        tagUpdateListeners.add(judgeStandardImplU);
        return tagUpdateListeners;
    }
}
