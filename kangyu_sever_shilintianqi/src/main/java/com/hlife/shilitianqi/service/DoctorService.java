package com.hlife.shilitianqi.service;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageResult;
import com.hlife.shilitianqi.model.Doctor;

/**
 * 医生服务层接口
 */
public interface DoctorService {
    /**
     * 根据手机号获取医生信息
     *
     * @param phone 手机号
     * @return 医生信息
     */
    Doctor selectByPhone(String phone);

    /**
     * 获取医生信息列表
     *
     * @param jsonObject 查询条件 json
     * @return 医生信息列表
     */
    PageResult<Doctor> getDoctorList(JSONObject jsonObject);

    /**
     * 根据id 删除医生信息
     *
     * @param id id
     * @return "操作成功"
     */
    String deleteDoctorById(String id);

    /**
     * 根据id 查询 医生信息
     *
     * @param id id
     * @return 医生信息
     */
    Doctor selectDoctorById(String id);

    /**
     * 创建或修改医生信息
     *
     * @param doctor 医生信息
     * @return 医生信息
     */
    Doctor createOrEditDoctor(Doctor doctor);
}
