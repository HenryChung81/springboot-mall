package com.example.springbootmall.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Order {

  private Integer orderId;
  private Integer userId;
  private Integer totalAmount;
  private Date createdDate;
  private Date lastModifiedDate;

  private List<OrderItem> orderItemList;
  
}
