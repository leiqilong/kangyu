package com.hlife.task_manager.controller;

import com.hlife.framework.base.ResultVO;
import com.hlife.task_manager.model.User;
import com.hlife.task_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/findAll")
    public ResultVO<List<User>> findAll() {
        return new ResultVO<>(this.userService.findAll());
    }
}
