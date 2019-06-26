package com.hlife.shilitianqi.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class DeviceResult implements Serializable {

    /**
     * 设备代码
     */
    private String code;

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
}
