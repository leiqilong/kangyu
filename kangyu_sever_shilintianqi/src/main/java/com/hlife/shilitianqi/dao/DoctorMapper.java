package com.hlife.shilitianqi.dao;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.shilitianqi.model.Doctor;
import org.bson.Document;

/**
 * 医生持久层接口
 */
public interface DoctorMapper {
    /**
     * 根据手机号获取医生信息
     * @param phone
     * @return
     */
    Doctor selectByPhone(String phone);

    PageResult<Doctor> getDoctorList(Document document,  PageParam pageParam);

    Doctor selectDoctorById(String id);

    long deleteDoctorById(String id);

    Doctor saveDoctor(Doctor doctor);

    boolean isExists(Document queryDoc);
}
