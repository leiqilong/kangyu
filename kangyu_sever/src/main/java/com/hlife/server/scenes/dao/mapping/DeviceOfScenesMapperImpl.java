package com.hlife.server.scenes.dao.mapping;

import com.hlife.framework.base.BaseMapper;
import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.server.scenes.dao.DeviceOfScenesMapper;
import com.hlife.server.scenes.model.DeviceOfScenes;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 场景设备持久层实现
 */
@Repository
public class DeviceOfScenesMapperImpl extends BaseMapper implements DeviceOfScenesMapper {
    @Override
    public DeviceOfScenes saveDeviceOfScenes(DeviceOfScenes deviceOfScenes) {
        return this.mongoTemplate.save(deviceOfScenes);
    }

    @Override
    public List<DeviceOfScenes> searchDeviceOfScenesList(String scenesId) {
        return this.mongoTemplate.find(new BasicQuery(new Document("scenesId", scenesId)), DeviceOfScenes.class);
    }

    @Override
    public boolean isExists(Document document) {
        return this.mongoTemplate.exists(new BasicQuery(document), DeviceOfScenes.class);
    }

    @Override
    public Long deleteByDeviceOfScenesId(String deviceOfScenesId) {
        return this.mongoTemplate.remove(new BasicQuery(new Document("deviceOfScenesId", deviceOfScenesId)), DeviceOfScenes.class).getDeletedCount();
    }

    @Override
    public DeviceOfScenes updateDeviceOfScenes(DeviceOfScenes deviceOfScenes) {
        Update update = Update.update("deviceCode", deviceOfScenes.getDeviceCode())
                .set("deviceName", deviceOfScenes.getDeviceName())
                .set("Priority", deviceOfScenes.getPriority())
                .set("weights", deviceOfScenes.getWeights())
                .set("fieldPaths", deviceOfScenes.getFieldPaths())
                .set("scenesFunKey", deviceOfScenes.getScenesFunKey());
        this.mongoTemplate.updateFirst(
                new BasicQuery(
                        new Document("deviceOfScenesId", deviceOfScenes.getDeviceOfScenesId())
                ),
                update,
                DeviceOfScenes.class
        );
        return deviceOfScenes;
    }

    @Override
    public Long deleteByScenesId(String scenesId) {
        return this.mongoTemplate.remove(new BasicQuery(new Document("scenesId", scenesId)), DeviceOfScenes.class).getDeletedCount();
    }

    @Override
    public PageResult<DeviceOfScenes> searchDeviceOfScenesListByParam(Document queryDoc, PageParam pageParam) {
        return this.pageQuery(new BasicQuery(queryDoc), DeviceOfScenes.class, pageParam.getPageSize(), pageParam.getPageNum());
    }
}
