package com.example.springbootmall.service;

import java.util.List;

import com.example.springbootmall.dto.CreateOrderRequest;
import com.example.springbootmall.dto.OrderQueryParams;
import com.example.springbootmall.model.Order;

public interface OrderService {

  Integer countOrder(OrderQueryParams orderQueryParams);

  List<Order> getOrders(OrderQueryParams orderQueryParams);

  Order getOrderById(Integer orderId);
  
  Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

  
}
