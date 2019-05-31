package com.hlife.form_designer.test.service.impl;


import com.hlife.form_designer.test.dao.UserRepository;
import com.hlife.form_designer.test.model.User;
import com.hlife.form_designer.test.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findOne(String id) {
        User user = new User();
        user.setId(id);
        //ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("age","createTime");
        //Example<User> example = Example.of(user, matcher);
       // Optional<User> optional = userRepository.findOne(example);
        return new User();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public List<User> findAllWithPage() {
        PageRequest pageRequest = new PageRequest(0,4);
        return userRepository.findAll(pageRequest).getContent();
    }
}
