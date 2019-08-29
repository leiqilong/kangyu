package com.hlife.task_manager.dao;

import com.hlife.task_manager.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {

    List<User> findAll();
}
