package com.hlife.shilitianqi.service.impl;

import com.hlife.framework.util.GuidUtil;
import com.hlife.framework.util.StringUtil;
import com.hlife.shilitianqi.dao.DeviceOfScenesMapper;
import com.hlife.shilitianqi.model.DeviceOfScenes;
import com.hlife.shilitianqi.service.DeviceOfScenesService;
import com.hlife.shilitianqi.service.JudgeStandardService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 场景设备服务层实现
 */
@Service
public class DeviceOfScenesServiceImpl implements DeviceOfScenesService {

    @Autowired
    private DeviceOfScenesMapper deviceOfScenesMapper;
    @Autowired
    private JudgeStandardService judgeStandardService;

    @Override
    public DeviceOfScenes saveOrEditDeviceOfScenes(DeviceOfScenes deviceOfScenes) {
        String deviceOfScenesId = deviceOfScenes.getDeviceOfScenesId();
        if (StringUtil.stringIsNull(deviceOfScenesId)) {
            deviceOfScenes.setDeviceOfScenesId(GuidUtil.generateGuid());
            return this.deviceOfScenesMapper.saveDeviceOfScenes(deviceOfScenes);
        }

        if (!this.deviceOfScenesMapper.isExists(new Document("deviceOfScenesId", deviceOfScenesId))) {
            throw new RuntimeException("该数据不存在");
        }

        return this.deviceOfScenesMapper.updateDeviceOfScenes(deviceOfScenes);
    }

    @Override
    public List<DeviceOfScenes> searchDeviceOfScenesList(String scenesId) {
        return this.deviceOfScenesMapper.searchDeviceOfScenesList(scenesId);
    }

    @Override
    public Long deleteByDeviceOfScenesId(String deviceOfScenesId) {
        if (!this.deviceOfScenesMapper.isExists(new Document("deviceOfScenesId", deviceOfScenesId))) {
            throw new RuntimeException("该数据不存在");
        }

        this.judgeStandardService.deleteJudgeStandardByDeviceOfScenesId(deviceOfScenesId);

        return this.deviceOfScenesMapper.deleteByDeviceOfScenesId(deviceOfScenesId);
    }

    @Override
    public Long deleteByScenesId(String scenesId) {
        List<DeviceOfScenes> deviceOfScenesList = this.searchDeviceOfScenesList(scenesId);
        if (deviceOfScenesList == null || deviceOfScenesList.isEmpty()) {
            return 0L;
        }

        for (DeviceOfScenes deviceOfScenes: deviceOfScenesList) {
            this.judgeStandardService.deleteJudgeStandardByDeviceOfScenesId(deviceOfScenes.getDeviceOfScenesId());
        }

        return this.deviceOfScenesMapper.deleteByScenesId(scenesId);
    }

    @Override
    public List<DeviceOfScenes> searchDeviceOfScenesWithJudgeStandardList(String scenesId) {
        return this.searchDeviceOfScenesList(scenesId)
                .stream()
                .map(this::setJudgeStandardListOfDeviceOfScenes)
                .collect(Collectors.toList());
    }

    /**
     * 给场景设备添加其判别规则
     *
     * @param deviceOfScenes 场景设备对象
     * @return 带有判别规则的场景设备对象
     */
    private DeviceOfScenes setJudgeStandardListOfDeviceOfScenes(DeviceOfScenes deviceOfScenes) {
        return deviceOfScenes.setJudgeStandardList(
                judgeStandardService.searchJudgeStandardList(deviceOfScenes.getDeviceOfScenesId())
        );
    }
}
