package com.example.rongfu.service.impl;

import com.example.rongfu.entity.Customer;
import com.example.rongfu.entity.Product;
import com.example.rongfu.mapper.CustomerMapper;
import com.example.rongfu.service.ICustomerService;
import com.example.rongfu.service.IProductService;
import com.example.rongfu.service.ex.FailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public Customer add(Customer customer) {
        if (customerMapper.insert(customer) != 1)
            throw new FailedException("添加失败，未知插入错误，请联系管理员！");
        return customer;
    }

    @Override
    public void delete(Customer customer) {
        if (customerMapper.delete(customer.getCustomerId()) != 1)
            throw new FailedException("删除失败，未知删除错误，请联系管理员！");
    }

    @Override
    public Customer update(Customer customer) {
        System.out.println(customer);
        if (customerMapper.update(customer) != 1)
            throw new FailedException("更新失败，未知更新错误，请联系管理员！");
        return customer;
    }

    @Override
    public List<Customer> findAll(int userId) {
        return customerMapper.findAll(userId);
    }

    @Override
    public List<Customer> findByPName(Customer customer) {
        return null;
    }
}
