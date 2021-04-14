package com.example.rongfu.service.impl;

import com.example.rongfu.entity.ProductCategory;
import com.example.rongfu.mapper.PCategoryMapper;
import com.example.rongfu.service.ICategoryService;
import com.example.rongfu.service.ex.FailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private PCategoryMapper categoryMapper;

    @Override
    public void add(ProductCategory category) {
        if (categoryMapper.findByPcName(category) != null)
            throw new FailedException("添加失败，分类名称已存在！");
        if (categoryMapper.insert(category) != 1)
            throw new FailedException("添加失败，未知插入错误，请联系管理员！");
    }

    @Override
    public void update(ProductCategory category) {
        if (categoryMapper.update(category) != 1)
            throw new FailedException("更新失败，未知更新错误，请联系管理员");
    }

    @Override
    public void delete(ProductCategory category) {
        if (categoryMapper.delete(category) != 1)
            throw new FailedException("删除失败，未知删除错误，请联系管理员");
    }

    @Override
    public List<ProductCategory> findAll(int userId) {
        System.out.println(userId);
        return categoryMapper.findAll(userId);
    }
}
