package com.hlife.form_designer.test.controller;


import com.hlife.form_designer.test.model.User;
import com.hlife.form_designer.test.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/saveUser")
    public User saveUser() {
        return userService.createUser(new User());
    }

    @GetMapping("/saveUser/{name}")
    public User saveUserWithName(@PathVariable String name) {
        return userService.createUser(new User(name));
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public User readUserById(@PathVariable("id") String id) {
        return userService.findOne(id);
    }

    @GetMapping(value = "findAll")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping(value = "deleteAll")
    public void deleteAll() {
        userService.deleteAll();
    }

    @GetMapping(value = "findAllWithPage")
    public List<User> findAllWithPage() {
        return userService.findAllWithPage();
    }
}
