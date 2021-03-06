package com.hlife.server.core.service;

import com.hlife.server.core.model.Hospital;

/**
 * 医院服务层接口
 */
public interface HospitalService {
    /**
     * 根据 id 获取医院信息
     *
     * @param id 医院主键
     * @return 医院信息
     */
    Hospital getHospitalById(String id);
}
