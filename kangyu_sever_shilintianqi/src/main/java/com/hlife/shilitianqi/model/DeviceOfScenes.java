package com.hlife.shilitianqi.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * 场景设备实体类
 */
@Data
@Accessors(chain = true)
@Document(collection = "device_of_scenes")
public class DeviceOfScenes implements Serializable {
    /**
     * 主键
     */
    @Indexed(unique = true)
    private String deviceOfScenesId;

    /**
     * 场景id
     */
    @Indexed
    private String scenesId;

    /**
     * 设备代码
     */
    private String deviceCode;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 判别
     * 不记录当前文档
     */
    @Transient
    private List<JudgeStandard> judgeStandardList;

    /**
     * 权重
     */
    private Double weights;

    /**
     * 优先级
     */
    private Integer priority;
}
