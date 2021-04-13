package com.example.rongfu.service;

import com.example.rongfu.entity.Product;

import java.util.List;

public interface IProductService {

    Product add(Product product);

    void delete(Product product);

    Product update(Product product);

    List<Product> findAll(int userId);

    List<Product> findByPName(Product product);
}
