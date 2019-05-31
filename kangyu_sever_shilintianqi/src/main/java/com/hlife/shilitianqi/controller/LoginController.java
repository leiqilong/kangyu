package com.hlife.shilitianqi.controller;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.ResultVO;
import com.hlife.shilitianqi.model.Doctor;
import com.hlife.shilitianqi.model.Hospital;
import com.hlife.shilitianqi.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 登录控制器
 */
@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

   /**
     * 根据 id 获取医院信息
     * @param id 医院主键
     * @return resultVO.resultData 医生信息
     */
    @GetMapping(value = "/getHospitalById/{id}/{rand}")
    public ResultVO<Hospital> getHospitalById(@PathVariable("id") String id) {
        return new ResultVO<>(loginService.getHospitalById(id));
    }

    /**
     * 登录
     * @param jsonObject
     *              phone 用户名手机号
     *              password 密码
     * @return resultVO.resultData 医院信息
     */
    @PostMapping(value = "login")
    public ResultVO<Doctor> login(@RequestBody JSONObject jsonObject) {
        return new ResultVO<>(loginService.login(jsonObject));
    }
}
