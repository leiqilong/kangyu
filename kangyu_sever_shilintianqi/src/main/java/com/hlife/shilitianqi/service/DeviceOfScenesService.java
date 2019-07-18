package com.hlife.shilitianqi.service;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageResult;
import com.hlife.shilitianqi.model.DeviceOfScenes;
import org.bson.Document;


import java.util.List;

/**
 * 场景设备服务层接口
 */
public interface DeviceOfScenesService {

    /**
     * 修改/保存 场景设备信息
     *
     * @param deviceOfScenes 场景设备信息
     * @return 场景设备信息
     */
    DeviceOfScenes saveOrEditDeviceOfScenes(DeviceOfScenes deviceOfScenes);

    /**
     * 根据场景 id 查询对应的 场景设备列表
     *
     * @param scenesId 场景 id
     * @return 场景设备列表
     */
    List<DeviceOfScenes> searchDeviceOfScenesList(String scenesId);

    /**
     * 根据主键 删除 设备信息
     *
     * @param deviceOfScenesId 主键
     * @return 影响的行数
     */
    Long deleteByDeviceOfScenesId(String deviceOfScenesId);

    /**
     * 删除 场景对应的 设备信息
     *
     * @param scenesId 场景 id
     * @return 影响的行数
     */
    Long deleteByScenesId(String scenesId);

    /**
     * 根据场景id 查询对应的设备用其判别规则
     *
     * @param scenesId 场景id
     * @return 对应的设备用其判别规则List
     */
    List<DeviceOfScenes> searchDeviceOfScenesWithJudgeStandardList(String scenesId);

    /**
     *
     * @return
     */
    List<DeviceOfScenes> createDeviceOfScenesListByDevice();

    PageResult<DeviceOfScenes> searchDeviceOfScenesListByParam(JSONObject jsonObject);
}
