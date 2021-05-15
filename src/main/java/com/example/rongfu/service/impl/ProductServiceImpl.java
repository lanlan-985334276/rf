package com.example.rongfu.service.impl;

import com.example.rongfu.entity.Admin;
import com.example.rongfu.entity.Enterprise;
import com.example.rongfu.entity.Product;
import com.example.rongfu.mapper.AdminMapper;
import com.example.rongfu.mapper.EnterpriseMapper;
import com.example.rongfu.mapper.PCategoryMapper;
import com.example.rongfu.mapper.ProductMapper;
import com.example.rongfu.service.IProductService;
import com.example.rongfu.service.ex.FailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Product add(Product product) {
        Enterprise enterprise = enterpriseMapper.findByUserId(product.getUserId());
        Admin admin = adminMapper.findByUserId(product.getUserId());
        int epid = 0;
        if (enterprise != null) {
            epid = enterprise.getEpId();
        } else if (admin != null) {
            epid = admin.getEpId();
        } else
            throw new FailedException("未知错误，请联系管理员！");
        product.setEpid(epid);
        if (productMapper.insert(product) != 1)
            throw new FailedException("添加失败，未知插入错误，请联系管理员！");
        return product;
    }

    @Override
    public void delete(Product product) {
        if (productMapper.delete(product.getProductId()) != 1)
            throw new FailedException("删除失败，未知删除错误，请联系管理员！");
    }

    @Override
    public Product update(Product product) {
        System.out.println(product);
        Enterprise enterprise = enterpriseMapper.findByUserId(product.getUserId());
        Admin admin = adminMapper.findByUserId(product.getUserId());
        int epid = 0;
        if (enterprise != null) {
            epid = enterprise.getEpId();
        } else if (admin != null) {
            epid = admin.getEpId();
        } else
            throw new FailedException("未知错误，请联系管理员！");
        product.setEpid(epid);
        if (productMapper.update(product) != 1)
            throw new FailedException("更新失败，未知更新错误，请联系管理员！");
        return product;
    }

    @Override
    public List<Product> findAll(int userId) {
        Enterprise enterprise = enterpriseMapper.findByUserId(userId);
        Admin admin = adminMapper.findByUserId(userId);
        int epid = 0;
        if (enterprise != null) {
            epid = enterprise.getEpId();
        } else if (admin != null) {
            epid = admin.getEpId();
        } else
            throw new FailedException("未知错误，请联系管理员！");
        return productMapper.findAll(epid);
    }

    @Override
    public List<Product> findByPName(Product product) {
        return null;
    }

    @Override
    public List<Product> findAllCount(int userId) {
        Enterprise enterprise = enterpriseMapper.findByUserId(userId);
        Admin admin = adminMapper.findByUserId(userId);
        int epid = 0;
        if (enterprise != null) {
            epid = enterprise.getEpId();
        } else if (admin != null) {
            epid = admin.getEpId();
        } else
            throw new FailedException("未知错误，请联系管理员！");
        return productMapper.findByCount(epid);
    }

    @Override
    public Product findById(Product product) {
        product=productMapper.findByProductId(product.getProductId());
        return product;
    }
}
