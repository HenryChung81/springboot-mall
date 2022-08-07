package com.example.springbootmall.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootmall.dto.UserLoginRequest;
import com.example.springbootmall.dto.UserRegisterRequest;
import com.example.springbootmall.model.User;
import com.example.springbootmall.service.UserService;

@RestController
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("users/register")
  public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {

    Integer userId = userService.register(userRegisterRequest);

    User user = userService.getUserById(userId);

    return ResponseEntity.status(HttpStatus.CREATED).body(user);

  }
  
  @PostMapping("users/login")
  public ResponseEntity<User> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {

    User user = userService.login(userLoginRequest);
    

    return ResponseEntity.status(HttpStatus.OK).body(user);
    
  }
  
}
