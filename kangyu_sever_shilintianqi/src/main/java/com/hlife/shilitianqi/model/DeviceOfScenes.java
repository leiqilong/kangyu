package com.hlife.shilitianqi.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
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

    /**
     * 算法key
     */
    private String scenesFunKey;

    /**
     * 参与运算字段
     */
    private Device.FieldPath[] fieldPaths;

    /**
     * 单项选择项
     */
    private String[] selector;

    public static List<DeviceOfScenes> createDeviceOfScenesByDevice(Device device) {
        List<DeviceOfScenes> deviceOfScenesList = new ArrayList<>();
        List<Device.CalculationType> calculationTypeList = device.getCalculationTypeList();
        for (Device.CalculationType calculationType : calculationTypeList) {
            DeviceOfScenes deviceOfScenes =
                    new DeviceOfScenes()
                            .setDeviceCode(getDeviceCode(device.getDeviceCode(), calculationType.getTypeName()))
                            .setDeviceName(getDeviceCode(device.getDeviceName(), calculationType.getTypeName()))
                            .setScenesFunKey(calculationType.getScenesFunKey())
                            .setFieldPaths(calculationType.getFieldPaths())
                            .setSelector(calculationType.getSelector());
            deviceOfScenesList.add(deviceOfScenes);
        }
        return deviceOfScenesList;
    }

    private static String getDeviceCode(String deviceCode, String typeName) {
        return String.format("%s-%s", deviceCode, typeName);
    }
}
