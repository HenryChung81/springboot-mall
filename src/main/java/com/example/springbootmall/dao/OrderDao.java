package com.example.springbootmall.dao;

import java.util.List;

import com.example.springbootmall.model.OrderItem;

public interface OrderDao {
  
  Integer createOrderDao(Integer userId, Integer totalAmount);
  void createOrderItems(Integer orderId, List<OrderItem> orderItemList);

}
