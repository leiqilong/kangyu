package com.hlife.form_designer.test.service.impl;

import com.hlife.form_designer.test.dao.IStudentMapper;
import com.hlife.form_designer.test.model.Student;
import com.hlife.form_designer.test.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements IStudentService {
    @Autowired
    private IStudentMapper studentMapper;

    public Student findStudentByStudentName(String name) {
        return studentMapper.findStudentByName(name);
    }
}
