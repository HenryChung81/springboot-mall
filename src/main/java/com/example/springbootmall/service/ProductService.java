package com.example.springbootmall.service;

import java.util.List;

import com.example.springbootmall.dto.ProductQueryParams;
import com.example.springbootmall.dto.ProductRequest;
import com.example.springbootmall.model.Product;

public interface ProductService {

  Integer countProduct(ProductQueryParams productQueryParams);

  List<Product> getProducts(ProductQueryParams productQueryParams);
  
  Product getProductById(Integer productId);

  Integer createProduct(ProductRequest productRequest);

  void updateProduct(Integer productId, ProductRequest productRequest);

  void deleteProductById(Integer productId);

}
