package com.hlife.form_designer.test.service;


import com.hlife.form_designer.test.model.User;

import java.util.List;

public interface IUserService {

    User createUser(User user);

    User findOne(String id);

    List<User> findAll();

    void deleteAll();

    List<User> findAllWithPage();
}
