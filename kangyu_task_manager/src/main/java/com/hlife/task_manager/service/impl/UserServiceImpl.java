package com.hlife.task_manager.service.impl;

import com.hlife.task_manager.dao.UserDao;
import com.hlife.task_manager.model.User;
import com.hlife.task_manager.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public List<User> findAll() {
        return this.userDao.findAll();
    }
}
