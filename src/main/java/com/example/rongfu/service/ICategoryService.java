package com.example.rongfu.service;

import com.example.rongfu.entity.ProductCategory;

import java.util.List;

public interface ICategoryService {

    void add(ProductCategory category);

    void update(ProductCategory category);

    void delete(ProductCategory category);

    List<ProductCategory> findAll(int userId);
}
