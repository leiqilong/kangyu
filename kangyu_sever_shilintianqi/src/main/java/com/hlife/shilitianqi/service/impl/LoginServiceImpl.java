package com.hlife.shilitianqi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.util.StringUtil;
import com.hlife.shilitianqi.model.Doctor;
import com.hlife.shilitianqi.model.Hospital;
import com.hlife.shilitianqi.service.DoctorService;
import com.hlife.shilitianqi.service.HospitalService;
import com.hlife.shilitianqi.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 登录报务层实现
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private DoctorService doctorService;

    @Override
    public Hospital getHospitalById(String id) {
        return hospitalService.getHospitalById(id);
    }

    @Override
    public Doctor login(JSONObject jsonObject) {
        String phone = jsonObject.getString("phone");

        String password = jsonObject.getString("password");

        if (StringUtil.stringIsNull(phone)) {
            throw new RuntimeException("传入用户名为空！");
        }

        if (StringUtil.stringIsNull(password)) {
            throw new RuntimeException("传入密码为空！");
        }

        Doctor doctor = this.doctorService.selectByPhone(phone);
        if (doctor == null) {
            throw new RuntimeException("系统里没有改用户！");
        }

        if (!password.trim().equals(doctor.getPassword())) {
            throw new RuntimeException("输入用户名密码错误！");
        }
        return doctor;
    }
}
