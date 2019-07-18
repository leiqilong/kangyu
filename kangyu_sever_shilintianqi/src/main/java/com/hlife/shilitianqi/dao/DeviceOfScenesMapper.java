package com.hlife.shilitianqi.dao;

import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.shilitianqi.model.DeviceOfScenes;
import org.bson.Document;


import java.util.List;

/**
 * 场景设备持久层接口
 */
public interface DeviceOfScenesMapper {

    /**
     * 保存场景设备信息
     *
     * @param deviceOfScenes 场景设备信息
     * @return 场景设备信息
     */
    DeviceOfScenes saveDeviceOfScenes(DeviceOfScenes deviceOfScenes);

    /**
     * 根据场景id 查询对应的 设备信息 列表
     *
     * @param scenesId 场景id
     * @return 对应的 设备信息 列表
     */
    List<DeviceOfScenes> searchDeviceOfScenesList(String scenesId);

    /**
     * 判断设备信息 是否存在
     *
     * @param document 查询条伯
     * @return 是否存在
     */
    boolean isExists(Document document);

    /**
     * 根据 主键 删除
     *
     * @param deviceOfScenesId 主键
     * @return 删除的条数
     */
    Long deleteByDeviceOfScenesId(String deviceOfScenesId);

    /**
     * 修改 高备信息
     *
     * @param deviceOfScenes 设备信息
     * @return 设备信息
     */
    DeviceOfScenes updateDeviceOfScenes(DeviceOfScenes deviceOfScenes);

    /**
     * 根据场景id 删除 设备信息
     *
     * @param scenesId 场景id
     * @return 删除的条数
     */
    Long deleteByScenesId(String scenesId);

    /**
     * 根据场景id 查询对应的 设备信息 列表(分页)
     *
     * @param queryDoc 查询条件
     * @param pageParam 分页条件
     * @return
     */
    PageResult<DeviceOfScenes> searchDeviceOfScenesListByParam(Document queryDoc, PageParam pageParam);
}
