package com.hlife.shilitianqi.business_config;

import com.alibaba.fastjson.JSONObject;
import com.hlife.shilitianqi.handler.devicehandler.DeviceHandler;
import com.hlife.shilitianqi.model.DeviceResult;
import com.hlife.shilitianqi.model.JudgeStandard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * springBean 配置
 */
@Configuration
public class BeanConfig {

    /**
     * 场景设备计算分支
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
        return deviceResultFunMap;
    }
}
