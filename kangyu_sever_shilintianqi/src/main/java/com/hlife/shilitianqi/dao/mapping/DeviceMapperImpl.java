package com.hlife.shilitianqi.dao.mapping;

import com.hlife.framework.base.BaseMapper;
import com.hlife.shilitianqi.dao.DeviceMapper;
import com.hlife.shilitianqi.model.Device;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 设备管理持久层实现
 */
@Repository
public class DeviceMapperImpl extends BaseMapper implements DeviceMapper {
    @Override
    public void addDeviceList(List<Device> devices) {
        this.saveBatch(devices, Device.class);
    }

    @Override
    public List<Device> findAll() {
        return this.mongoTemplate.find(new Query(), Device.class);
    }

    @Override
    public long deleteAll() {
        return this.mongoTemplate.remove(new Query(), Device.class).getDeletedCount();
    }

    @Override
    public void dropCollection() {
        this.mongoTemplate.dropCollection(Device.class);
    }
}
