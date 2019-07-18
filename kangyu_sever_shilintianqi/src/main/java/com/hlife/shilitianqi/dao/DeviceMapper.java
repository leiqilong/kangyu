package com.hlife.shilitianqi.dao;

import com.hlife.shilitianqi.model.Device;

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
