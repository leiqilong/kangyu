package com.hlife.server.scenes.dao;

import com.hlife.server.scenes.model.Device;

import java.util.List;

/**
 * 设备管理持久层接口
 */
public interface DeviceMapper {
    void addDeviceList(List<Device> devices);

    List<Device> findAll();

    long deleteAll();

    void dropCollection();
}
