package com.hlife.server.core.service;

import com.alibaba.fastjson.JSONObject;
import com.hlife.server.core.model.Doctor;
import com.hlife.server.core.model.Hospital;

/**
 * 登录服务层接口
 */
public interface LoginService {
    /**
     * 根据 id 获取医院信息
     *
     * @param id 医院主键
     * @return
     */
    Hospital getHospitalById(String id);

    /**
     * 登录
     *
     * @param jsonObject phone 用户名手机号
     *                   password 密码
     * @return 医生信息
     */
    Doctor login(JSONObject jsonObject);
}
