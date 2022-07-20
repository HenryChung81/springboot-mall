package com.example.springbootmall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.springbootmall.dao.OrderDao;
import com.example.springbootmall.dao.ProductDao;
import com.example.springbootmall.dto.BuyItem;
import com.example.springbootmall.dto.CreateOrderRequest;
import com.example.springbootmall.model.Order;
import com.example.springbootmall.model.OrderItem;
import com.example.springbootmall.model.Product;
import com.example.springbootmall.service.OrderService;

@Component
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderDao orderDao;

  @Autowired
  private ProductDao productDao;

  @Override
  public Order getOrderById(Integer orderId) {
    Order order = orderDao.getOrderById(orderId);

    List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

    order.setOrderItemList(orderItemList);

    return order;
  }



  @Transactional
  @Override
  public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
    int totalAmount = 0;
    List<OrderItem> orderItemList = new ArrayList<>();

    for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
        Product product = productDao.getProductById(buyItem.getProductId());
        
        // total amount
        int amount = buyItem.getQuantity() * product.getPrice();
        totalAmount += amount;

        // BuyItem to OrderItem
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(buyItem.getProductId());
        orderItem.setQuantity(buyItem.getQuantity());
        orderItem.setAmount(amount);

        orderItemList.add(orderItem);

    }
    // create order
    Integer orderId = orderDao.createOrder(userId, totalAmount);

    orderDao.createOrderItems(orderId, orderItemList);

    return orderId;
  }

  
  
}
