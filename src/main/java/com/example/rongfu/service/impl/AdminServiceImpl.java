package com.example.rongfu.service.impl;

import com.example.rongfu.entity.Admin;
import com.example.rongfu.entity.Enterprise;
import com.example.rongfu.entity.User;
import com.example.rongfu.mapper.AdminMapper;
import com.example.rongfu.mapper.EnterpriseMapper;
import com.example.rongfu.service.IAdminService;
import com.example.rongfu.service.ex.FailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Override
    public List<User> all(int userId) {
        List<User> list = adminMapper.findAll(userId);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUserId() == userId) {
                list.remove(i);
                break;
            }
        }
        return list;
    }

    @Override
    public List<User> other(int userId) {
        return adminMapper.findOther(userId);
    }

    @Override
    public void add(int mUserId, int userId) {
        Admin admin = adminMapper.findByUserId(mUserId);
        if (admin == null) {
            Enterprise enterprise = enterpriseMapper.findByUserId(mUserId);
            if (enterprise == null)
                throw new FailedException("没有权限执行该操作！");
            admin.setEpId(enterprise.getEpId());
            admin.setUserId(userId);
        } else
            admin.setUserId(userId);
        System.out.println(admin);
        if (adminMapper.insert(admin) != 1)
            throw new FailedException("分配权限失败，未知插入错误，请联系管理员！");
    }

    @Override
    public void delete(int adminId) {
        if (adminMapper.delete(adminId) != 1)
            throw new FailedException("删除管理员失败，未知删除错误，请联系管理员！");

    }
}
