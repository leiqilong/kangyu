package com.hlife.shilitianqi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.framework.util.GuidUtil;
import com.hlife.framework.util.StringUtil;
import com.hlife.shilitianqi.dao.DeviceOfScenesMapper;
import com.hlife.shilitianqi.model.Device;
import com.hlife.shilitianqi.model.DeviceOfScenes;
import com.hlife.shilitianqi.service.DeviceOfScenesService;
import com.hlife.shilitianqi.service.DeviceService;
import com.hlife.shilitianqi.service.JudgeStandardService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 场景设备服务层实现
 */
@Service
public class DeviceOfScenesServiceImpl implements DeviceOfScenesService {

    @Autowired
    private DeviceOfScenesMapper deviceOfScenesMapper;
    @Autowired
    private JudgeStandardService judgeStandardService;
    @Autowired
    private DeviceService deviceService;

    @Override
    public DeviceOfScenes saveOrEditDeviceOfScenes(DeviceOfScenes deviceOfScenes) {
        checkRepeat(deviceOfScenes);

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

        for (DeviceOfScenes deviceOfScenes : deviceOfScenesList) {
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

    @Override
    public List<DeviceOfScenes> createDeviceOfScenesListByDevice() {
        return this.deviceService.getDeviceList()
                .stream().map(device -> DeviceOfScenes.createDeviceOfScenesByDevice(device))
                .map(list -> list.stream()).flatMap(Function.identity())
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<DeviceOfScenes> searchDeviceOfScenesListByParam(JSONObject jsonObject) {
        Document queryDoc = new Document();

        String scenesId = jsonObject.getString("scenesId");
        if (StringUtil.stringIsNotNull(scenesId)) {
            queryDoc.append("scenesId", scenesId);
        }

        PageParam pageParam = new PageParam(jsonObject.getInteger(PageParam.PAGE_SIZE),
                jsonObject.getInteger(PageParam.PAGE_NUM));
        return this.deviceOfScenesMapper.searchDeviceOfScenesListByParam(queryDoc, pageParam);
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

    /**
     * 场景设备重复验证
     * @param deviceOfScenes 场景设备信息
     */
    private void checkRepeat(DeviceOfScenes deviceOfScenes) {
        Document queryDoc = new Document("scenesId", deviceOfScenes.getScenesId());

        String deviceOfScenesId = deviceOfScenes.getDeviceOfScenesId();
        if (StringUtil.stringIsNotNull(deviceOfScenesId)) {
            queryDoc.put("deviceOfScenesId", new Document("$ne", deviceOfScenesId));
        }

        queryDoc.append("deviceName", deviceOfScenes.getDeviceName())
                .append("deviceCode", deviceOfScenes.getDeviceCode());
        if (this.deviceOfScenesMapper.isExists(queryDoc)) {
            throw new RuntimeException("场景设备名称和代码重复!");
        }

        queryDoc.remove("deviceName");
        queryDoc.remove("deviceCode");

        queryDoc.append("weights", deviceOfScenes.getWeights())
                .append("priority", deviceOfScenes.getPriority());
        if (this.deviceOfScenesMapper.isExists(queryDoc)) {
            throw new RuntimeException("场景设备权重优先级重复!");
        }

    }
}
