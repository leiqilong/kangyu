package com.hlife.shilitianqi.service.impl;

import com.hlife.shilitianqi.dao.HospitalMapper;
import com.hlife.shilitianqi.model.Hospital;
import com.hlife.shilitianqi.service.HospitalService;
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
