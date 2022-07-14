package com.example.springbootmall.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.example.springbootmall.dao.UserDao;
import com.example.springbootmall.dto.UserRegisterRequest;
import com.example.springbootmall.model.User;
import com.example.springbootmall.service.UserService;

@Component
public class UserServiceImpl implements UserService {
  
  private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
  
  @Autowired
  private UserDao userDao;

  @Override
  public User getUserById(Integer userId) {
    return userDao.getUserById(userId);
  }

  @Override
  public Integer register(UserRegisterRequest userRegisterRequest) {
    // check registered email
    User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

    if (user != null) {
      log.warn("該 email {} 已經被註冊", userRegisterRequest.getEmail());
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    } else {

      // create a new account
      return userDao.createUser(userRegisterRequest);
    }
  }
  
}
