package com.hlife.form_designer.test.service;


import com.hlife.form_designer.test.model.Student;

public interface IStudentService {

    Student findStudentByStudentName(String name);
}
