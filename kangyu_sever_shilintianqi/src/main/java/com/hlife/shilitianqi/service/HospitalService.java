package com.hlife.shilitianqi.service;

import com.hlife.shilitianqi.model.Hospital;

/**
 * 医院服务层接口
 */
public interface HospitalService {
    /**
     * 根据 id 获取医院信息
     *
     * @param id 医院主键
     * @return
     */
    Hospital getHospitalById(String id);
}
