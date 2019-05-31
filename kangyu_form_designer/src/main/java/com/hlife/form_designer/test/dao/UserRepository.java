package com.hlife.form_designer.test.dao;


import com.hlife.form_designer.test.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, String> {
     Page<User> findByNameLike(String name, Pageable pageable);
}
