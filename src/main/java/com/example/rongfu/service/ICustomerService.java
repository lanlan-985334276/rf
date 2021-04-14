package com.example.rongfu.service;

import com.example.rongfu.entity.Customer;
import com.example.rongfu.entity.Product;

import java.util.List;

public interface ICustomerService {

    Customer add(Customer customer);

    void delete(Customer customer);

    Customer update(Customer customer);

    List<Customer> findAll(int userId);

    List<Customer> findByPName(Customer customer);
}
