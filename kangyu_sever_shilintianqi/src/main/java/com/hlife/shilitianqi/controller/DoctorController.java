package com.hlife.shilitianqi.controller;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageResult;
import com.hlife.framework.base.ResultVO;
import com.hlife.shilitianqi.model.Doctor;
import com.hlife.shilitianqi.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 医生控制器
 */
@RestController
@RequestMapping(value = "/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    /**
     * 获取医生列表
     *
     * @param jsonObject
     * @return resultVO.resultData 医生信息列表
     */
    @PostMapping(value = "/getDoctorList")
    public ResultVO<PageResult<Doctor>> getDoctorList(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(doctorService.getDoctorList(jsonObject));
    }

    /**
     * 删除医生信息
     * @param id 医生id
     * @return resultVO.resultData 操作成功
     */
    @DeleteMapping(value = "/deleteDoctor/{id}/{rand}")
    public ResultVO<String> deleteDoctor(@PathVariable(value = "id") String id) {
        return new ResultVO<>(doctorService.deleteDoctorById(id));
    }

    /**
     * 根据id 获取医生信息
     */
    @GetMapping(value = "getDoctorById/{id}/{rand}")
    public ResultVO<Doctor> getDoctorById(@PathVariable(value = "id") String id) {
        return new ResultVO<>(doctorService.selectDoctorById(id));
    }

    @PostMapping(value = "/createOrEditDoctor")
    public ResultVO<Doctor> createOrEditDoctor(@RequestBody Doctor doctor) {
        return new ResultVO<>(doctorService.createOrEditDoctor(doctor));
    }
}
