package com.example.rongfu.service.impl;

import com.example.rongfu.entity.Admin;
import com.example.rongfu.entity.Enterprise;
import com.example.rongfu.entity.ProductCategory;
import com.example.rongfu.mapper.AdminMapper;
import com.example.rongfu.mapper.EnterpriseMapper;
import com.example.rongfu.mapper.PCategoryMapper;
import com.example.rongfu.service.ICategoryService;
import com.example.rongfu.service.ex.FailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private PCategoryMapper categoryMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void add(ProductCategory category) {
        Enterprise enterprise = enterpriseMapper.findByUserId(category.getUserId());
        Admin admin = adminMapper.findByUserId(category.getUserId());
        if (enterprise != null) {
            category.setEpid(enterprise.getEpId());
        } else if (admin != null) {
            category.setEpid(admin.getEpId());
        } else
            throw new FailedException("未知错误，请联系管理员！");
        if (categoryMapper.findByPcName(category) != null)
            throw new FailedException("添加失败，分类名称已存在！");
        System.out.println(category);
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
        System.out.println(category);
        if (categoryMapper.delete(category) != 1)
            throw new FailedException("删除失败，未知删除错误，请联系管理员");
    }

    @Override
    public List<ProductCategory> findAll(int userId) {
        System.out.println(userId);
        Enterprise enterprise = enterpriseMapper.findByUserId(userId);
        Admin admin = adminMapper.findByUserId(userId);
        int epid = 0;
        if (enterprise != null) {
            epid = enterprise.getEpId();
        } else if (admin != null) {
            epid = admin.getEpId();
        } else
            throw new FailedException("未知错误，请联系管理员！");
        return categoryMapper.findAll(epid);
    }
}
