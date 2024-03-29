package com.example.springbootmall.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import com.example.springbootmall.dao.UserDao;
import com.example.springbootmall.dto.UserLoginRequest;
import com.example.springbootmall.dto.UserRegisterRequest;
import com.example.springbootmall.model.User;
import com.example.springbootmall.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  
  private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
  
  private final UserDao userDao;

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

      // use MD5 generate hashed password
      String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());

      userRegisterRequest.setPassword(hashedPassword);

      // create a new account
      return userDao.createUser(userRegisterRequest);
    }
  }

  @Override
  public User login(UserLoginRequest userLoginRequest) {
    User user = userDao.getUserByEmail(userLoginRequest.getEmail());

    // check user
    if (user == null) {
      log.warn("該 email {} 尚未註冊", userLoginRequest.getEmail());
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    // use MD5 generate hashed password
    String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());

    // check password
    if (user.getPassword().equals(hashedPassword)) {
      return user;
    } else {
      log.warn("email {} 的密碼不正確", userLoginRequest.getEmail());
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

  }

  
  
}
