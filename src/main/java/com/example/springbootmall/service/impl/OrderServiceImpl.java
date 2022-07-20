package com.example.springbootmall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.springbootmall.dao.OrderDao;
import com.example.springbootmall.dao.ProductDao;
import com.example.springbootmall.dao.UserDao;
import com.example.springbootmall.dto.BuyItem;
import com.example.springbootmall.dto.CreateOrderRequest;
import com.example.springbootmall.model.Order;
import com.example.springbootmall.model.OrderItem;
import com.example.springbootmall.model.Product;
import com.example.springbootmall.model.User;
import com.example.springbootmall.service.OrderService;

@Component
public class OrderServiceImpl implements OrderService {

  private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

  @Autowired
  private OrderDao orderDao;

  @Autowired
  private ProductDao productDao;

  @Autowired
  private UserDao userDao;

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

    User user = userDao.getUserById(userId);

    if (user == null) {
      log.warn("該 userId {} 不存在", userId);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    int totalAmount = 0;
    List<OrderItem> orderItemList = new ArrayList<>();

    for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
      Product product = productDao.getProductById(buyItem.getProductId());
        
      // check if product exists?
      if (product == null) {
        log.warn("商品 {} 不存在", buyItem.getProductId());
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST); 
      } else if (product.getStock() < buyItem.getQuantity()) {
        log.warn("商品 {} 庫存數量不足,無法購買。剩餘庫存 {},欲購買數量 {}",
            buyItem.getProductId(), product.getStock(), buyItem.getQuantity());
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
      }
      
      // update product stock
      productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());

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
