package com.hlife.form_designer.test.controller;


import com.hlife.form_designer.test.model.Student;
import com.hlife.form_designer.test.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/studentController")
public class StudentController {

    @Autowired
    private IStudentService studentService;

    @RequestMapping("/selectStudentTom")
    public Student selectStudentTom() {
        return studentService.findStudentByStudentName("jerry");
    }
}
