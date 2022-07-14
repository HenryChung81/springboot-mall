package com.example.springbootmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.springbootmall.dao.UserDao;
import com.example.springbootmall.dto.UserRegisterRequest;
import com.example.springbootmall.model.User;
import com.example.springbootmall.service.UserService;

@Component
public class UserServiceImpl implements UserService{
  
  @Autowired
  private UserDao userDao;

  @Override
  public User getUserById(Integer userId) {
    return userDao.getUserById(userId);
  }

  @Override
  public Integer register(UserRegisterRequest userRegisterRequest) {
    return userDao.createUser(userRegisterRequest);
  }
  
}
