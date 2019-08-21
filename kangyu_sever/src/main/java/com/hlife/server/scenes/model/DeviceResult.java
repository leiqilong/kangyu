package com.hlife.server.scenes.model;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Optional;

/**
 * 设备计算结果实体类
 */
@Data
@Accessors(chain = true)
public class DeviceResult implements Serializable {

    /**
     * 设备代码
     */
    private String dataType;

    /**
     * 得分
     */
    private Double score;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 标签值
     */
    private String tagValue;

    /**
     * 标签Id
     */
    private String tagId;

    private String tagRemark;

    private Boolean base;

    private JSONObject jsonObject;

    private Record datas;



    public boolean isNotBase() {
        return !Optional.of(this.base).orElse(false);
    }
    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Record {

        /**
         * 二次数据
         */
        private String twiceValue;

        /**
         * 中文描述
         */
        private String chineseDescription;
    }
}
