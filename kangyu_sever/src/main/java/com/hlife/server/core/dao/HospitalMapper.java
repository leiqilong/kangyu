package com.hlife.server.core.dao;

import com.hlife.server.core.model.Hospital;

/**
 * 医院持久层接口
 */
public interface HospitalMapper {
    /**
     * 根据id 查询医院信息
     *
     * @param id 医院主键
     * @return 医院信息
     */
    Hospital getHospitalById(String id);
}
