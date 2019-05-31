package com.hlife.form_designer.test.controller;


import com.hlife.form_designer.test.model.Student;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/testController")
public class TestController {

    @RequestMapping("/test")
    public String test() {
        return "2131213123111";
    }

    @RequestMapping("/student")
    public Student student() {
        return new Student().setId(UUID.randomUUID().toString()).setName("Jerry");
    }
}
