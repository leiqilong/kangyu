package com.hlife.server.scenes.service;

import com.hlife.server.scenes.model.Device;

import java.util.List;

/**
 * 设备管理服务层接口
 */
public interface DeviceService {



    List<Device> getDeviceList();

    /**
     * 增加默认设备数据
     *
     * @return 添加成功
     */
    String addDeviceList();
}
