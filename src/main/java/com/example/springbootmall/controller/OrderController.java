package com.example.springbootmall.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootmall.dto.CreateOrderRequest;
import com.example.springbootmall.dto.OrderQueryParams;
import com.example.springbootmall.model.Order;
import com.example.springbootmall.service.OrderService;
import com.example.springbootmall.util.Page;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @GetMapping("/users/{userId}/orders")
  public ResponseEntity<Page<Order>> getOrders(
    @PathVariable Integer userId,
    @RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit,
    @RequestParam(defaultValue = "0") @Min(0) Integer offset

  ) {
    OrderQueryParams orderQueryParams = new OrderQueryParams();
    orderQueryParams.setUserId(userId);
    orderQueryParams.setLimit(limit);
    orderQueryParams.setOffset(offset);

    // get order list
    List<Order> orderList = orderService.getOrders(orderQueryParams);

    // get order count
    Integer count = orderService.countOrder(orderQueryParams);

    Page<Order> page = new Page<>();
    page.setLimit(limit);
    page.setOffset(offset);
    page.setTotal(count);
    page.setResults(orderList);

    return ResponseEntity.status(HttpStatus.OK).body(page);
    
  }

  @PostMapping("users/{userId}/orders")
  public ResponseEntity<?> createOrder(@PathVariable Integer userId,
      @RequestBody @Valid CreateOrderRequest createOrderRequest) {
    
    Integer orderId = orderService.createOrder(userId, createOrderRequest);

    Order order = orderService.getOrderById(orderId);

    return ResponseEntity.status(HttpStatus.CREATED).body(order);
    
  }
  
  
}
