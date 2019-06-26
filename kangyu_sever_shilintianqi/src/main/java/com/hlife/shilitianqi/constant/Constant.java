package com.hlife.shilitianqi.constant;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 常量类
 */
public class Constant {
    private Constant() {
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
     * 03: 消息
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
     * 量表格举
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
            for (Constant.Scale scale : Constant.Scale.values()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(VALUE, String.format(VALUE_FORMART, scale.getId(), "01"));
                //jsonObject.put(VALUE, scale.getId());
                jsonObject.put(LABEL, scale.getName());
                //jsonObject.put(TYPE, "01");
                jsonObjectList.add(jsonObject);
            }
            return jsonObjectList;
        }
    }



}
