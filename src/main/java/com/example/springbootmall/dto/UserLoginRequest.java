package com.example.springbootmall.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserLoginRequest {

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String password;  
}
