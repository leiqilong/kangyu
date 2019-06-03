package com.hlife.shilitianqi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hlife.framework.base.PageParam;
import com.hlife.framework.base.PageResult;
import com.hlife.framework.util.GuidUtil;
import com.hlife.framework.util.StringUtil;
import com.hlife.shilitianqi.dao.DoctorMapper;
import com.hlife.shilitianqi.model.Doctor;
import com.hlife.shilitianqi.service.DoctorService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.regex.Pattern;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorMapper doctorMapper;

    @Override
    public Doctor selectByPhone(String phone) {
        return this.doctorMapper.selectByPhone(phone);
    }

    @Override
    //@Transactional(readOnly = true)
    public PageResult<Doctor> getDoctorList(JSONObject jsonObject) {
        Document queryDoc = new Document();

        String name = jsonObject.getString("name");
        if (StringUtil.stringIsNotNull(name)) {
            queryDoc.append("name", Pattern.compile("^.*" + name + ".*$", Pattern.CASE_INSENSITIVE));
        }

        String sex = jsonObject.getString("sex");
        if (StringUtil.stringIsNotNull(sex) && !"0".equals(sex)) {
            queryDoc.append("sex", sex);
        }

        Integer age = jsonObject.getInteger("age");
        if (age != null) {
            queryDoc.append("age", age);
        }

        String phone = jsonObject.getString("phone");
        if (StringUtil.stringIsNotNull(phone)) {
            queryDoc.append("phone", Pattern.compile("^.*" + phone + ".*$", Pattern.CASE_INSENSITIVE));
        }

        PageParam pageParam = new PageParam(jsonObject.getInteger(PageParam.PAGE_SIZE), jsonObject.getInteger(PageParam.PAGE_NUM));
        return this.doctorMapper.getDoctorList(queryDoc, pageParam);
    }

    @Override
    //@Transactional
    public String deleteDoctorById(String id) {
        if (StringUtil.stringIsNull(id)) {
            throw new RuntimeException("传入主键id为空");
        }
        Doctor doctor = this.selectDoctorById(id);
        if (doctor == null) {
            throw new RuntimeException("该条数据已不存在");
        }
        if (this.doctorMapper.deleteDoctorById(id) < 1) {
            throw new RuntimeException("删除操作失败");
        }
        throw new RuntimeException("删除操作失败");
        //return "删除操作成功";
    }

    @Override
    public Doctor selectDoctorById(String id) {
        return this.doctorMapper.selectDoctorById(id);
    }

    @Override
    public Doctor createOrEditDoctor(Doctor doctor) {
        this.checkPhone(doctor);
        String doctorId = doctor.getId();
        if (StringUtil.stringIsNotNull(doctorId)) {
            Doctor record = this.doctorMapper.selectDoctorById(doctorId);
            doctor.setCreatetime(record.getCreatetime())
                    .setPassword(record.getPassword());
            this.doctorMapper.deleteDoctorById(doctorId);
        } else {
            doctor.setId(GuidUtil.generateGuid())
                    .setCreatetime(new Date())
                    .setPassword("123456");
        }

        return this.doctorMapper.saveDoctor(doctor);
    }

    private void checkPhone(Doctor doctor) {
        Document queryDoc = new Document("phone", doctor.getPhone());
        if (StringUtil.stringIsNotNull(doctor.getId())) {
            queryDoc.append("id", new Document("$ne", doctor.getId()));
        }
        if (this.doctorMapper.isExists(queryDoc)) {
            throw new RuntimeException("手机号重复");
        }
    }
}
