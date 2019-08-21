package com.hlife.server.core.service.impl;

import com.hlife.server.core.dao.HospitalMapper;
import com.hlife.server.core.model.Hospital;
import com.hlife.server.core.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    private HospitalMapper hospitalMapper;

    @Override
    public Hospital getHospitalById(String id) {
        return hospitalMapper.getHospitalById(id);
    }
}
