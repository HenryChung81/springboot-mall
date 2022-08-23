package com.example.springbootmall.dto;

import javax.validation.constraints.NotNull;

import com.example.springbootmall.constant.ProductCategory;

import lombok.Data;

@Data
public class ProductRequest {

  @NotNull
  private String productName;

  @NotNull
  private ProductCategory category;

  @NotNull
  private String imageUrl;

  @NotNull
  private Integer price;

  @NotNull
  private Integer stock;

  private String description;
  
}
