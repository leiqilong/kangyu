package com.hlife.shilitianqi.controller;

import com.hlife.shilitianqi.model.DataCollectionOut;
import com.hlife.shilitianqi.service.DataCollectionOutService;
import com.hlife.framework.base.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 信息采集后台控制器
 */
@Slf4j
@RestController
@RequestMapping("/dataCollectionOutServiceController")
public class DataCollectionOutServiceController {

    @Autowired
    private DataCollectionOutService dataCollectionOutService;

    /**
     * 信息采集后台入口
     */
    @PostMapping(value = "/saveOrUpdateDataCollectionOut")
    public ResultVO<DataCollectionOut> saveOrUpdateDataCollectionOut(@RequestBody DataCollectionOut dataCollectionOut) {
        return new ResultVO<>(dataCollectionOutService.saveOrUpdateDataCollectionOut(dataCollectionOut));
    }

    /**
     * 信息采集数据删除
     * @param id 数据id
     * @return 操作成功失败
     */
    @DeleteMapping(value = "/deleteDataCollectionOut/{id}")
    public ResultVO<String> deleteDataCollectionOut(@PathVariable("id") String id) {
        log.debug("id==>{}", id);
        return new ResultVO<>(dataCollectionOutService.deleteDataCollectionOutById(id));
    }
}
