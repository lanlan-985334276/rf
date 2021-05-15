package com.example.rongfu.service.impl;

import com.example.rongfu.entity.Admin;
import com.example.rongfu.entity.Enterprise;
import com.example.rongfu.entity.User;
import com.example.rongfu.mapper.AdminMapper;
import com.example.rongfu.mapper.EnterpriseMapper;
import com.example.rongfu.mapper.StaffMapper;
import com.example.rongfu.service.IAdminService;
import com.example.rongfu.service.ex.FailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private StaffMapper staffMapper;

    @Override
    public List<User> all(int userId) {
        Enterprise enterprise = enterpriseMapper.findByUserId(userId);
        Admin admin = adminMapper.findByUserId(userId);
        int epid = 0;
        if (enterprise != null) {
            epid = enterprise.getEpId();
        } else if (admin != null) {
            epid = admin.getEpId();
        } else throw new FailedException("未知错误，请联系管理员！");
        List<User> list = adminMapper.findAll(epid);
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
        Enterprise enterprise = enterpriseMapper.findByUserId(userId);
        Admin admin = adminMapper.findByUserId(userId);
        int epid = 0;
        if (enterprise != null) {
            epid = enterprise.getEpId();
        } else if (admin != null) {
            epid = admin.getEpId();
        } else throw new FailedException("未知错误，请联系管理员！");
        List<User> users = staffMapper.findByEnterpriseId(epid);
        List<User> result=new ArrayList<>();
        result.addAll(users);
        for (User user : users) {
            admin = adminMapper.findByUserId(user.getUserId());
            if (admin != null) result.remove(user);
        }
        return result;
    }

    @Override
    public void add(int mUserId, int userId) {
        Enterprise enterprise = enterpriseMapper.findByUserId(mUserId);
        Admin admin = adminMapper.findByUserId(mUserId);
        int epid = 0;
        if (enterprise != null) {
            epid = enterprise.getEpId();
        } else if (admin != null) {
            epid = admin.getEpId();
        } else throw new FailedException("未知错误，请联系管理员！");
        admin = new Admin();
        admin.setEpId(epid);
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
