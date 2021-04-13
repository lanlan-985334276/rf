package com.example.rongfu.service.impl;

import com.example.rongfu.entity.Product;
import com.example.rongfu.mapper.ProductMapper;
import com.example.rongfu.service.IProductService;
import com.example.rongfu.service.ex.FailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceIml implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Product add(Product product) {
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
        if (productMapper.update(product) != 1)
            throw new FailedException("更新失败，未知更新错误，请联系管理员！");
        return product;
    }

    @Override
    public List<Product> findAll(int userId) {
        return productMapper.findAll(userId);
    }

    @Override
    public List<Product> findByPName(Product product) {
        return null;
    }
}
