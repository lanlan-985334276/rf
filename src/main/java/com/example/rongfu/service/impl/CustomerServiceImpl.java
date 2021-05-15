package com.example.rongfu.service.impl;

import com.example.rongfu.entity.Admin;
import com.example.rongfu.entity.Customer;
import com.example.rongfu.entity.Enterprise;
import com.example.rongfu.entity.Product;
import com.example.rongfu.mapper.AdminMapper;
import com.example.rongfu.mapper.CustomerMapper;
import com.example.rongfu.mapper.EnterpriseMapper;
import com.example.rongfu.service.ICustomerService;
import com.example.rongfu.service.IProductService;
import com.example.rongfu.service.ex.FailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Customer add(Customer customer) {
        Enterprise enterprise = enterpriseMapper.findByUserId(customer.getUserId());
        Admin admin = adminMapper.findByUserId(customer.getUserId());
        int epid = 0;
        if (enterprise != null) {
            epid = enterprise.getEpId();
        } else if (admin != null) {
            epid = admin.getEpId();
        } else
            throw new FailedException("未知错误，请联系管理员！");
        customer.setEpid(epid);
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
        Enterprise enterprise = enterpriseMapper.findByUserId(customer.getUserId());
        Admin admin = adminMapper.findByUserId(customer.getUserId());
        int epid = 0;
        if (enterprise != null) {
            epid = enterprise.getEpId();
        } else if (admin != null) {
            epid = admin.getEpId();
        } else
            throw new FailedException("未知错误，请联系管理员！");
        customer.setEpid(epid);
        System.out.println(customer);
        if (customerMapper.update(customer) != 1)
            throw new FailedException("更新失败，未知更新错误，请联系管理员！");
        return customer;
    }

    @Override
    public List<Customer> findAll(int userId) {
        Enterprise enterprise = enterpriseMapper.findByUserId(userId);
        Admin admin = adminMapper.findByUserId(userId);
        int epid = 0;
        if (enterprise != null) {
            epid = enterprise.getEpId();
        } else if (admin != null) {
            epid = admin.getEpId();
        } else
            throw new FailedException("未知错误，请联系管理员！");
        return customerMapper.findAll(epid);
    }

    @Override
    public List<Customer> findByPName(Customer customer) {
        return null;
    }
}
