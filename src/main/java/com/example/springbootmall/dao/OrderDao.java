package com.example.springbootmall.dao;

import java.util.List;

import com.example.springbootmall.model.Order;
import com.example.springbootmall.model.OrderItem;

public interface OrderDao {
  
  Order getOrderById(Integer orderId);

  List<OrderItem> getOrderItemsByOrderId(Integer orderId);

  Integer createOrder(Integer userId, Integer totalAmount);

  void createOrderItems(Integer orderId, List<OrderItem> orderItemList);

}
