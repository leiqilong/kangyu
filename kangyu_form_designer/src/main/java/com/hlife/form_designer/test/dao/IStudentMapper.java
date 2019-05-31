package com.hlife.form_designer.test.dao;

import com.hlife.form_designer.test.model.Student;

public interface IStudentMapper {

    /**
     * 根据name查询学生对象
     * @param name
     * @return
     */
    Student findStudentByName(String name);

    /**
     * 创建对象
     * @param user
     */
    void saveStudent(Student user);
}
