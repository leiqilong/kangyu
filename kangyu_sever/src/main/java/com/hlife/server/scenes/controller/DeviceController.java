package com.hlife.server.scenes.controller;

import com.hlife.framework.base.ResultVO;
import com.hlife.server.scenes.model.Device;
import com.hlife.server.scenes.service.DeviceService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 设备管理控制器
 */
@Slf4j
@RestController
@RequestMapping(value = "device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    /**
     * 增加默认设备数据
     *
     * @return 添加成功
     */
    @ApiOperation("增加默认设备数据")
    @PostMapping("addDeviceList")
    public ResultVO<String> addDeviceList() {
        return new ResultVO<>(this.deviceService.addDeviceList());
    }

    /**
     * 查询所有设备
     *
     * @return 查询所有设备
     */
    @ApiOperation("查询所有设备")
    @GetMapping("getDeviceList/{rand}")
    public ResultVO<List<Device>> getDeviceList() {
        return new ResultVO<>(this.deviceService.getDeviceList());
    }
}
