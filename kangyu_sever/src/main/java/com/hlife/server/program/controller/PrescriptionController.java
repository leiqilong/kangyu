package com.hlife.server.program.controller;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageResult;
import com.hlife.framework.base.ResultVO;
import com.hlife.server.program.model.Prescription;
import com.hlife.server.program.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 处方管理controller
 */
@RestController
@RequestMapping(value = "/prescription")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    /**
     * 分页查询
     *
     * @param jsonObject 查询条件 <br/>
     *                   title: 标题 <br/>
     *                   daterange： 时间范围 <br/>
     *                   pageSize： 每页大小 <br/>
     *                   pageNum: 当前页
     *
     * @return 当前页数据
     */
    @PostMapping(value = "/findPrescriptionPageResult")
    public ResultVO<PageResult<Prescription>> findPrescriptionPageResult(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(this.prescriptionService.findPrescriptionPageResult(jsonObject));
    }

    /**
     * 保存
     *
     * @param prescription 处方实例
     * @return resultVO.resultData 保存完成的处方实例
     */
    @PostMapping(value = "/savePrescription")
    public ResultVO<Prescription> savePrescription(@RequestBody Prescription prescription) {
        return new ResultVO<>(this.prescriptionService.savePrescription(prescription));
    }

    /**
     * 删除
     *
     * @param id 处方id
     * @return @return resultVO 删除的条数
     */
    @GetMapping(value = "/deletePrescription/{id}/{rand}")
    public ResultVO<Long> deletePrescription(@PathVariable(value = "id") String id) {
        return new ResultVO<>(this.prescriptionService.deletePrescription(id));
    }
}
