package com.hlife.server.scenes.constant;

import com.alibaba.fastjson.JSONObject;
import com.hlife.server.scenes.handler.devicehandler.DeviceResultHandler;
import com.hlife.server.scenes.handler.devicehandler.FlattenedDataHandler;
import com.hlife.server.scenes.model.Device;
import com.hlife.server.scenes.model.DeviceResult;
import com.hlife.server.scenes.model.JudgeStandard;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * 常量类
 */
public class ScenesConstant {
    private ScenesConstant() {
    }

    /**
     * 相关表单返回 value
     */
    public static final String VALUE = "value";

    /**
     * 相关表单返回 label
     */
    public static final String LABEL = "label";

    /**
     * 相关表单返回 type
     * 01: 量表
     * 02: 自定义表单
     * 03: 宣教
     * 04: 其它
     */
    public static final String TYPE = "type";

    /**
     * 相关表单 value 格式
     */
    public static final String VALUE_FORMART = "%s;%s";

    /**
     * 分组名
     */
    public static final String GROUP_LABEL = "groupLabel";

    /**
     * 分组 key
     */
    public static final String GROUP_OPTIONS = "groupOptions";

    /**
     * 量表枚举
     */
    public enum Scale {
        HEIGHT_AND_WEIGHT("d4fb3653-048f-404c-b808-d813d1f2e313", "身高体重", "test1"),
        SNAP_Ⅳ("62b1e1d9-ec82-484e-8b23-1ebdc5900dde", "SNAP-Ⅳ评定量表", "test2"),
        M_CHAT("1d20e63a-fe2a-4eb0-911a-72842f5824f5", "M-CHAT婴幼儿孤独症筛查量表", "test3");

        /**
         * 量表id
         */
        @Getter
        private String id;

        /**
         * 量表名称
         */
        @Getter
        private String name;

        /**
         * 量表url
         */
        @Getter
        private String url;

        Scale(String id, String name, String url) {
            this.id = id;
            this.name = name;
            this.url = url;
        }

        public static List<JSONObject> getScaleList() {
            List<JSONObject> jsonObjectList = new ArrayList<>();
            for (ScenesConstant.Scale scale : ScenesConstant.Scale.values()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(VALUE, String.format(VALUE_FORMART, scale.getId(), "01"));
                jsonObject.put(LABEL, scale.getName());
                jsonObjectList.add(jsonObject);
            }
            return jsonObjectList;
        }
    }

    /**
     * 相关表单类型
     */
    public enum RelatedFormType {
        CUSTOM_FORM("02", "formTagPush", "调查问卷", "请点击详情完成调查问卷"),
        MISSION("03", "pdAndEduTagPush", "宣教", "请点击详情完成宣教"),
        PRESCRIPTION("04", "处方"),
        DOCTOR("10", "医生"),
        OTHER("99", "其它");

        @Getter
        private String key;

        @Getter
        private String pushType;

        @Getter
        private String title;

        @Getter
        private String content;

        RelatedFormType(String key, String pushType, String title, String content) {
            this.key = key;
            this.pushType = pushType;
            this.title = title;
            this.content = content;
        }

        RelatedFormType(String key, String title) {
            this(key, null, title, null);
        }

        public String getNewFormId(String formId) {
            return String.format(VALUE_FORMART, formId, key);
        }
    }

    /**
     * 场景算法分支枚举
     */
    public enum ScenesFun {
        /**
         * 通用算法分支
         * <p>
         * 平行化算法 表达式为 X=value1;Y=value2;  匹配算法 用eval(表达式+规则)
         */
        COMMON_PARAMETER("common", DeviceResultHandler::getDeviceResultCommon),

        /**
         * 自定义表单算法
         * 平行化算法 返回值字符串; 匹配算法 用equals
         */
        FORM_PARAMETER("form", DeviceResultHandler::getDeviceResultForm),

        /**
         * 情绪类算法
         * 平行化算法 返回值字符串; 匹配算法 用contains
         */
        MOOD_PARAMETER("mood", DeviceResultHandler::getDeviceResultMood),

        /**
         * 盆底类
         */
        PENDI_PARAMETER("pendi", DeviceResultHandler::getDeviceResultPendi);

        /**
         * key
         */
        @Getter
        private String key;

        /**
         * 平行化数据算法
         */
        @Getter
        private BiFunction<JSONObject, Device.FieldPath[], DeviceResult.Record> funFlattenedData;

        /**
         * 规则计算
         */
        @Getter
        private BiFunction<List<JudgeStandard>, String, DeviceResult> funDeviceResult;

        ScenesFun(String key, BiFunction<JSONObject, Device.FieldPath[], DeviceResult.Record> funFlattenedData,
                  BiFunction<List<JudgeStandard>, String, DeviceResult> funDeviceResult) {
            this.key = key;
            this.funFlattenedData = funFlattenedData;
            this.funDeviceResult = funDeviceResult;
        }

        ScenesFun(String key, BiFunction<List<JudgeStandard>, String, DeviceResult> funDeviceResult) {
            this(key, FlattenedDataHandler::getParameterCommon, funDeviceResult);
        }


        /**
         * 根据key 获取枚举实例
         *
         * @param key key
         * @return 枚举实例
         */
        public static ScenesFun getInstance(String key) {
            for (ScenesFun scenesFun : ScenesFun.values()) {
                if (scenesFun.key.equals(key)) {
                    return scenesFun;
                }
            }
            throw new RuntimeException("没有找到相应的业务类型");
        }
    }
}
