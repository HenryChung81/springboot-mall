package com.example.springbootmall.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class User {

  private Integer userId;
  private String email;

  @JsonIgnore
  private String password;
  
  private Date createdDate;
  private Date lastModifiedDate;
  
}
